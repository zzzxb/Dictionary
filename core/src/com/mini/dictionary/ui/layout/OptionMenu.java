package com.mini.dictionary.ui.layout;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.mini.dictionary.ui.button.ButtonFramework;

import java.util.ArrayList;
import java.util.List;

import static com.mini.dictionary.util.MenInformation.*;


public class OptionMenu {
    private Stage stage;
    // 选项按钮
    private ImageTextButton buttonOne;
    private ImageTextButton buttonTwo;
    private ImageTextButton buttonThree;
    private ImageTextButton buttonFour;
    private ImageTextButton buttonFive;
    private ImageTextButton buttonSix;
    private ImageTextButton settingButton;

    private List<ImageTextButton> buttonGroup;

    public OptionMenu(Stage stage) {
        this.stage = stage;
        init();
    }

    public void init() {
        buttonGroup = new ArrayList<ImageTextButton>();
        for (int i = 0; i <= 6 ; i++) {
            if (OPENBUTTON[i])
                switch (i) {
                    case 0 : createFirstButton(); buttonGroup.add(buttonOne); break;
                    case 1 : createSecondButton(); buttonGroup.add(buttonTwo); break;
                    case 2 : createThirdButton(); buttonGroup.add(buttonThree); break;
                    case 3 : createFourthButton(); buttonGroup.add(buttonFour); break;
                    case 4 : createFifthButton(); buttonGroup.add(buttonFive); break;
                    case 5 : createSixthButton(); buttonGroup.add(buttonSix);break;
                    case 6 : createSettingButton(); buttonGroup.add(settingButton);break;
                }
        }
    }

    public void buttonEvent () {
        // 先缕缕思路，明天再写
        for (ImageTextButton itb : buttonGroup)
            buttonChecked(itb);

    }

    // 改变页,点击不同的按钮显示不同的页面
    public void changePage (ImageTextButton itb) {
        // 累了，休息休息明天缕缕思路再写
        if (!itb.isChecked()) {
            if (itb == buttonOne){
                // 一个页面
            }
            else if (itb == buttonTwo) {
                // 另一个页面
            }
        }
    }

    // Button选中效果- ButtonGroup中只能有一个被选中
    public void buttonChecked(ImageTextButton itb) {
        if (itb.isPressed()) {
            changePage(itb);
            itb.setChecked(true);
            itb.setDisabled(true);
            for (ImageTextButton button : buttonGroup) {
                if (button == itb)
                    continue;
                else
                    button.setChecked(false);
            }
        }
    }

    /** 创建第一个位置上的按钮*/
    public void createFirstButton () {
        ButtonFramework buttonFramework = new ButtonFramework();
        buttonFramework.buttonMessage.setText(BUTTONTEXT[0]);
        buttonFramework.buttonMessage.setTexturePath (BUTTONONEPATH[0],BUTTONONEPATH[1],BUTTONONEPATH[2]);
        buttonFramework.buttonMessage.setFontFilePath(FONTPATH[0],FONTPATH[1]);
        buttonFramework.buttonMessage.setAxis(0,FRAMEHEIGHT - (1 * BUTTONHEIGHT));
        buttonOne = buttonFramework.createButton();
        stage.addActor(buttonOne);
    }

    /** 创建第二个位置上的按钮*/
    public void createSecondButton() {
        ButtonFramework buttonFramework = new ButtonFramework();
        buttonFramework.buttonMessage.setText(BUTTONTEXT[1]);
        buttonFramework.buttonMessage.setTexturePath (BUTTONTWOPATH[0],BUTTONTWOPATH[1],BUTTONTWOPATH[2]);
        buttonFramework.buttonMessage.setFontFilePath(FONTPATH[0],FONTPATH[1]);
        buttonFramework.buttonMessage.setAxis(0,FRAMEHEIGHT - (2 * BUTTONHEIGHT));
        buttonTwo = buttonFramework.createButton();
        stage.addActor(buttonTwo);
    }

    /** 第三个按钮*/
    public void createThirdButton() {
        ButtonFramework buttonFramework = new ButtonFramework();
        buttonFramework.buttonMessage.setText(BUTTONTEXT[2]);
        buttonFramework.buttonMessage.setTexturePath (BUTTONTHREEPATH[0],BUTTONTHREEPATH[1],BUTTONTHREEPATH[2]);
        buttonFramework.buttonMessage.setFontFilePath(FONTPATH[0],FONTPATH[1]);
        buttonFramework.buttonMessage.setAxis(0,FRAMEHEIGHT - (3 * BUTTONHEIGHT));
        buttonThree = buttonFramework.createButton();
        stage.addActor(buttonThree);
    }

    /** 第四个按钮*/
    public void createFourthButton() {
        ButtonFramework buttonFramework = new ButtonFramework();
        buttonFramework.buttonMessage.setText(BUTTONTEXT[3]);
        buttonFramework.buttonMessage.setTexturePath (BUTTONFOURPATH[0],BUTTONFOURPATH[1],BUTTONFOURPATH[2]);
        buttonFramework.buttonMessage.setFontFilePath(FONTPATH[0],FONTPATH[1]);
        buttonFramework.buttonMessage.setAxis(0,FRAMEHEIGHT - (4 * BUTTONHEIGHT));
        buttonFour = buttonFramework.createButton();
        stage.addActor(buttonFour);
    }

    /** 第五个按钮*/
    public void createFifthButton() {
        ButtonFramework buttonFramework = new ButtonFramework();
        buttonFramework.buttonMessage.setText(BUTTONTEXT[4]);
        buttonFramework.buttonMessage.setTexturePath (BUTTONFIVEPATH[0],BUTTONFIVEPATH[1],BUTTONFIVEPATH[2]);
        buttonFramework.buttonMessage.setFontFilePath(FONTPATH[0],FONTPATH[1]);
        buttonFramework.buttonMessage.setAxis(0,FRAMEHEIGHT - (5 * BUTTONHEIGHT));
        buttonFive = buttonFramework.createButton();
        stage.addActor(buttonFive);
    }

    /** 第六个按钮*/
    public void createSixthButton() {
        ButtonFramework buttonFramework = new ButtonFramework();
        buttonFramework.buttonMessage.setText(BUTTONTEXT[5]);
        buttonFramework.buttonMessage.setTexturePath (BUTTONSIXPATH[0],BUTTONSIXPATH[1],BUTTONSIXPATH[2]);
        buttonFramework.buttonMessage.setFontFilePath(FONTPATH[0],FONTPATH[1]);
        buttonFramework.buttonMessage.setAxis(0,FRAMEHEIGHT - (6 * BUTTONHEIGHT));
        buttonSix = buttonFramework.createButton();
        stage.addActor(buttonSix);
    }

    /** 设置按钮*/
    public void createSettingButton() {
        ButtonFramework buttonFramework = new ButtonFramework();
        buttonFramework.buttonMessage.setTexturePath (SETTINGBUTTONPATH[0],SETTINGBUTTONPATH[1],null);
        buttonFramework.buttonMessage.setFontFilePath(FONTPATH[0],FONTPATH[1]);
        buttonFramework.buttonMessage.setAxis(SETTINGBUTTONPOSITION[0],SETTINGBUTTONPOSITION[1]);
        settingButton = buttonFramework.createButton();
        stage.addActor(settingButton);
    }
}
