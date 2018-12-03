package com.mini.dictionary.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class DictionaryRender implements Disposable {
    private DictionaryLayout dictionaryLayout;
    private SpriteBatch batch;

    public DictionaryRender(DictionaryLayout dictionaryLayout) {
        this.dictionaryLayout = dictionaryLayout;
        init();
    }

    public void init() {
        batch = new SpriteBatch();
    }

    public void render() {
        renderTestObject();
    }

    public void renderTestObject() {
        batch.begin();
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
