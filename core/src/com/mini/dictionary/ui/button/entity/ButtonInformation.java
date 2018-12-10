package com.mini.dictionary.ui.button.entity;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class ButtonInformation {
    private String text;
    private String upTexturePath; // 抬起纹理
    private String overTexturePath; // 经过纹理
    private String downTexturePath; // 点击纹理
    private String fontFntPath = "font/default.fnt";
    private String fontPngPath = "font/default.png";

    public void setFontFilePath(String fontFntPath, String fontPngPath) {
        this.fontFntPath = fontFntPath;
        this.fontPngPath = fontPngPath;
    }

    public BitmapFont setFont(BitmapFont font) {
        return font;
    }

    public String getFontFntPath() {
        return fontFntPath;
    }

    public String getFontPngPath() {
        return fontPngPath;
    }

    private float xAxis; // x轴坐标
    private float yAxis; // y轴坐标

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUpTexturePath() {
        return upTexturePath;
    }

    public void setTexturePath(String up, String over, String down) {
        this.upTexturePath = up;
        this.overTexturePath = over;
        this.downTexturePath = down;
    }

    public String getOverTexturePath() {
        return overTexturePath;
    }

    public String getDownTexturePath() {
        return downTexturePath;
    }

    public void setAxis(float xAxis, float yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public float getxAxis() {
        return xAxis;
    }

    public float getyAxis() {
        return yAxis;
    }
}
