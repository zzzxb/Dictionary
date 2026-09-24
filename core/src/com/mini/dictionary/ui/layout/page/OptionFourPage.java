package com.mini.dictionary.ui.layout.page;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mini.dictionary.ui.UiKit;
import com.mini.dictionary.ui.layout.page.dao.OptionPageDao;
import com.mini.dictionary.util.FontCache;
import com.mini.dictionary.util.StudyData;
import com.mini.dictionary.util.TextUtil;

import java.util.List;

/**
 * 新功能页: 生词本。
 *
 * 背单词页点"不认识"、词库页点"加入生词本"都会记到这里, 可以逐条移除, 数据存在本地。
 */
public class OptionFourPage implements OptionPageDao {

    private static final int ROWS = 7;
    private static final int ROW_STEP = 42;

    private final Stage stage;
    private final BitmapFont font18;
    private final BitmapFont font28;

    private Label titleLabel;
    private Label countLabel;
    private Label pageLabel;
    private Label detailLabel;
    private Label hintLabel;
    private TextButton prevButton;
    private TextButton nextButton;
    private final TextButton[] wordButtons = new TextButton[ROWS];
    private final TextButton[] removeButtons = new TextButton[ROWS];

    private List<String[]> entries;
    private int page = 0;
    private int selectedIndex = -1;
    private int lastVersion = -1;

    public OptionFourPage(Stage stage) {
        this.stage = stage;
        this.font18 = FontCache.font18();
        this.font28 = FontCache.myfont();
        init();
    }

    @Override
    public void init() {
        titleLabel = UiKit.label("生词本", font28);
        titleLabel.setPosition(175, 528);

        countLabel = UiKit.label("", font18);
        countLabel.setPosition(175, 502);

        pageLabel = UiKit.label("", font18);
        pageLabel.setPosition(500, 537);

        prevButton = UiKit.button("上一页", font18, 610, 528, 80, 30);
        prevButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (page > 0) {
                    page--;
                    selectedIndex = -1;
                    refresh();
                }
            }
        });

        nextButton = UiKit.button("下一页", font18, 700, 528, 80, 30);
        nextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                page++;
                selectedIndex = -1;
                refresh();
            }
        });

        for (int i = 0; i < ROWS; i++) {
            final int row = i;
            TextButton wordButton = UiKit.rowButton("", font18, 175, 460 - i * ROW_STEP, 420, 34);
            wordButton.setProgrammaticChangeEvents(false);
            wordButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (!wordButtons[row].isChecked()) {
                        return;
                    }
                    selectedIndex = page * ROWS + row;
                    updateDetail();
                    refreshChecked();
                }
            });
            wordButtons[i] = wordButton;

            TextButton removeButton = UiKit.button("移除", font18, 610, 460 - i * ROW_STEP, 90, 34);
            removeButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    int index = page * ROWS + row;
                    if (entries != null && index < entries.size()) {
                        StudyData.removeFromNotebook(entries.get(index)[0]);
                        selectedIndex = -1;
                        refresh();
                    }
                }
            });
            removeButtons[i] = removeButton;
        }

        detailLabel = UiKit.wrapped("", font18, 620);
        detailLabel.setPosition(175, 120);

        hintLabel = UiKit.label("生词本还是空的: 背单词时点\"不认识\", 或者在词库里点\"加入生词本\"",
                font18, UiKit.TEXT_MUTED);
        hintLabel.setPosition(175, 300);
    }

    private void refresh() {
        entries = StudyData.getNotebook();
        int pageCount = Math.max(1, (entries.size() + ROWS - 1) / ROWS);
        if (page > pageCount - 1) {
            page = pageCount - 1;
        }
        if (page < 0) {
            page = 0;
        }
        for (int i = 0; i < ROWS; i++) {
            int index = page * ROWS + i;
            boolean has = index < entries.size();
            wordButtons[i].setVisible(has);
            wordButtons[i].setTouchable(has ? Touchable.enabled : Touchable.disabled);
            wordButtons[i].setChecked(has && index == selectedIndex);
            removeButtons[i].setVisible(has);
            removeButtons[i].setTouchable(has ? Touchable.enabled : Touchable.disabled);
            if (has) {
                String[] entry = entries.get(index);
                wordButtons[i].setText(TextUtil.truncate(entry[0], 16) + " | "
                        + TextUtil.truncate(TextUtil.brief(entry[1], 26), 26));
            }
        }
        countLabel.setText("共 " + entries.size() + " 个生词");
        pageLabel.setText("第 " + (page + 1) + " / " + pageCount + " 页");
        prevButton.setDisabled(page <= 0);
        nextButton.setDisabled(page >= pageCount - 1);
        boolean empty = entries.isEmpty();
        hintLabel.setVisible(empty);
        prevButton.setVisible(!empty);
        nextButton.setVisible(!empty);
        pageLabel.setVisible(!empty);
        if (empty) {
            detailLabel.setText("");
            selectedIndex = -1;
        } else {
            updateDetail();
        }
        lastVersion = StudyData.version();
    }

    private void refreshChecked() {
        for (int i = 0; i < ROWS; i++) {
            wordButtons[i].setChecked(entries != null && page * ROWS + i == selectedIndex);
        }
    }

    private void updateDetail() {
        if (entries == null || selectedIndex < 0 || selectedIndex >= entries.size()) {
            detailLabel.setText("");
            return;
        }
        String[] entry = entries.get(selectedIndex);
        detailLabel.setText(entry[0] + "  " + TextUtil.truncate(TextUtil.oneLine(entry[1]), 100));
    }

    @Override
    public void addToStage() {
        refresh();
        stage.addActor(countLabel);
        stage.addActor(titleLabel);
        for (int i = 0; i < ROWS; i++) {
            stage.addActor(wordButtons[i]);
            stage.addActor(removeButtons[i]);
        }
        stage.addActor(prevButton);
        stage.addActor(nextButton);
        stage.addActor(pageLabel);
        stage.addActor(detailLabel);
        stage.addActor(hintLabel);
    }

    @Override
    public void showMessage() {
        // 别的页面(背单词/词库)改了生词本, 这里自动跟上
        if (StudyData.version() != lastVersion) {
            refresh();
        }
    }
}
