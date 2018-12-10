package com.mini.dictionary.entity;

public class EnglishChineseDictionary {
    private int WordId;
    private String WordSpell;
    private String WordMeaning;
    private int WordType;
    private String WordSoundmark;
    private String WordPronouncex;
    private String DeriveLenovo;
    private int Whether;

    public int getWordId() {
        return WordId;
    }

    public void setWordId(int wordId) {
        WordId = wordId;
    }

    public String getWordSpell() {
        return WordSpell;
    }

    public void setWordSpell(String wordSpell) {
        WordSpell = wordSpell;
    }

    public String getWordMeaning() {
        return WordMeaning;
    }

    public void setWordMeaning(String wordMeaning) {
        WordMeaning = wordMeaning;
    }

    public int getWordType() {
        return WordType;
    }

    public void setWordType(int wordType) {
        WordType = wordType;
    }

    public String getWordSoundmark() {
        return WordSoundmark;
    }

    public void setWordSoundmark(String wordSoundmark) {
        WordSoundmark = wordSoundmark;
    }

    public String getWordPronouncex() {
        return WordPronouncex;
    }

    public void setWordPronouncex(String wordPronouncex) {
        WordPronouncex = wordPronouncex;
    }

    public String getDeriveLenovo() {
        return DeriveLenovo;
    }

    public void setDeriveLenovo(String deriveLenovo) {
        DeriveLenovo = deriveLenovo;
    }

    public int getWhether() {
        return Whether;
    }

    public void setWhether(int whether) {
        Whether = whether;
    }
}
