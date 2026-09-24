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
import com.mini.dictionary.util.TextUtil;
import com.mini.dictionary.util.WordBank;

import java.util.List;

public class OptionOnePage implements OptionPageDao, Disposable {
    private Stage stage;

    private BitmapFont font;
    private Texture dailySentenceTexture;
    private Texture searchBoxTexture;
    private Image dailySentenceImage;

    private TextField searchBox;
    private Label label;
    private Label dailySentenceLabel;
    private String lastQuery; // showMessage 每帧都会被调用, 记一下上次查的词, 没变就不重复算

    private ButtonFramework query;
    private ImageTextButton queryButton;

    public OptionOnePage(Stage stage) {
        this.stage = stage;
        init();
    }

    @Override
    public void init() {
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

    /** 输入框内容变了(或者点了查询按钮/按了回车)才真去查词库 */
    public void showMessage() {
        String query = searchBox.getText() == null ? "" : searchBox.getText().trim();
        boolean clicked = queryButton.isChecked() || Gdx.input.isKeyJustPressed(Input.Keys.ENTER);
        if (queryButton.isChecked()) {
            queryButton.setChecked(false);
        }
        if (!clicked && query.equals(lastQuery)) {
            return;
        }
        lastQuery = query;
        label.setText(lookup(query));
    }

    /** 先精确查, 查不到再给几个"你是不是想找" */
    private String lookup(String query) {
        if (query.isEmpty()) {
            return "";
        }
        WordBank.Word word = WordBank.find(query);
        if (word != null) {
            return word.getWord() + "\n\n" + TextUtil.wrap(word.getExplain(), 60, 3);
        }
        List<WordBank.Word> hits = WordBank.suggest(query, 3);
        String shortQuery = TextUtil.truncate(query, 16);
        if (hits.isEmpty()) {
            return "词库里没有 \"" + shortQuery + "\", 换个拼写试试?";
        }
        StringBuilder builder = new StringBuilder("没找到 \"" + shortQuery + "\", 你是不是想找:");
        for (WordBank.Word hit : hits) {
            builder.append("\n").append(hit.getWord()).append("   ")
                    .append(TextUtil.truncate(hit.getBrief(), 20));
        }
        return builder.toString();
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
