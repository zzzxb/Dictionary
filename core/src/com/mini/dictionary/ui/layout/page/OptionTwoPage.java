package com.mini.dictionary.ui.layout.page;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.mini.dictionary.ui.layout.page.dao.OptionPageDao;

public class OptionTwoPage implements OptionPageDao, Disposable {
    private Stage stage;

    private BitmapFont font;
    private Texture wordCardTexture;
    private Image wordCardImage;
    private Label wordLabel;

    public OptionTwoPage(Stage stage) {
        this.stage = stage;
        init();
    }

    @Override
    public void init() {
        font = new BitmapFont(Gdx.files.internal("font/font18.fnt"),
                Gdx.files.internal("font/font18.png"),false);
        wordCardTexture = new Texture(Gdx.files.internal("icon/wordcard.png"));
        wordCardImage = new Image(wordCardTexture);
        wordLabel = new Label("",new Label.LabelStyle(font, null));

        wordLabel.setPosition(300,400);
        wordCardImage.setPosition(280,100);
        wordLabel.setText("Happy\n\n" + "adj. 幸福的; 高兴的; 巧妙地;\n\n" + "n.(Happy)人名;()");
    }

    @Override
    public void addToStage() {
        stage.addActor(wordCardImage);
        stage.addActor(wordLabel);
    }

    @Override
    public void showMessage() {
    }

    @Override
    public void dispose() {
        wordCardTexture.dispose();
    }
}
