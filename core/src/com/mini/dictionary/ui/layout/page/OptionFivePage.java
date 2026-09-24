package com.mini.dictionary.ui.layout.page;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mini.dictionary.ui.UiKit;
import com.mini.dictionary.ui.layout.page.dao.OptionPageDao;
import com.mini.dictionary.util.FontCache;
import com.mini.dictionary.util.StudyData;
import com.mini.dictionary.util.TextUtil;
import com.mini.dictionary.util.WordBank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 新功能页: 单词测验。
 *
 * 从词库里随机抽词, 四选一选释义, 答完一轮给成绩, 正确率会记到学习统计里。
 * 设置里开了"答对后自动下一题"的话, 答完停一下会自动跳。
 */
public class OptionFivePage implements OptionPageDao {

    private static final int OPTIONS = 4;
    private static final float AUTO_NEXT_DELAY = 1.2f;
    private static final Random RANDOM = new Random();

    private final Stage stage;
    private final BitmapFont font18;
    private final BitmapFont font28;

    private Label titleLabel;
    private Label roundLabel;
    private Label scoreLabel;
    private Label questionLabel;
    private Label promptLabel;
    private Label feedbackLabel;
    private TextButton nextButton;
    private TextButton restartButton;
    private final TextButton[] optionButtons = new TextButton[OPTIONS];
    private final TextButton.TextButtonStyle[] optionStyles = new TextButton.TextButtonStyle[OPTIONS];
    private final String[] optionTexts = new String[OPTIONS];

    private WordBank.Word current;
    private int correctIndex = 0;
    private boolean answered = false;
    private boolean roundFinished = false;
    private int roundIndex = 0;
    private int roundSize = 10;
    private int correctCount = 0;
    private float autoNextTimer = 0f;
    private int lastQuizSize = -1;

    public OptionFivePage(Stage stage) {
        this.stage = stage;
        this.font18 = FontCache.font18();
        this.font28 = FontCache.myfont();
        init();
    }

    @Override
    public void init() {
        titleLabel = UiKit.label("单词测验", font28);
        titleLabel.setPosition(175, 528);

        roundLabel = UiKit.label("", font18);
        roundLabel.setPosition(560, 537);

        scoreLabel = UiKit.label("", font18);
        scoreLabel.setPosition(560, 507);

        questionLabel = UiKit.label("", font28);
        questionLabel.setPosition(175, 462);

        promptLabel = UiKit.label("选出正确的释义:", font18, UiKit.TEXT_MUTED);
        promptLabel.setPosition(175, 430);

        for (int i = 0; i < OPTIONS; i++) {
            final int index = i;
            optionStyles[i] = UiKit.rowStyle(font18);
            TextButton button = new TextButton("", optionStyles[i]);
            button.setBounds(175, 380 - i * 50, 430, 40);
            button.getLabel().setEllipsis(true);
            button.setProgrammaticChangeEvents(false);
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (!optionButtons[index].isChecked()) {
                        return;
                    }
                    answer(index);
                }
            });
            optionButtons[i] = button;
        }

        feedbackLabel = UiKit.wrapped("", font18, 620);
        feedbackLabel.setPosition(175, 185);

        nextButton = UiKit.button("下一题", font18, 175, 120, 140, 36);
        nextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (roundFinished) {
                    startRound();
                } else {
                    nextQuestion();
                }
            }
        });

        restartButton = UiKit.button("重新开始", font18, 330, 120, 140, 36);
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startRound();
            }
        });
    }

    private void startRound() {
        roundSize = StudyData.getQuizSize();
        lastQuizSize = roundSize;
        roundIndex = 0;
        correctCount = 0;
        roundFinished = false;
        nextButton.setText("下一题");
        newQuestion();
    }

    private void newQuestion() {
        if (roundIndex >= roundSize) {
            finishRound();
            return;
        }
        answered = false;
        autoNextTimer = 0f;
        paintOptions(-1, -1);
        for (int i = 0; i < OPTIONS; i++) {
            optionButtons[i].setChecked(false);
            optionButtons[i].setDisabled(false);
        }

        WordBank.Word word = WordBank.randomQuizWord();
        int guard = 0;
        while (current != null && word.getWord().equals(current.getWord()) && guard++ < 20) {
            word = WordBank.randomQuizWord();
        }
        current = word;

        String correctText = word.getBrief();
        List<String> options = new ArrayList<String>();
        options.add(correctText);
        List<WordBank.Word> pool = WordBank.quizPool();
        int attempts = 0;
        while (options.size() < OPTIONS && attempts++ < 400) {
            String text = pool.get(RANDOM.nextInt(pool.size())).getBrief();
            if (!options.contains(text)) {
                options.add(text);
            }
        }
        while (options.size() < OPTIONS) {
            options.add("暂无更多选项");
        }
        Collections.shuffle(options, RANDOM);
        for (int i = 0; i < OPTIONS; i++) {
            optionTexts[i] = options.get(i);
            optionButtons[i].setText(TextUtil.truncate(optionTexts[i], 22));
        }
        correctIndex = options.indexOf(correctText);

        questionLabel.setText(TextUtil.truncate(word.getWord(), 18));
        promptLabel.setText("选出正确的释义:");
        roundLabel.setText("第 " + (roundIndex + 1) + " / " + roundSize + " 题");
        feedbackLabel.setText("");
        updateScore();
    }

    private void answer(int index) {
        if (answered || roundFinished) {
            return;
        }
        answered = true;
        boolean correct = index == correctIndex;
        if (correct) {
            correctCount++;
        }
        StudyData.recordQuiz(correct);
        paintOptions(index, correctIndex);
        for (int i = 0; i < OPTIONS; i++) {
            optionButtons[i].setDisabled(true);
        }
        if (correct) {
            feedbackLabel.setText("回答正确");
        } else {
            feedbackLabel.setText("回答错误, 正确答案是: "
                    + TextUtil.truncate(optionTexts[correctIndex], 40));
        }
        updateScore();
        if (roundIndex + 1 >= roundSize) {
            nextButton.setText("看结果");
        }
    }

    /** 给正确/错误选项换成绿/红底色 */
    private void paintOptions(int chosen, int correct) {
        for (int i = 0; i < OPTIONS; i++) {
            TextButton.TextButtonStyle style = optionStyles[i];
            if (i == correct && correct >= 0) {
                style.up = UiKit.solid(UiKit.GREEN);
                style.over = UiKit.solid(UiKit.GREEN);
                style.down = UiKit.solid(UiKit.GREEN);
                style.checked = UiKit.solid(UiKit.GREEN);
            } else if (i == chosen) {
                style.up = UiKit.solid(UiKit.RED);
                style.over = UiKit.solid(UiKit.RED);
                style.down = UiKit.solid(UiKit.RED);
                style.checked = UiKit.solid(UiKit.RED);
            } else {
                style.up = UiKit.solid(UiKit.ROW_UP);
                style.over = UiKit.solid(UiKit.ROW_OVER);
                style.down = UiKit.solid(UiKit.ROW_CHECKED);
                style.checked = UiKit.solid(UiKit.ROW_CHECKED);
            }
        }
    }

    private void finishRound() {
        roundFinished = true;
        answered = true;
        for (int i = 0; i < OPTIONS; i++) {
            optionButtons[i].setText("");
            optionButtons[i].setDisabled(true);
        }
        questionLabel.setText("本轮结束");
        promptLabel.setText("");
        roundLabel.setText("本轮 " + roundSize + " 题已完成");
        feedbackLabel.setText("答对 " + correctCount + " / " + roundSize + " 题, 正确率 "
                + TextUtil.percent(correctCount, roundSize) + ". 点\"再来一轮\"继续.");
        nextButton.setText("再来一轮");
        updateScore();
    }

    private void updateScore() {
        scoreLabel.setText("本轮答对 " + correctCount + " 题");
    }

    @Override
    public void addToStage() {
        if (roundSize != StudyData.getQuizSize() || lastQuizSize != roundSize) {
            startRound();
        }
        stage.addActor(titleLabel);
        stage.addActor(roundLabel);
        stage.addActor(scoreLabel);
        stage.addActor(questionLabel);
        stage.addActor(promptLabel);
        for (int i = 0; i < OPTIONS; i++) {
            stage.addActor(optionButtons[i]);
        }
        stage.addActor(feedbackLabel);
        stage.addActor(nextButton);
        stage.addActor(restartButton);
    }

    @Override
    public void showMessage() {
        // 设置里改了每轮题数就重开一轮
        if (StudyData.getQuizSize() != lastQuizSize) {
            startRound();
            return;
        }
        if (!answered || roundFinished || !StudyData.isAutoNext()) {
            return;
        }
        autoNextTimer += Gdx.graphics.getDeltaTime();
        if (autoNextTimer >= AUTO_NEXT_DELAY) {
            nextQuestion();
        }
    }

    private void nextQuestion() {
        autoNextTimer = 0f;
        roundIndex++;
        newQuestion();
    }
}
