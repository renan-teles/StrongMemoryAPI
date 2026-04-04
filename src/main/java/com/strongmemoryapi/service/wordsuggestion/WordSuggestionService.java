package com.strongmemoryapi.service.wordsuggestion;

import com.strongmemoryapi.exception.local.ResourceAlreadyExistsException;
import com.strongmemoryapi.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.dto.request.wordsuggestion.WordSuggestionRequest;
import com.strongmemoryapi.dto.response.WordSuggestionResponse;
import com.strongmemoryapi.domain.entity.user.UserEntity;
import com.strongmemoryapi.domain.entity.wordsuggestion.WordSuggestionEntity;
import com.strongmemoryapi.repository.difficulty.DifficultyRepository;
import com.strongmemoryapi.repository.user.UserRepository;
import com.strongmemoryapi.repository.word.WordRepository;
import com.strongmemoryapi.repository.wordsuggestion.WordSuggestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class WordSuggestionService {

    @Autowired
    private WordSuggestionRepository suggestionRepository;

    @Autowired
    private DifficultyRepository difficultyRepository;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<WordSuggestionResponse> getAll(
            int page,
            int size,
            String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return suggestionRepository
                .findAll(pageable)
                .map(this::partoToWordSuggestionResponse);
    }

    public Page<WordSuggestionResponse> getByPeriod(
            LocalDate startDate,
            LocalDate endDate,
            int page,
            int size
    ) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Data final não pode ser menor que a data inicial.");
        }

        Pageable pageable = PageRequest.of(page, size);

        Instant start = startDate.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant end = endDate.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();

        Page<WordSuggestionEntity> suggestions =
                suggestionRepository.findBySuggestedAtBetween(start, end, pageable);

        return suggestions.map(this::partoToWordSuggestionResponse);
    }

    public WordSuggestionResponse register(Long userId, WordSuggestionRequest request){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário sugeridor não encontrado."));

        if(wordRepository.existsByWord(request.suggestedWord())){
            throw new ResourceAlreadyExistsException("Palavra sugerida já cadastrada.");
        }

        if(suggestionRepository.existsBysuggestedWord(request.suggestedWord())){
            throw new ResourceAlreadyExistsException("Palavra já sugerida.");
        }

        if(!difficultyRepository.existsByDifficulty(request.suggestedDifficulty())){
            throw new ResourceNotFoundException("Difficuldade não encontrada.");
        }

        WordSuggestionEntity suggestion = new WordSuggestionEntity();
        suggestion.setUser(user);
        suggestion.setSuggestedWord(request.suggestedWord().toLowerCase());
        suggestion.setSuggestedDifficulty(request.suggestedDifficulty().toLowerCase());

        return partoToWordSuggestionResponse(suggestionRepository.save(suggestion));
    }

    public void delete(Long id){
        if(!suggestionRepository.existsById(id)){
            throw new ResourceNotFoundException("Sugestão de palavra não encontrada.");
        }
        suggestionRepository.deleteById(id);
    }

    private WordSuggestionResponse partoToWordSuggestionResponse(WordSuggestionEntity suggestion){
        return new WordSuggestionResponse(
                suggestion.getId(),
                suggestion.getSuggestedWord(),
                suggestion.getSuggestedDifficulty(),
                suggestion.getSuggestedAt(),
                suggestion.getUser().getUsername(),
                suggestion.getUser().getEmail()
        );
    }

}
