package com.mini.dictionary.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class DictionaryRender implements Disposable {
    private DictionaryLayout dictionaryLayout;
    private SpriteBatch batch;

    private Texture menuTexture;
    private Texture hintTexture;

    public DictionaryRender(DictionaryLayout dictionaryLayout) {
        this.dictionaryLayout = dictionaryLayout;
        init();
    }

    public void init() {
        batch = new SpriteBatch();
        menuTexture = new Texture(Gdx.files.internal("icon/menu_background.png"));
        hintTexture = new Texture(Gdx.files.internal("icon/hint.png"));
    }

    public void render() {
        renderTestObject();
    }

    public void renderTestObject() {
        batch.begin();
        batch.draw(menuTexture,0,0,150,580);
        batch.draw(hintTexture,0,430,150,150);
        batch.end();
        dictionaryLayout.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        menuTexture.dispose();
        hintTexture.dispose();
        dictionaryLayout.dispose();
    }
}
