package com.mini.dictionary.entity;

public class LexiconTable {
    private int LexiconTableId;
    private String LexiconName;
    private String LexiconTableImage;
    private int WordAllNumber;

    public int getLexiconTableId() {
        return LexiconTableId;
    }

    public void setLexiconTableId(int lexiconTableId) {
        LexiconTableId = lexiconTableId;
    }

    public String getLexiconName() {
        return LexiconName;
    }

    public void setLexiconName(String lexiconName) {
        LexiconName = lexiconName;
    }

    public String getLexiconTableImage() {
        return LexiconTableImage;
    }

    public void setLexiconTableImage(String lexiconTableImage) {
        LexiconTableImage = lexiconTableImage;
    }

    public int getWordAllNumber() {
        return WordAllNumber;
    }

    public void setWordAllNumber(int wordAllNumber) {
        WordAllNumber = wordAllNumber;
    }
}
