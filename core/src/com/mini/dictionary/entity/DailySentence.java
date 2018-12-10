package com.mini.dictionary.entity;

public class DailySentence {
    private int SentenceId;
    private String EnglishSentence;
    private String ChineseMeaning;

    public int getSentenceId() {
        return SentenceId;
    }

    public void setSentenceId(int sentenceId) {
        SentenceId = sentenceId;
    }

    public String getEnglishSentence() {
        return EnglishSentence;
    }

    public void setEnglishSentence(String englishSentence) {
        EnglishSentence = englishSentence;
    }

    public String getChineseMeaning() {
        return ChineseMeaning;
    }

    public void setChineseMeaning(String chineseMeaning) {
        ChineseMeaning = chineseMeaning;
    }
}
