package com.mini.dictionary.ui.layout.page;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.mini.dictionary.WordJson;
import com.mini.dictionary.ui.button.ButtonFramework;
import com.mini.dictionary.ui.layout.page.dao.OptionPageDao;
import com.mini.dictionary.util.LoadFile;

import java.util.Date;

public class OptionOnePage implements OptionPageDao, Disposable {
    private Stage stage;

    private Image dailySentenceImage;

    private Label label;
    private Label dailySentenceLabel;
    private Date date;

    private ButtonFramework query;
    private ImageTextButton queryButton;
    private WordJson wordJson;

    public OptionOnePage(Stage stage) {
        this.stage = stage;
        init();
    }

    @Override
    public void init() {
        date = new Date(); // 在哪放个日期
        wordJson = new WordJson();
        label = new Label("",new Label.LabelStyle(LoadFile.getFont18(),null));
        label.setPosition(220,380);
        dailySentenceImage = new Image();
        queryButton();
        dailySentenceBox();
    }

    /** 查询按钮*/
    public void queryButton() {
        query = new ButtonFramework();
        query.buttonMessage.setTexturePath("icon/query.png","icon/query-hover.png",null);
        query.buttonMessage.setFont(LoadFile.getFont18());
        query.buttonMessage.setAxis(700,450);
        queryButton = query.createButton();
    }


    public void showMessage() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) ||
                Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) ||
                queryButton.isChecked() ) {
            label.setText(wordJson.init(LoadFile.getSearchBox().getText(),1,0));
        }
        queryButton.setChecked(false);
    }


    /** 每日一句*/
    public void dailySentenceBox() {
        dailySentenceImage.setDrawable(new TextureRegionDrawable(new TextureRegion(LoadFile.getDailySentenceTexture())));
        dailySentenceImage.setPosition(200,250);
        dailySentenceImage.setSize(570,3);
        dailySentenceMessage();
    }
    /** 每日一句信息*/
    public void dailySentenceMessage() {
        dailySentenceLabel = new Label(wordJson.init((date.getDay()+1) + "",
                1,0),new Label.LabelStyle(LoadFile.getFont18(),null));
        dailySentenceLabel.setPosition(220,120);
    }

    @Override
    public void addToStage() {
        stage.addActor(dailySentenceImage);
        stage.addActor(dailySentenceLabel);
        stage.addActor(LoadFile.getSearchBox());
        stage.addActor(label);
        stage.addActor(queryButton);
    }

    @Override
    public void dispose() {
    }
}
