package com.mini.dictionary.ui.layout.page;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mini.dictionary.ui.UiKit;
import com.mini.dictionary.ui.layout.page.dao.OptionPageDao;
import com.mini.dictionary.util.FontCache;
import com.mini.dictionary.util.StudyData;
import com.mini.dictionary.util.TextUtil;
import com.mini.dictionary.util.WordBank;

/**
 * 新功能页: 学习统计。
 *
 * 今日进度 / 累计复习 / 认识不认识的比例 / 测验正确率 / 连续打卡天数 / 生词本数量,
 * 都来自 StudyData 里的本地记录。
 */
public class OptionSixPage implements OptionPageDao {

    private static final float BAR_WIDTH = 500f;
    private static final float CONFIRM_SECONDS = 5f;

    private final Stage stage;
    private final BitmapFont font18;
    private final BitmapFont font28;

    private Label titleLabel;
    private Label todayLabel;
    private Label lineOne;
    private Label lineTwo;
    private Label lineThree;
    private Label lineFour;
    private Label tipLabel;
    private Label statusLabel;
    private Image barTrack;
    private Image barFill;
    private TextButton resetButton;

    private int lastVersion = -1;
    private boolean confirmReset = false;
    private float confirmTimer = 0f;

    public OptionSixPage(Stage stage) {
        this.stage = stage;
        this.font18 = FontCache.font18();
        this.font28 = FontCache.myfont();
        init();
    }

    @Override
    public void init() {
        titleLabel = UiKit.label("学习统计", font28);
        titleLabel.setPosition(175, 528);

        todayLabel = UiKit.label("", font18);
        todayLabel.setPosition(175, 480);

        barTrack = UiKit.bar(UiKit.TRACK, 175, 448, BAR_WIDTH, 18);
        barFill = UiKit.bar(UiKit.PRIMARY, 175, 448, 0, 18);

        lineOne = UiKit.label("", font18);
        lineOne.setPosition(175, 402);
        lineTwo = UiKit.label("", font18);
        lineTwo.setPosition(175, 366);
        lineThree = UiKit.label("", font18);
        lineThree.setPosition(175, 330);
        lineFour = UiKit.label("", font18);
        lineFour.setPosition(175, 294);

        tipLabel = UiKit.label("统计只记录本机上的学习行为, 数据保存在本地.", font18, UiKit.TEXT_MUTED);
        tipLabel.setPosition(175, 235);

        resetButton = UiKit.button("重置统计数据", font18, 175, 175, 180, 36);
        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onResetClicked();
            }
        });

        statusLabel = UiKit.label("", font18, UiKit.TEXT_MUTED);
        statusLabel.setPosition(375, 185);
    }

    private void onResetClicked() {
        if (!confirmReset) {
            confirmReset = true;
            confirmTimer = CONFIRM_SECONDS;
            resetButton.setText("再点一次确认");
            statusLabel.setText("5 秒内再点一次就会清空统计");
            return;
        }
        StudyData.resetStats();
        cancelConfirm();
        statusLabel.setText("统计数据已清空");
        refresh();
    }

    private void cancelConfirm() {
        confirmReset = false;
        confirmTimer = 0f;
        resetButton.setText("重置统计数据");
    }

    private void refresh() {
        int goal = StudyData.getDailyGoal();
        int todayReviewed = StudyData.getTodayReviewed();
        int todayQuiz = StudyData.getTodayQuiz();
        int totalReviewed = StudyData.getTotalReviewed();
        int known = StudyData.getKnownCount();
        int unknown = StudyData.getUnknownCount();
        int quizTotal = StudyData.getQuizTotal();
        int quizCorrect = StudyData.getQuizCorrect();

        todayLabel.setText("今日已学: " + todayReviewed + " / " + goal + " 个"
                + (todayReviewed >= goal ? " (今日目标已完成)" : ""));
        float ratio = goal <= 0 ? 1f : Math.min(1f, todayReviewed * 1f / goal);
        barFill.setWidth(BAR_WIDTH * ratio);

        lineOne.setText("累计复习: " + totalReviewed + " 次    认识: " + known
                + " 次    不认识: " + unknown + " 次    掌握率: "
                + TextUtil.percent(known, known + unknown));
        lineTwo.setText("测验: " + quizTotal + " 题    答对: " + quizCorrect
                + " 题    正确率: " + TextUtil.percent(quizCorrect, quizTotal)
                + "    今日测验: " + todayQuiz + " 题");
        lineThree.setText("连续打卡: " + StudyData.getStreak() + " 天    最长连续: "
                + StudyData.getBestStreak() + " 天    学习天数: " + StudyData.getStudyDays() + " 天");
        lineFour.setText("生词本: " + StudyData.notebookSize() + " 个    词库总量: "
                + WordBank.size() + " 个    每日目标: " + goal + " 个");
        lastVersion = StudyData.version();
    }

    @Override
    public void addToStage() {
        refresh();
        stage.addActor(titleLabel);
        stage.addActor(todayLabel);
        stage.addActor(barTrack);
        stage.addActor(barFill);
        stage.addActor(lineOne);
        stage.addActor(lineTwo);
        stage.addActor(lineThree);
        stage.addActor(lineFour);
        stage.addActor(tipLabel);
        stage.addActor(resetButton);
        stage.addActor(statusLabel);
    }

    @Override
    public void showMessage() {
        if (confirmReset) {
            confirmTimer -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();
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
