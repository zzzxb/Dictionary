package com.mini.dictionary.ui.layout.page;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.mini.dictionary.WordJson;
import com.mini.dictionary.ui.button.ButtonFramework;
import com.mini.dictionary.ui.layout.page.dao.OptionPageDao;
import com.mini.dictionary.util.LoadFile;

import java.io.IOException;

public class OptionTwoPage implements OptionPageDao, Disposable {
    private Stage stage;

    private Image wordCardImage;
    private Image progressBox;
    private Image progressKnow;
    private Image progressNotKnow;

    private ImageTextButton backButton;
    private ImageTextButton forWardButton;
    private ImageTextButton knowButton;
    private ImageTextButton notKnowButton;
    private ImageTextButton playSound;

//    private Sound wordSound;
    private String word;

    private Label wordLabel;
    private int count = 0;
    private WordJson wordJson;
    private int length;

    public OptionTwoPage(Stage stage) {
        this.stage = stage;
        init();
    }

    @Override
    public void init() {
        wordJson = new WordJson();
        length = Integer.parseInt(wordJson.init("",3,0)) - 8;
        wordLabel = new Label("", new Label.LabelStyle(LoadFile.getFont18(), null));
        wordCardImage = new Image(LoadFile.getWordCardTexture());

        createWordCardBoxAndWordLabel();
        createBackButton();
        createForWardButton();
        createKnowButton();
        createNotKnowButton();
        createProgress();
        createSoundButton();
    }

    /** 向前翻页按钮 */
    public void createBackButton() {
        ButtonFramework buttonFramework= new ButtonFramework();
        buttonFramework.buttonMessage.setTexturePath("icon/back-disabled.png"
                ,"icon/back-hover.png", null);
        buttonFramework.buttonMessage.setFont(LoadFile.getFont18());
        buttonFramework.buttonMessage.setAxis(200, 300);
        backButton = buttonFramework.createButton();
    }

    /** 向后翻页按钮 */
    public void createForWardButton() {
        ButtonFramework buttonFramework= new ButtonFramework();
        buttonFramework.buttonMessage.setTexturePath("icon/forward-disabled.png",
                "icon/forward-hover.png",null);
        buttonFramework.buttonMessage.setFont(LoadFile.getFont18());
        buttonFramework.buttonMessage.setAxis(710,300);
        forWardButton = buttonFramework.createButton();
    }

    /** 知道按钮 */
    public void createKnowButton() {
        ButtonFramework buttonFramework= new ButtonFramework();
        buttonFramework.buttonMessage.setTexturePath("icon/know-disabled.png",
                "icon/know-hover.png",null);
        buttonFramework.buttonMessage.setFont(LoadFile.getFont18());
        buttonFramework.buttonMessage.setAxis(580,100);
        knowButton = buttonFramework.createButton();
    }

    /** 不知道按钮*/
    public void createNotKnowButton() {
        ButtonFramework buttonFramework= new ButtonFramework();
        buttonFramework.buttonMessage.setTexturePath("icon/notknow-disabled.png",
                "icon/notknow-hover.png",null);
        buttonFramework.buttonMessage.setFont(LoadFile.getFont18());
        buttonFramework.buttonMessage.setAxis(280,100);
        notKnowButton = buttonFramework.createButton();
    }

    /** 单词背景和单词显示*/
    public void createWordCardBoxAndWordLabel() {
        wordCardImage.setPosition(280,100); // 单词卡背景图显示位置
        wordLabel.setPosition(300,400); // 单词卡单词显示位置
    }

    /** 进度条 */
    public void createProgress() {
        progressBox = new Image(LoadFile.getProgress(0));
        progressKnow = new Image(LoadFile.getProgress(1));
        progressNotKnow = new Image(LoadFile.getProgress(2));
        progressBox.setPosition(280,520);
        progressKnow.setPosition(280,520);
        progressNotKnow.setPosition(280,520);
        progressBox.setWidth(400);
        progressKnow.setWidth(400);
        progressNotKnow.setWidth(400);
    }

    /** 声音按钮*/
    public void createSoundButton() {
        ButtonFramework buttonFramework = new ButtonFramework();
        buttonFramework.buttonMessage.setTexturePath("icon/daily-pronounce.png",
                "icon/daily-pronounce-hover.png",null);
        playSound = buttonFramework.createButton();
        playSound.setPosition(600, 445);
    }

    /** Actor添加到舞台*/
    @Override
    public void addToStage() {
        stage.addActor(wordCardImage);
        stage.addActor(wordLabel);
        stage.addActor(backButton);
        stage.addActor(forWardButton);
        stage.addActor(knowButton);
        stage.addActor(notKnowButton);
        stage.addActor(progressNotKnow);
        stage.addActor(progressKnow);
        stage.addActor(progressBox);
        stage.addActor(playSound);
    }

    /** 有事件触发才被调用*/
    @Override
    public void showMessage() {
        word = wordJson.init("",2, count);
        wordLabel.setPosition(300,400); // 单词卡单词显示位置
        if (backButton.isChecked()) { // 向前翻页
            count--; backButton.setChecked(false);
        }
        else if (forWardButton.isChecked()){ // 向后翻页
            count++; forWardButton.setChecked(false);
        }
        else if (knowButton.isChecked()) {
            count++; knowButton.setChecked(false);
            try {
                word = wordJson.init("",2, count);
                Runtime.getRuntime().exec("java -jar speech.jar " + word);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (notKnowButton.isChecked()) {
            count++; notKnowButton.setChecked(false);
        }
        else if (playSound.isChecked()) {
            playSound.setChecked(false);
            try{
                // 调用本地文件声音文件发音
//              wordSound = Gdx.audio.newSound(Gdx.files.internal("sound/" + word.toLowerCase() + ".mp3"));
                // 调用百度语音合成接口发音
                Runtime.getRuntime().exec("java -jar speech.jar " + word);
            }catch (Exception e) {
            }
//            wordSound.play();
        }

        count = count < 0 ? 0 : count;
        count = count >= length ? length-1 : count;

        // 显示单词
        wordLabel.setText(word + "\n\n");
        // 进度条显示
        if (count <= length)
            progressKnow.setWidth(count * (400 / length));
    }

    @Override
    public void dispose() {
//        wordSound.dispose();
    }
}
