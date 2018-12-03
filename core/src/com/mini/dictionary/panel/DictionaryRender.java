package com.mini.dictionary.panel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class DictionaryRender implements Disposable {
    private Stage stage;
    private SpriteBatch batch;
    private DictionaryLayout dictionaryLayout;

    public DictionaryRender(DictionaryLayout dictionaryLayout) {
        stage = new Stage();
        this.dictionaryLayout = dictionaryLayout;
        init();
    }
    /** 初始化布局*/
    public void init(){
        Gdx.input.setInputProcessor(stage);
        stage.addActor(dictionaryLayout.button);
        batch = new SpriteBatch();
    }

    /** 渲染图像*/
    public void render() {
        renderTestObject();
    }

    public void renderTestObject() {
        // 在begin和end中间写渲染界面
        batch.begin();
        stage.act();
        stage.draw();
        batch.end();
    }

    @Override
    /** 释放资源*/
    public void dispose() {
        batch.dispose();
    }
}
