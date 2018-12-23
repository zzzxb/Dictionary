package com.mini.dictionary.ui.layout.page;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mini.dictionary.ui.layout.page.dao.OptionPageDao;

public class OptionSettingPage implements OptionPageDao {
    private Stage stage;

    private BitmapFont font;
    private Label label;

    public OptionSettingPage(Stage stage) {
        this.stage = stage;
        init();
    }

    @Override
    public void init() {
        font = new BitmapFont(Gdx.files.internal("font/font18.fnt"),
                Gdx.files.internal("font/font18.png"),false);
        label = new Label("Version: 1.0\n作者: Zzzxb & Sssdh",new Label.LabelStyle(font,null));
        label.setPosition(200,500);
    }

    @Override
    public void addToStage() {
        stage.addActor(label);
    }

    @Override
    public void showMessage() {
    }
}
