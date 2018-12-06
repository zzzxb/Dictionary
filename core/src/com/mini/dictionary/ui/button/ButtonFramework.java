package com.mini.dictionary.ui.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.mini.dictionary.ui.button.entity.ButtonInformation;

/** 创建ImageTextButton的框架*/
public class ButtonFramework implements Disposable {
    public ButtonInformation buttonMessage; // 设置按钮信息
    private BitmapFont font;
    private ImageTextButton button;
    private ImageTextButton.ImageTextButtonStyle style;
    private Texture upTexture;
    private Texture overTexture;
    private Texture downTexture;

    public ButtonFramework() {
        buttonMessage = new ButtonInformation();
    }

    /** 创建按钮*/
    public ImageTextButton createButton() {
        setTextures();
        setFont();
        setStyle();
        setButton();
        return button;
    }

    /** 设置按钮*/
    public void setButton() {
        button = new ImageTextButton(buttonMessage.getText(),style);
        button.setPosition(buttonMessage.getxAxis(),buttonMessage.getyAxis());
    }

    /** 设置按钮风格 */
    public void setStyle() {
        style = new ImageTextButton.ImageTextButtonStyle();
        style.font = font;
        if (upTexture != null)
            style.imageUp = new TextureRegionDrawable(new TextureRegion(upTexture));
        if (overTexture != null) {
            style.imageOver = new TextureRegionDrawable(new TextureRegion(overTexture));
            style.imageChecked = new TextureRegionDrawable(new TextureRegion(overTexture));
        }
        if (downTexture != null)
            style.checked = new TextureRegionDrawable(new TextureRegion(downTexture));
    }

    /** 为按钮添加字体文件*/
    public void setFont() {
        font = new BitmapFont(Gdx.files.internal(buttonMessage.getFontFntPath()),
                Gdx.files.internal(buttonMessage.getFontPngPath()),false);
    }

    /** 设置按钮纹理 */
    public void setTextures() {
        if (buttonMessage.getUpTexturePath() != null)
            upTexture = new Texture(Gdx.files.internal
                    (buttonMessage.getUpTexturePath())); // 抬起纹理
        if (buttonMessage.getOverTexturePath() != null)
            overTexture = new Texture(Gdx.files.internal
                    (buttonMessage.getOverTexturePath())); // 经过纹理
        if (buttonMessage.getDownTexturePath() != null)
            downTexture = new Texture(Gdx.files.
                    internal(buttonMessage.getDownTexturePath())); // 按下纹理
    }

    /** 释放资源 */
    @Override
    public void dispose () {
        if (upTexture != null)
            upTexture.dispose();
        if (overTexture != null)
            overTexture.dispose();
        if (downTexture != null)
            downTexture.dispose();
    }
}
