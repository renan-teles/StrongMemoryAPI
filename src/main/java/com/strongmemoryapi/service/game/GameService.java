package com.strongmemoryapi.service.game;

import com.strongmemoryapi.domain.model.DifficultyModel;
import com.strongmemoryapi.domain.model.MatchPlayedModel;
import com.strongmemoryapi.dto.request.game.StartGameRequest;
import com.strongmemoryapi.dto.response.GameResponse;
import com.strongmemoryapi.dto.response.WordDrawnResponse;
import com.strongmemoryapi.service.difficulty.DifficultyService;
import com.strongmemoryapi.service.matchhistory.MatchPlayedService;
import com.strongmemoryapi.service.matchhistory.WordDrawnService;
import com.strongmemoryapi.service.word.WordService;
import com.strongmemoryapi.utils.mapper.WordDrawnMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private WordService wordService;

    @Autowired
    private WordDrawnService wordDrawnService;

    @Autowired
    private MatchPlayedService matchService;

    @Autowired
    private DifficultyService difficultyService;

    @Transactional
    public GameResponse startGame(Long userId, StartGameRequest request){
        DifficultyModel difficulty = difficultyService
                .findByName(request.difficulty().toLowerCase());

        List<Long> randomWordIds = wordService.findRandomWordIds(
                difficulty.getName().toLowerCase(),
                difficulty.getQuantityWords()
        );

        MatchPlayedModel match = matchService.registerInitialData(
                userId,
                difficulty,
                request.infiniteMode()
        );

        List<WordDrawnResponse> wordDrawnList = wordDrawnService
                .registerInitialData(randomWordIds, match)
                .stream()
                .map(WordDrawnMapper::toDTO)
                .toList();

        return new GameResponse(match.getId(), wordDrawnList);
    }

    public void finalizeGame(){}

    public void findMoreRandomWords(){}

}
