package com.strongmemoryapi.service.word;

import com.strongmemoryapi.domain.model.WordModel;
import com.strongmemoryapi.dto.word.RegisterWordRequest;
import com.strongmemoryapi.service.word.suggestion.WordSuggestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordSharedService {

    @Autowired
    private WordSuggestionService suggestionService;

    @Autowired
    private WordService wordService;

    @Transactional
    public WordModel register(RegisterWordRequest request, boolean originatedFromSuggestion){
        if(originatedFromSuggestion){
            suggestionService.delete(request.word());
        }
        return wordService.register(request);
    }

}
