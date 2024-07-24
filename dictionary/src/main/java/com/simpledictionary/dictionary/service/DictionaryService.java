package com.simpledictionary.dictionary.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpledictionary.dictionary.model.Word;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class DictionaryService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public Word getWordMeaning (String word){
        String url="https://api.dictionaryapi.dev/api/v2/entries/en/"+word;
        String response  = restTemplate.getForObject(url,String.class);
            try {
                JsonNode root = objectMapper.readTree(response);
                JsonNode definitionsNode = root
                        .get(0)
                        .get("meanings")
                        .get(0)
                        .get("definitions")
                        .get(0);

                String meaning = definitionsNode.get("definition").asText();
                String example = definitionsNode.has("example") ? definitionsNode.get("example").asText() : "Example not available";

                return new Word(word, meaning, example);
            } catch (IOException e) {
                e.printStackTrace();
                return new Word(word, "Meaning not found", "Example not available");
            }

    }

}
