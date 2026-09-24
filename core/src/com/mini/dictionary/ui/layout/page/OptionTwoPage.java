package com.mini.dictionary.ui.layout.page;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.mini.dictionary.ui.button.ButtonFramework;
import com.mini.dictionary.ui.layout.page.dao.OptionPageDao;
import com.mini.dictionary.util.StudyData;
import com.mini.dictionary.util.TextUtil;
import com.mini.dictionary.util.WordBank;

import java.util.ArrayList;
import java.util.List;

public class OptionTwoPage implements OptionPageDao, Disposable {
    private Stage stage;

    private BitmapFont font18;
    private BitmapFont font28;
    private Texture wordCardTexture;
    private Texture progress;
    private Texture progress1;
    private Texture progress2;
    private Image wordCardImage;
    private Image progressBox;
    private Image progressKnow;
    private Image progressNotKnow;

    private ImageTextButton backButton;
    private ImageTextButton forWardButton;
    private ImageTextButton knowButton;
    private ImageTextButton notKnowButton;
    private ImageTextButton playSound;

    private Sound wordSound;

    private Label wordLabel;
    private Label explainLabel;
    // 词表改成从 character.json 里按"每日目标"抽, 不再是写死的三个词
    private List<WordBank.Word> cards = new ArrayList<WordBank.Word>();
    private int cardGoal = -1; // 已经按哪个目标抽过词了
    private int count = 0;
    private int count1 = 0;

    public OptionTwoPage(Stage stage) {
        this.stage = stage;
        init();
    }

    @Override
    public void init() {
        font18 = new BitmapFont(Gdx.files.internal("font/font18.fnt"),
                Gdx.files.internal("font/font18.png"),false);
        font28 = new BitmapFont(Gdx.files.internal("font/myfont.fnt"),
                Gdx.files.internal("font/myfont.png"),false);

        wordCardTexture = new Texture(Gdx.files.internal("icon/wordcard.png"));
        wordLabel = new Label("", new Label.LabelStyle(font28, null));
        explainLabel = new Label("", new Label.LabelStyle(font18, null));
        wordCardImage = new Image(wordCardTexture);

        createWordCardBoxAndWordLabel();
        createBackButton();
        createForWardButton();
        createKnowButton();
        createNotKnowButton();
        createProgress();
        createSoundButton();
        loadCards();
    }

    /** 按"每日目标"从词库里抽今天要背的词; 同一天抽到的是同一批, 重启后进度条还对得上 */
    private void loadCards() {
        cardGoal = StudyData.getDailyGoal();
        cards = WordBank.dailyCards(cardGoal);
        count = 0;
        count1 = 0;
    }

    /** 当前这张卡, 下标越界时兜底 */
    private WordBank.Word currentCard() {
        if (cards.isEmpty()) {
            return null;
        }
        int index = Math.max(0, Math.min(count, cards.size() - 1));
        return cards.get(index);
    }

    /** 向前翻页按钮 */
    public void createBackButton() {
        ButtonFramework buttonFramework= new ButtonFramework();
        buttonFramework.buttonMessage.setTexturePath("icon/back-disabled.png"
                ,"icon/back-hover.png", null);
        buttonFramework.buttonMessage.setFont(font18);
        buttonFramework.buttonMessage.setAxis(200, 300);
        backButton = buttonFramework.createButton();
    }

    /** 向后翻页按钮 */
    public void createForWardButton() {
        ButtonFramework buttonFramework= new ButtonFramework();
        buttonFramework.buttonMessage.setTexturePath("icon/forward-disabled.png",
                "icon/forward-hover.png",null);
        buttonFramework.buttonMessage.setFont(font18);
        buttonFramework.buttonMessage.setAxis(710,300);
        forWardButton = buttonFramework.createButton();
    }

    /** 知道按钮 */
    public void createKnowButton() {
        ButtonFramework buttonFramework= new ButtonFramework();
        buttonFramework.buttonMessage.setTexturePath("icon/know-disabled.png",
                "icon/know-hover.png",null);
        buttonFramework.buttonMessage.setFont(font18);
        buttonFramework.buttonMessage.setAxis(580,100);
        knowButton = buttonFramework.createButton();
    }

    /** 不知道按钮*/
    public void createNotKnowButton() {
        ButtonFramework buttonFramework= new ButtonFramework();
        buttonFramework.buttonMessage.setTexturePath("icon/notknow-disabled.png",
                "icon/notknow-hover.png",null);
        buttonFramework.buttonMessage.setFont(font18);
        buttonFramework.buttonMessage.setAxis(280,100);
        notKnowButton = buttonFramework.createButton();
    }

    /** 单词背景和单词显示*/
    public void createWordCardBoxAndWordLabel() {
        wordCardImage.setPosition(280,100); // 单词卡背景图显示位置
        wordLabel.setPosition(300,400); // 单词卡单词显示位置
        explainLabel.setPosition(300, 360); // 单词解释显示位置
    }

    /** 进度条 */
    public void createProgress() {
        progress = new Texture(Gdx.files.internal("icon/progress.png"));
        progress1 = new Texture(Gdx.files.internal("icon/progress1.png"));
        progress2 = new Texture(Gdx.files.internal("icon/progress2.png"));
        progressBox = new Image(progress);
        progressKnow = new Image(progress1);
        progressNotKnow = new Image(progress2);
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
        buttonFramework.buttonMessage.setFont(font18);
        buttonFramework.buttonMessage.setAxis(600,415);
        playSound = buttonFramework.createButton();
    }

    /** Actor添加到舞台*/
    @Override
    public void addToStage() {
        if (cardGoal != StudyData.getDailyGoal()) { // 设置里改了每日目标就重新抽词
            loadCards();
        }
        stage.addActor(wordCardImage);
        stage.addActor(wordLabel);
        stage.addActor(explainLabel);
        if (count1 >= cards.size()) {
            stage.addActor(backButton);
            stage.addActor(forWardButton);
        }
        else {
            stage.addActor(knowButton);
            stage.addActor(notKnowButton);
        }
        stage.addActor(progressNotKnow);
        stage.addActor(progressKnow);
        stage.addActor(progressBox);
        stage.addActor(playSound);
    }

    /** 有事件触发才被调用*/
    @Override
    public void showMessage() {
        if (backButton.isChecked()) { // 向前翻页
            count--;
            backButton.setChecked(false);
        }
        else if (forWardButton.isChecked()){ // 向后翻页
            count++;
            forWardButton.setChecked(false);
        }
        else if (knowButton.isChecked()) {
            count1++;
            count++;
            StudyData.recordReview(true); // 新功能: 记一次学习统计
            knowButton.setChecked(false);
        }
        else if (notKnowButton.isChecked()) {
            StudyData.recordReview(false);
            WordBank.Word card = currentCard();
            if (card != null) {
                StudyData.addToNotebook(card.getWord(), card.getExplain()); // 新功能: 不认识的自动进生词本
            }
            notKnowButton.setChecked(false);
        }
        else if (playSound.isChecked()) {
            playSound.setChecked(false);
            WordBank.Word card = currentCard();
            if (card != null) {
                playWord(card.getWord());
            }
        }

        count = count < 0 ? 0 : count;
        count = count >= cards.size() ? Math.max(0, cards.size() - 1) : count;

        // 显示单词
        WordBank.Word card = currentCard();
        wordLabel.setText(card == null ? "" : TextUtil.truncate(card.getWord(), 16) + "\n\n");
        explainLabel.setText(card == null ? "" : TextUtil.wrap(card.getExplain(), 36, 4));
        // 进度条显示
        if (count1 <= cards.size())
            progressKnow.setWidth(count1 * (400f / Math.max(1, cards.size())));
    }

    /** 有 sound/<单词>.mp3 就放单词读音, 没有就放默认音效 */
    private void playWord(String word) {
        try {
            wordSound = Gdx.audio.newSound(Gdx.files.internal("sound/" + word.trim().toLowerCase() + ".mp3"));
        } catch (Exception e) {
            wordSound = Gdx.audio.newSound(Gdx.files.internal("sound/noSound.mp3"));
        }
        wordSound.play();
    }

    @Override
    public void dispose() {
        wordCardTexture.dispose();
        progress.dispose();
        progress1.dispose();
        progress2.dispose();
        wordSound.dispose();
    }
}
