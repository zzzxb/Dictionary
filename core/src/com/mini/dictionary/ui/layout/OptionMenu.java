package com.mini.dictionary.ui.layout;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.mini.dictionary.ui.button.ButtonFramework;
import com.mini.dictionary.ui.layout.page.*;
import com.mini.dictionary.ui.layout.page.dao.OptionPageDao;

import java.util.ArrayList;
import java.util.List;

import static com.mini.dictionary.util.MenuInformation.*;


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
    // 选项编组
    private List<ImageTextButton> buttonGroup;
    // 选项内容页
    private OptionPageDao optionOnePage;
    private OptionPageDao optionTwoPage;
    private OptionPageDao optionThreePage;
    private OptionPageDao optionFourPage;
    private OptionPageDao optionFivePage;
    private OptionPageDao optionSixPage;
    private OptionPageDao optionSettingPage;

    public OptionMenu(Stage stage) {
        this.stage = stage;
        init();
    }

    public void init() {
        buttonGroup = new ArrayList<ImageTextButton>();
        for (int i = 0; i <= 6 ; i++) {
            if (OPENBUTTON[i]) // 只有打开按钮显示的才能被实例化和添加到buttonGroup
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

    /** 菜单按钮事件*/
    public void buttonEvent () {
        defaultOption(); // 设置默认选中选项
        for (ImageTextButton itb : buttonGroup) {
            changePage(itb);
            buttonChecked(itb);
        }

    }

    /** 改变页,点击不同的按钮显示不同的页面 */
    public void changePage (ImageTextButton itb) {
        if (itb.isChecked()) {
            stage.clear();
            readdButtonToStage();
            if (itb == buttonOne)
                optionOnePage.addToStage();
            else if (itb == buttonTwo)
                optionTwoPage.addToStage();
            else if (itb == buttonThree)
                optionThreePage.addToStage();
            else if (itb == buttonFour)
                optionFourPage.addToStage();
            else if (itb == buttonFive)
                optionFivePage.addToStage();
            else if (itb == buttonSix)
                optionSixPage.addToStage();
            else if (itb == settingButton)
                optionSettingPage.addToStage();
        }
    }

    /** 默认选项 - 打开软件默认选中一个按钮 */
    public void defaultOption() {
        if (defaultOptionOn >= 0 && defaultOptionOn<= 5) {
            buttonGroup.get(defaultOptionOn).setChecked(true);
            buttonGroup.get(defaultOptionOn).setDisabled(true);
            defaultOptionOn = -1;
        }
    }

    /** Button选中效果- ButtonGroup中只能有一个被选中 */
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
        optionOnePage = new OptionOnePage(stage); // 创建选项内容页
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
        optionTwoPage = new OptionTwoPage(stage);
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
        optionThreePage = new OptionThreePage(stage);
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
        optionFourPage = new OptionFourPage(stage);
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
        optionFivePage = new OptionFivePage(stage);
        ButtonFramework buttonFramework = new ButtonFramework();
        buttonFramework.buttonMessage.setText(BUTTONTEXT[4]);
        buttonFramework.buttonMessage.setTexturePath (BUTTONFIVEPATH[0],BUTTONFIVEPATH[1],BUTTONFIVEPATH[2]);
        buttonFramework.buttonMessage.setFontFilePath(FONTPATH[0],FONTPATH[1]);
        buttonFramework.buttonMessage.setAxis(0,FRAMEHEIGHT - (5 * BUTTONHEIGHT));
        buttonFive = buttonFramework.createButton();
    }

    /** 第六个按钮*/
    public void createSixthButton() {
        optionSixPage = new OptionSixPage(stage);
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
        optionSettingPage = new OptionSettingPage(stage);
        ButtonFramework buttonFramework = new ButtonFramework();
        buttonFramework.buttonMessage.setTexturePath (SETTINGBUTTONPATH[0],SETTINGBUTTONPATH[1],null);
        buttonFramework.buttonMessage.setFontFilePath(FONTPATH[0],FONTPATH[1]);
        buttonFramework.buttonMessage.setAxis(SETTINGBUTTONPOSITION[0],SETTINGBUTTONPOSITION[1]);
        settingButton = buttonFramework.createButton();
        stage.addActor(settingButton);
    }

    /** 添加按钮到舞台 */
    public void readdButtonToStage() {
        for (ImageTextButton itb: buttonGroup) {
            stage.addActor(itb);
        }
    }
}
