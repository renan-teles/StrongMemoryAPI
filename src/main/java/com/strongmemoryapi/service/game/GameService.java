package com.strongmemoryapi.service.game;

import com.strongmemoryapi.domain.model.DifficultyModel;
import com.strongmemoryapi.domain.model.matchhistory.MatchPlayedModel;
import com.strongmemoryapi.domain.model.matchhistory.DrawnWordModel;
import com.strongmemoryapi.dto.game.FinishGameResponse;
import com.strongmemoryapi.dto.game.StartGameRequest;
import com.strongmemoryapi.dto.game.GameDataDTO;
import com.strongmemoryapi.dto.matchhistory.DrawnWordDTO;
import com.strongmemoryapi.dto.matchhistory.FinishMatchDTO;
import com.strongmemoryapi.dto.matchhistory.MatchPlayedDTO;
import com.strongmemoryapi.service.difficulty.DifficultyService;
import com.strongmemoryapi.service.matchhistory.MatchPlayedService;
import com.strongmemoryapi.service.matchhistory.DrawnWordService;
import com.strongmemoryapi.service.word.WordService;
import com.strongmemoryapi.utils.mapper.DrawnWordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private WordService wordService;

    @Autowired
    private DrawnWordService drawnWordService;

    @Autowired
    private MatchPlayedService matchService;

    @Autowired
    private DifficultyService difficultyService;

    @Transactional
    public GameDataDTO start(Long userId, StartGameRequest request){
        DifficultyModel difficulty = difficultyService
                .findByName(request.difficulty().toLowerCase());

        List<Long> randomWordIds = wordService.findRandomWordIds(
                difficulty.getName().toLowerCase(),
                difficulty.getNumberWords()
        );

        MatchPlayedModel match = matchService.register(
                userId,
                difficulty,
                request.mode()
        );

        List<DrawnWordDTO> wordDrawnList = drawnWordService
                .register(randomWordIds, match)
                .stream()
                .map(DrawnWordMapper::toDTO)
                .toList();

        MatchPlayedDTO matchResponse = new MatchPlayedDTO(
                match.getId(),
                difficulty.getName(),
                match.getResult(),
                match.getAverageResponseTimeMs()
        );

        return new GameDataDTO(matchResponse, wordDrawnList);
    }

    @Transactional
    public FinishGameResponse finish(Long userId, GameDataDTO request){
        MatchPlayedDTO matchDTO = request.match();

        MatchPlayedModel match = matchService
                .findNotCompletedByIdAndUserId(
                        matchDTO.id(),
                        userId
                );

        List<DrawnWordDTO> drawnWords = request.drawnWords();

        drawnWordService.validateDrawnWordsList(drawnWords, match);

        FinishMatchDTO finishDTO = matchService
                .finish(userId, drawnWords, match, matchDTO);

        drawnWordService.update(drawnWords, finishDTO.match());

        return new FinishGameResponse(
                finishDTO.hasNewHighestScore(),
                finishDTO.highestScore()
        );
    }

    @Transactional
    public void gaveUp(Long userId, GameDataDTO request){
        List<DrawnWordDTO> drawnWords = request.drawnWords();
        MatchPlayedDTO matchDTO = request.match();

        MatchPlayedModel matchModel = matchService
                .findNotCompletedByIdAndUserId(
                    matchDTO.id(),
                    userId
                );

        drawnWordService.validateDrawnWordsList(drawnWords, matchModel);

        matchService.gaveUp(matchDTO, matchModel, drawnWords);

        drawnWordService.update(drawnWords, matchModel);
    }

    public List<DrawnWordModel> findMoreRandomWords(
            Long userId,
            Long matchId,
            Integer startOrderIndex
    ){
        MatchPlayedModel match = matchService
                .findNotCompletedByIdAndUserId(matchId, userId);

        DifficultyModel difficulty = match.getDifficulty();

        List<Long> randomWordIds = wordService.findRandomWordIds(
                difficulty.getName().toLowerCase(),
                difficulty.getNumberWords()
        );

        return drawnWordService
                .register(randomWordIds, match, startOrderIndex);
    }

}