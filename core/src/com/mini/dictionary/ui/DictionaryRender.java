package com.mini.dictionary.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.mini.dictionary.ui.layout.DictionaryLayout;
import com.mini.dictionary.util.LoadFile;
import com.mini.dictionary.util.MenuInformation;

public class DictionaryRender implements Disposable {
    private DictionaryLayout dictionaryLayout;
    private SpriteBatch batch;


    public DictionaryRender(DictionaryLayout dictionaryLayout,SpriteBatch batch) {
        this.dictionaryLayout = dictionaryLayout;
        this.batch = batch;
        init();
    }

    public void init() {
    }

    public void render() {
        renderTestObject();
    }

    public void renderTestObject() {
        batch.begin();
        batch.draw( LoadFile.atlas.findRegion("menu_background"),0,0,150,580); //
        // 更改菜单灰色块的位置和大小
        batch.draw( LoadFile.atlas.findRegion("hint"),0,430,150,150); // 更改菜单蓝色块的位置和大小
        batch.end();
        dictionaryLayout.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        dictionaryLayout.dispose();
    }
}
