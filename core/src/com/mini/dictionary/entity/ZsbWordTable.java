package com.mini.dictionary.entity;

public class ZsbWordTable {
    private int WordId;

    public int getWordId() {
        return WordId;
    }

    public void setWordId(int wordId) {
        WordId = wordId;
    }

    public int getLexiconId() {
        return LexiconId;
    }

    public void setLexiconId(int lexiconId) {
        LexiconId = lexiconId;
    }

    private int LexiconId;
}
