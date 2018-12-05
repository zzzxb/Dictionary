package com.mini.dictionary.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class SelectionMenu {
    private Stage stage;
    private BitmapFont font;

    // 选项按钮
    private Button buttonOne;
    private Button buttonTwo;
    private Button setUpButton;

    // 选项纹理
    private Texture upTextureOne; // 抬起
    private Texture overTextureOne; // 经过
    private Texture upTextureTwo;
    private Texture overTextureTwo;
    private Texture downTexture; // 点击
    private Texture upTextureSet;
    private Texture overTextureSet;

    private int heigh = 430;

    public SelectionMenu(Stage stage) {
        this.stage = stage;
        init();
    }

    public void init() {
        font = new BitmapFont(Gdx.files.internal("font/menu.fnt"),
                Gdx.files.internal("font/menu.png"),false);

        downTexture = new Texture(Gdx.files.internal("icon/select.png"));

        optionOne();
        optionTwo();
        setButton();
    }

    public void optionOne() {
        upTextureOne = new Texture(Gdx.files.internal("icon/dict.png"));
        overTextureOne = new Texture(Gdx.files.internal("icon/dict-hover.png"));
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.font = font; // 设置字体
        style.imageUp = new TextureRegionDrawable(new TextureRegion(upTextureOne));
        style.imageOver = new TextureRegionDrawable(new TextureRegion(overTextureOne));
        style.imageChecked = new TextureRegionDrawable(new TextureRegion(overTextureOne)); // 点击后图标改变
        style.checked = new TextureRegionDrawable(new TextureRegion(downTexture)); // 点击后背景改变
        buttonOne = new ImageTextButton("查单词",style);
        buttonOne.setPosition(0,heigh - (1 * 45));
        stage.addActor(buttonOne); // 添加按钮到舞台
    }

    public void optionTwo() {
        upTextureTwo = new Texture(Gdx.files.internal("icon/wb.png"));
        overTextureTwo = new Texture(Gdx.files.internal("icon/wb-hover.png"));
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.font = font; // 设置字体
        style.imageUp = new TextureRegionDrawable(new TextureRegion(upTextureTwo));
        style.imageOver = new TextureRegionDrawable(new TextureRegion(overTextureTwo));
        style.imageChecked = new TextureRegionDrawable(new TextureRegion(overTextureTwo)); // 点击后图标改变
        style.checked = new TextureRegionDrawable(new TextureRegion(downTexture)); // 点击后背景改变
        buttonTwo = new ImageTextButton("背单词",style);
        buttonTwo.setPosition(0,heigh - (2 * 45));
        stage.addActor(buttonTwo); // 添加按钮到舞台
    }

    public void setButton() {
        upTextureSet = new Texture(Gdx.files.internal("icon/wb-setting-normal.png"));
        overTextureSet = new Texture(Gdx.files.internal("icon/wb-setting-hover.png"));
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(upTextureSet));
        style.imageOver = new TextureRegionDrawable(new TextureRegion(overTextureSet));
        style.imageChecked = new TextureRegionDrawable(new TextureRegion(overTextureSet));
        setUpButton = new ImageButton(style);
        setUpButton.setPosition(10,10);
        stage.addActor(setUpButton);
    }

    // 测试事件
    public void event() {
        if (buttonOne.isPressed()) {
            if (buttonOne.isChecked())
                buttonOne.setDisabled(true);
            else
                buttonOne.setDisabled(false);
            buttonTwo.setChecked(false);
            setUpButton.setChecked(false);
        }
        if (buttonTwo.isPressed()) {
            if (buttonTwo.isChecked())
                buttonTwo.setDisabled(true);
            else
                buttonTwo.setDisabled(false);
            buttonOne.setChecked(false);
            setUpButton.setChecked(false);
        }
        if (setUpButton.isPressed()) {
            if (setUpButton.isChecked())
                setUpButton.setDisabled(true);
            else
                setUpButton.setDisabled(false);
            buttonOne.setChecked(false);
            buttonTwo.setChecked(false);
        }
    }

    public void dispose() {
        if (upTextureOne != null)
            upTextureOne.dispose();
        if (overTextureOne != null)
            overTextureOne.dispose();
        if (upTextureTwo != null)
            upTextureTwo.dispose();
        if (overTextureTwo != null)
            overTextureTwo.dispose();
        if (downTexture != null)
            downTexture.dispose();
        if (upTextureSet != null)
            upTextureSet.dispose();
    }
}
