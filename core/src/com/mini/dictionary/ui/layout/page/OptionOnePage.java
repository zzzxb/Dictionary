package com.mini.dictionary.ui.layout.page;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.mini.dictionary.ui.layout.page.dao.OptionPageDao;

public class OptionOnePage implements OptionPageDao, Disposable {
    private Stage stage;
    private Skin skin;
    private TextureAtlas atlas;

    private BitmapFont font;
    private Texture dailySentenceTexture;
    private Image dailySentenceImage;
    private Label dailySentenceLabel;
    private TextField search;

    public OptionOnePage(Stage stage) {
        this.stage = stage;
        init();
    }

    @Override
    public void init() {
//        atlas = new TextureAtlas("uiskin.atlas");
//        skin = new Skin(Gdx.files.internal("uiskin.json"),atlas);
        font = new BitmapFont(Gdx.files.internal("font/cn.fnt"),
                Gdx.files.internal("font/cn.png"),false);
        dailySentenceTexture = new Texture(Gdx.files.internal("icon/boundary.png"));
        dailySentenceImage = new Image();
//        search = new TextField("",skin);
//        searchBox();
        dailySentenceBox();
    }

    /** 搜索框*/
    public void searchBox() {
        search.setSize(200,50);
        search.setMessageText("输入单词");
    }

    /** 每日一句显示框*/
    public void dailySentenceBox() {
        dailySentenceImage.setDrawable(new TextureRegionDrawable(new TextureRegion(dailySentenceTexture)));
        dailySentenceImage.setPosition(200,250);
        dailySentenceImage.setSize(570,3);
        dailySentenceMessage();
    }
    /** 每日一句信息*/
    public void dailySentenceMessage() {
        dailySentenceLabel = new Label("No one can make you feel inferior without your consent.\n" +
                "\n未经你的许可,没有人可以让你觉得低人一等.",
                new Label.LabelStyle(font,null));
        dailySentenceLabel.setPosition(200,120);
    }

    @Override
    public void addToStage() {
        stage.addActor(dailySentenceImage);
        stage.addActor(dailySentenceLabel);
//        stage.addActor(search);
    }

    @Override
    public void dispose() {
        dailySentenceTexture.dispose();
//        skin.dispose();
    }
}
