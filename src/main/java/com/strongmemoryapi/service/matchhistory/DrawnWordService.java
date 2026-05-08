package com.strongmemoryapi.service.matchhistory;

import com.strongmemoryapi.domain.exception.local.BusinessRuleException;
import com.strongmemoryapi.domain.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.domain.model.DifficultyModel;
import com.strongmemoryapi.domain.model.MatchPlayedModel;
import com.strongmemoryapi.domain.model.DrawnWordModel;
import com.strongmemoryapi.domain.model.WordModel;
import com.strongmemoryapi.dto.matchhistory.DrawnWordDTO;
import com.strongmemoryapi.repository.DrawnWordRepository;
import com.strongmemoryapi.repository.WordRepository;
import com.strongmemoryapi.utils.DatabaseErrorUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class DrawnWordService {

    @Autowired
    private DrawnWordRepository drawnWordRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<DrawnWordModel> register(List<Long> wordIds, MatchPlayedModel match){
        return register(wordIds, match, Optional.empty());
    }

    public List<DrawnWordModel> register(
            List<Long> wordIds,
            MatchPlayedModel match,
            Integer startOrderIndex
    ){
        return register(wordIds, match, Optional.ofNullable(startOrderIndex));
    }

    public void update(List<DrawnWordDTO> drawnWords, MatchPlayedModel match){
        List<DrawnWordModel> updatedModelList = drawnWords
                .stream()
                .map((dto) -> {
                    DrawnWordModel model = new DrawnWordModel();
                    WordModel word = new WordModel();
                    word.setId(dto.word().id());

                    model.setMatchPlayed(match);
                    model.setWord(word);

                    model.setId(dto.id());
                    model.setOrderIndex(dto.orderIndex());
                    model.setWasShown(dto.wasShown());
                    model.setIsCorrect(dto.isCorrect());
                    model.setLastTypedWord(dto.lastTypedWord());

                    return model;
                })
                .toList();

        try {
            drawnWordRepository.saveAll(updatedModelList);
        } catch (DataIntegrityViolationException ex){
            if(DatabaseErrorUtils.isForeignKeyViolation(ex)){
                throw new ResourceNotFoundException("Palavra de associação não encontrada.");
            }
            throw ex;
        }
    }

    public void validateDrawnWordsList(
            List<DrawnWordDTO> drawnWords,
            MatchPlayedModel match
    ){
        if(drawnWords.isEmpty()){
            throw new IllegalArgumentException(
                 "Lista de palavras inválida."
            );
        }

        List<Integer> orderIndexes = drawnWords
                .stream()
                .map(DrawnWordDTO::orderIndex)
                .toList();

        for(int i = 0; i < orderIndexes.size(); i++){
            if(orderIndexes.get(i) != i){
                throw new IllegalArgumentException(
                    "Índice de ordem de palavra no sorteio inválido."
                );
            }
        }

        List<Long> dtoDrawnWordsIds = drawnWords
                .stream()
                .map(DrawnWordDTO::id)
                .toList();

        List<Long> drawnWordsIds = drawnWordRepository
                .findIdByMatchPlayed_IdOrderByOrderIndex(match.getId());

        int dtoListSize = dtoDrawnWordsIds.size();

        if(drawnWordsIds.size() != dtoListSize){
            throw new IllegalArgumentException(
                  "Lista de palavras sorteadas inválida."
            );
        }

        for(int i = 0; i < dtoListSize; i++){
            long dtoId = dtoDrawnWordsIds.get(i);
            long drawnWordId = drawnWordsIds.get(i);

            if(dtoId != drawnWordId){
                throw new IllegalArgumentException(
                     "Ids de palavras sorteadas inválidos."
                );
            }
        }

        boolean containInvalids = drawnWords
                .stream()
                .anyMatch((w) -> !w.wasShown() && w.isCorrect());

        if(containInvalids){
            throw new BusinessRuleException(
                    "Palavra não exibida marcada como correta."
            );
        }

        List<Long> dtoWordIds = drawnWords
                .stream()
                .map((d) -> d.word().id())
                .toList();

        dtoListSize = dtoWordIds.size();

        List<Long> wordIds = drawnWordRepository
                .findWord_IdByMatchPlayed_IdOrderByOrderIndex(match.getId());

        if(wordIds.size() != dtoListSize){
            throw new IllegalArgumentException(
                    "Lista de palavras sorteadas inválida."
            );
        }

        for(int i = 0; i < dtoListSize; i++){
            long dtoWordId = dtoWordIds.get(i);
            long wordId = wordIds.get(i);

            if(dtoWordId != wordId){
                throw new IllegalArgumentException(
                     "Ids de palavras inválidos."
                );
            }
        }
    }

    @Transactional
    private List<DrawnWordModel> register(
            List<Long> wordIds,
            MatchPlayedModel match,
            Optional<Integer> startIndexOpt
    ){
        if(wordIds.isEmpty()){
            throw new IllegalArgumentException(
                  "A lista de ids não pode ser vazio para registro de palavras sorteadas em uma partida."
            );
        }

        DifficultyModel difficulty = match.getDifficulty();
        if(wordIds.size() != difficulty.getQuantityWords()){
            throw new BusinessRuleException(
                 "Quantidade mínima de palavras inválidas para dificuldade: " + difficulty.getName()
            );
        }

        boolean isGetMore = startIndexOpt.isPresent();
        int startIndex = getStartIndex(startIndexOpt, match);

        List<DrawnWordModel> wordDrawnList = IntStream
                .range(0, wordIds.size())
                .mapToObj(index -> {
                    Long wordId = wordIds.get(index);

                    WordModel word = new WordModel();
                    word.setId(wordId);

                    DrawnWordModel drawnWord = new DrawnWordModel();
                    drawnWord.setMatchPlayed(match);
                    drawnWord.setWord(word);

                    drawnWord.setOrderIndex(startIndex + index);
                    drawnWord.setIsCorrect(false);
                    drawnWord.setWasShown(false);

                    return drawnWord;
                })
                .toList();

        try {
            drawnWordRepository.saveAll(wordDrawnList);

            entityManager.flush();
            entityManager.clear();

            if(isGetMore){
                return drawnWordRepository
                        .findByMatchPlayed_IdWithWordAndOrderIndexEqualsOrGreaterThan(
                                match.getId(),
                                startIndex
                        );
            }

            return drawnWordRepository.findByMatchPlayed_IdWithWord(match.getId());
        } catch (DataIntegrityViolationException ex){
            if(DatabaseErrorUtils.isForeignKeyViolation(ex, "words")){
                throw new ResourceNotFoundException("Palavra não encontrada.");
            }
            if(DatabaseErrorUtils.isForeignKeyViolation(ex, "matches_played")){
                throw new ResourceNotFoundException("Partida não encontrada.");
            }
            throw ex;
        }
    }

    private int getStartIndex(
            Optional<Integer> startIndexOpt,
            MatchPlayedModel match
    ) {
        if(startIndexOpt.isEmpty()){
            return 0;
        }

        if(!match.getInfiniteMode()){
            throw new BusinessRuleException(
                 "Partidas com modo de jogo finito não podem ter novas palavras."
            );
        }

        int startOrderIndex = startIndexOpt.get();
        int count = (int) drawnWordRepository.countByMatchPlayed_Id(match.getId());

        if(startOrderIndex != count){
            throw new IllegalArgumentException(
                 "Índice de ordem de palavras no sorteio inválido."
            );
        }

        return startOrderIndex;
    }

}
