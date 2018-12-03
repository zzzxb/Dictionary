package com.mini.dictionary.panel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class DictionaryLayout {
    public Button button;
    private Texture upTexture;
    private Texture downTexture;

    public DictionaryLayout() {
        upTexture = new Texture(Gdx.files.internal("core/assets/b1.png"));
        downTexture = new Texture(Gdx.files.internal("core/assets/b2.png"));
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(upTexture));
        style.down = new TextureRegionDrawable(new TextureRegion(downTexture));
        button = new Button(style);
        button.setPosition(100, 200);
        init();
    }

    public void init() {
        leftLayout();
        upperRight();
        bottomRight();
    }

    public void leftLayout() {
    }

    public void upperRight() {}

    public void bottomRight() {}
}
