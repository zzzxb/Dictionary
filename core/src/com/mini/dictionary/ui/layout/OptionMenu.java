package com.mini.dictionary.ui.layout;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.mini.dictionary.ui.UiKit;
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
    private Boolean buttonClick = true; // 按钮点击标志

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
        if (itb.isChecked() && buttonClick) { // 如果按钮被点击则刷新页面
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
            buttonClick = false;
        }
        if (itb.isChecked() && itb == buttonOne)
            optionOnePage.showMessage();
        if (itb.isChecked() && itb == buttonTwo)
            optionTwoPage.showMessage();
        // 新加的页面靠 showMessage() 做每帧刷新(自动下一题/数据变了就重画)
        if (itb.isChecked() && itb == buttonThree)
            optionThreePage.showMessage();
        if (itb.isChecked() && itb == buttonFour)
            optionFourPage.showMessage();
        if (itb.isChecked() && itb == buttonFive)
            optionFivePage.showMessage();
        if (itb.isChecked() && itb == buttonSix)
            optionSixPage.showMessage();
        if (itb.isChecked() && itb == settingButton)
            optionSettingPage.showMessage();
    }

    /** 新功能按钮的小图标是用代码画出来的, 这里换掉 ButtonFramework 里的占位图 */
    private void applyIcon(ImageTextButton button, String kind) {
        if (button == null)
            return;
        button.getStyle().imageUp = UiKit.icon(kind);
        button.getStyle().imageOver = UiKit.icon(kind);
        button.getStyle().imageChecked = UiKit.icon(kind);
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
        // 选中的按钮会被置为 disabled, 这里跳过它, 顺便把"要恢复可点"的事情交给下面的循环:
        // 老代码 stage.clear() 之后按钮的触摸焦点就丢了, 松手事件送不到, isPressed() 会一直为 true,
        // 于是选中的按钮每帧都重复 clear/重建, 点别的按钮时两个按钮来回抢, 页面就再也切不过去;
        // 但光跳过 disabled 也不行 - 不把其它按钮 setDisabled(false), 点过一次的按钮就再也回不去了。
        if (itb.isPressed() && !itb.isDisabled()) {
            buttonClick = true;  // 如果按钮被点击标志为true
            stage.clear();
            readdButtonToStage();
            changePage(itb);
            itb.setChecked(true);
            itb.setDisabled(true);
            itb.getClickListener().cancel(); // 结束这次按压, 免得 isPressed() 一直挂着
            for (ImageTextButton button : buttonGroup) {
                if (button == itb)
                    continue;
                else {
                    button.setChecked(false);
                    button.setDisabled(false); // 没选中的按钮要恢复可点, 不然点过一次就再也回不去了
                }
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
        applyIcon(buttonThree, "book");
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
        applyIcon(buttonFour, "list");
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
        applyIcon(buttonFive, "check");
        stage.addActor(buttonFive); // 原来这里忘了加进舞台, 5 号按钮一直显示不出来
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
        applyIcon(buttonSix, "chart");
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
