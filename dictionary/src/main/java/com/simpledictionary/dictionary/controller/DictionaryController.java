package com.simpledictionary.dictionary.controller;

import com.simpledictionary.dictionary.model.Word;
import com.simpledictionary.dictionary.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/api/v1/dictionary")
    @RequiredArgsConstructor
    public class DictionaryController {

        private final DictionaryService dictionaryService;

        @GetMapping("/{word}")
        public Word getWordMeaning(@PathVariable String word) {
            return dictionaryService.getWordMeaning(word);
        }
}
