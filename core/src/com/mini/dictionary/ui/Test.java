package com.mini.dictionary.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Test {
    public Label label;
    public Test (BitmapFont font) {
        label = new Label("hello",new Label.LabelStyle(font,null));
        label.setPosition(500,300);
    }
}
