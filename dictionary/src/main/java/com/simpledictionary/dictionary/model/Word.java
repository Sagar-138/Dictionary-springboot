package com.simpledictionary.dictionary.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Word {
    private String word;
    private String meaning;
    private String example;


}
