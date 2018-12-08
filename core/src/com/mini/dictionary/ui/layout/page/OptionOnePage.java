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
import com.mini.dictionary.ui.button.ButtonFramework;
import com.mini.dictionary.ui.layout.page.dao.OptionPageDao;

import java.util.Date;

public class OptionOnePage implements OptionPageDao, Disposable {
    private Stage stage;

    private BitmapFont font;
    private Texture dailySentenceTexture;
    private Texture searchBoxTexture;
    private Image dailySentenceImage;

    private TextField searchBox;
    private Label label;
    private Label dailySentenceLabel;
    private Date date;

    private ButtonFramework query;
    private ImageTextButton queryButton;

    public OptionOnePage(Stage stage) {
        this.stage = stage;
        init();
    }

    @Override
    public void init() {
        date = new Date(); // 在哪放个日期
        font = new BitmapFont(Gdx.files.internal("font/font18.fnt"),
                Gdx.files.internal("font/font18.png"),false);
        label = new Label("",new Label.LabelStyle(font,null));
        label.setPosition(220,380);
        dailySentenceTexture = new Texture(Gdx.files.internal("icon/boundary.png"));
        searchBoxTexture = new Texture(Gdx.files.internal("icon/search.png"));
        dailySentenceImage = new Image();
        searchBox();
        queryButton();
        dailySentenceBox();
    }

    /** 查询按钮*/
    public void queryButton() {
        query = new ButtonFramework();
        query.buttonMessage.setTexturePath("icon/query.png","icon/query-hover.png",null);
        query.buttonMessage.setFont(font);
        query.buttonMessage.setAxis(700,450);
        queryButton = query.createButton();
    }

    /** 搜索框*/
    public void searchBox() {
        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.background = new TextureRegionDrawable(new TextureRegion(searchBoxTexture));
        style.cursor = new TextureRegionDrawable(new TextureRegion(createCursorTexture()));
        style.font = font;
        style.fontColor = new Color(1,1,1,1);
        searchBox = new TextField("",style);
        searchBox.setPosition(200,450);
        searchBox.setSize(500,50);
        searchBox.setMessageText("请输入需要查询的单词...");
    }

    public void showMessage() {
//        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) ||
//                Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) ||
//                queryButton.isChecked() ) {
//            queryButton.setChecked(false);
//        }
        if (searchBox.getText() != null || searchBox.getText() != "")
            if (searchBox.getText().toLowerCase().equals("happy"))
                label.setText("adj. 高兴的; 愉快的; 开心的;");
            else if (searchBox.getText().toLowerCase().equals("memory"))
                label.setText("n. 记忆,记忆力;内存,[计]存储器,回忆;");
            else if (searchBox.getText().equals("赵鹏"))
                label.setText("大帅哥");
            else if (searchBox.getText().equals("慎东海"))
                label.setText("大丑逼");
            else
                label.setText(searchBox.getText());
    }

    /** 光标*/
    private Texture createCursorTexture() {
        Pixmap pixmap = new Pixmap(1, 16, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 1);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    /** 每日一句*/
    public void dailySentenceBox() {
        dailySentenceImage.setDrawable(new TextureRegionDrawable(new TextureRegion(dailySentenceTexture)));
        dailySentenceImage.setPosition(200,250);
        dailySentenceImage.setSize(570,3);
        dailySentenceMessage();
    }
    /** 每日一句信息*/
    public void dailySentenceMessage() {
        dailySentenceLabel = new Label("No one can make you feel inferior without your consent.\n" +
                "\n未经你的许可,没有人可以让你觉得低人一等.",new Label.LabelStyle(font,null));
        dailySentenceLabel.setPosition(220,120);
    }

    @Override
    public void addToStage() {
        stage.addActor(dailySentenceImage);
        stage.addActor(dailySentenceLabel);
        stage.addActor(searchBox);
        stage.addActor(label);
        stage.addActor(queryButton);
    }

    @Override
    public void dispose() {
        searchBoxTexture.dispose();
        dailySentenceTexture.dispose();
    }
}
