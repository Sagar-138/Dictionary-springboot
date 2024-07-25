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
            JsonNode meaningsNode = root.get(0).get("meanings");

            for (JsonNode meaningNode : meaningsNode) {
                JsonNode definitionsNode = meaningNode.get("definitions");

                for (JsonNode definitionNode : definitionsNode) {
                    String meaning = definitionNode.get("definition").asText();
                    String example = definitionNode.has("example") ? definitionNode.get("example").asText() : "Example not available";

                    if (!example.equals("Example not available")) {
                        return new Word(word, meaning, example);
                    }
                }
            }

            // If no example found in any definition, return the first definition with a default example
            JsonNode firstDefinitionNode = meaningsNode.get(0).get("definitions").get(0);
            String firstMeaning = firstDefinitionNode.get("definition").asText();
            return new Word(word, firstMeaning, "Example not available");
            } catch (IOException e) {
                e.printStackTrace();
                return new Word(word, "Meaning not found", "Example not available");
            }

    }

}
