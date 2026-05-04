package com.strongmemoryapi.service.matchhistory;

import com.strongmemoryapi.domain.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.domain.model.MatchPlayedModel;
import com.strongmemoryapi.domain.model.WordDrawnModel;
import com.strongmemoryapi.domain.model.WordModel;
import com.strongmemoryapi.repository.WordDrawnRepository;
import com.strongmemoryapi.utils.DatabaseErrorUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class WordDrawnService {

    @Autowired
    private WordDrawnRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<WordDrawnModel> registerInitialData(List<Long> wordIds, MatchPlayedModel match){
        if(wordIds.isEmpty()){
            throw new IllegalArgumentException(
                 "A lista de ids não pode ser vazio para registro de palavras sorteadas em uma partida."
            );
        }

        List<WordDrawnModel> wordDrawnList = IntStream.range(0, wordIds.size())
                .mapToObj(index -> {
                    Long wordId = wordIds.get(index);

                    WordModel word = new WordModel();
                    word.setId(wordId);

                    WordDrawnModel wordDrawn = new WordDrawnModel();
                    wordDrawn.setMatchPlayed(match);
                    wordDrawn.setOrderIndex(index);
                    wordDrawn.setWord(word);

                    return wordDrawn;
                })
                .toList();

        try {
            repository.saveAll(wordDrawnList);

            entityManager.flush();
            entityManager.clear();

            return repository.findByMatchPlayed_IdWithWord(match.getId());
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

}
