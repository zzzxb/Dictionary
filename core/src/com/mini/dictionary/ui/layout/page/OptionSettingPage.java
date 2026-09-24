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

/**
 * 设置页(在老的空页面上补的功能)。
 *
 * 每天背单词数 / 每轮测验题数 / 答对自动下一题, 加上一个两步确认的"重置全部数据"。
 */
public class OptionSettingPage implements OptionPageDao {

    private static final int GOAL_STEP = 5;
    private static final int QUIZ_STEP = 5;
    private static final float CONFIRM_SECONDS = 5f;

    private final Stage stage;
    private final BitmapFont font18;
    private final BitmapFont font28;

    private Label titleLabel;
    private Label goalTitle;
    private Label goalValue;
    private Label quizTitle;
    private Label quizValue;
    private Label autoTitle;
    private Label tipLabel;
    private Label statusLabel;
    private TextButton goalMinus;
    private TextButton goalPlus;
    private TextButton quizMinus;
    private TextButton quizPlus;
    private TextButton autoToggle;
    private TextButton resetButton;

    private int lastVersion = -1;
    private boolean confirmReset = false;
    private float confirmTimer = 0f;

    public OptionSettingPage(Stage stage) {
        this.stage = stage;
        this.font18 = FontCache.font18();
        this.font28 = FontCache.myfont();
        init();
    }

    @Override
    public void init() {
        titleLabel = UiKit.label("设置", font28);
        titleLabel.setPosition(175, 528);

        goalTitle = UiKit.label("每天背单词数", font18);
        goalTitle.setPosition(175, 468);
        goalValue = UiKit.label("", font18);
        goalValue.setPosition(175, 432);
        goalMinus = UiKit.button("-", font18, 270, 425, 40, 32);
        goalMinus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                StudyData.setDailyGoal(StudyData.getDailyGoal() - GOAL_STEP);
                refresh();
            }
        });
        goalPlus = UiKit.button("+", font18, 320, 425, 40, 32);
        goalPlus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                StudyData.setDailyGoal(StudyData.getDailyGoal() + GOAL_STEP);
                refresh();
            }
        });

        quizTitle = UiKit.label("每轮测验题数", font18);
        quizTitle.setPosition(175, 375);
        quizValue = UiKit.label("", font18);
        quizValue.setPosition(175, 339);
        quizMinus = UiKit.button("-", font18, 270, 332, 40, 32);
        quizMinus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                StudyData.setQuizSize(StudyData.getQuizSize() - QUIZ_STEP);
                refresh();
            }
        });
        quizPlus = UiKit.button("+", font18, 320, 332, 40, 32);
        quizPlus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                StudyData.setQuizSize(StudyData.getQuizSize() + QUIZ_STEP);
                refresh();
            }
        });

        autoTitle = UiKit.label("答对后自动下一题", font18);
        autoTitle.setPosition(175, 282);
        autoToggle = UiKit.button("", font18, 400, 275, 110, 32);
        autoToggle.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                StudyData.setAutoNext(!StudyData.isAutoNext());
                refresh();
            }
        });

        resetButton = UiKit.button("重置全部数据", font18, 175, 190, 180, 36);
        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onResetClicked();
            }
        });

        statusLabel = UiKit.label("", font18, UiKit.TEXT_MUTED);
        statusLabel.setPosition(375, 200);

        tipLabel = UiKit.label("数据保存在本地, 重启程序后依然有效.", font18, UiKit.TEXT_MUTED);
        tipLabel.setPosition(175, 140);
    }

    private void onResetClicked() {
        if (!confirmReset) {
            confirmReset = true;
            confirmTimer = CONFIRM_SECONDS;
            resetButton.setText("再点一次确认");
            statusLabel.setText("统计/生词本/设置都会被清空");
            return;
        }
        StudyData.resetAll();
        cancelConfirm();
        statusLabel.setText("已恢复默认设置");
        refresh();
    }

    private void cancelConfirm() {
        confirmReset = false;
        confirmTimer = 0f;
        resetButton.setText("重置全部数据");
    }

    private void refresh() {
        goalValue.setText(StudyData.getDailyGoal() + " 个");
        quizValue.setText(StudyData.getQuizSize() + " 题");
        autoToggle.setText(StudyData.isAutoNext() ? "开启" : "关闭");
        lastVersion = StudyData.version();
    }

    @Override
    public void addToStage() {
        refresh();
        stage.addActor(titleLabel);
        stage.addActor(goalTitle);
        stage.addActor(goalValue);
        stage.addActor(goalMinus);
        stage.addActor(goalPlus);
        stage.addActor(quizTitle);
        stage.addActor(quizValue);
        stage.addActor(quizMinus);
        stage.addActor(quizPlus);
        stage.addActor(autoTitle);
        stage.addActor(autoToggle);
        stage.addActor(resetButton);
        stage.addActor(statusLabel);
        stage.addActor(tipLabel);
    }

    @Override
    public void showMessage() {
        if (confirmReset) {
            confirmTimer -= Gdx.graphics.getDeltaTime();
            if (confirmTimer <= 0) {
                cancelConfirm();
                statusLabel.setText("");
            }
        }
        if (StudyData.version() != lastVersion) {
            refresh();
        }
    }
}
