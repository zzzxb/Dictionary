package com.mini.dictionary.ui.layout.page;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mini.dictionary.ui.UiKit;
import com.mini.dictionary.ui.layout.page.dao.OptionPageDao;
import com.mini.dictionary.util.FontCache;
import com.mini.dictionary.util.StudyData;
import com.mini.dictionary.util.TextUtil;
import com.mini.dictionary.util.WordBank;

import java.util.List;

/**
 * 新功能页: 词库。
 *
 * 把 character.json 里的一千多个词条全列出来, 支持关键字筛选 + 翻页 + 看释义,
 * 看中的词可以一键丢进生词本。
 */
public class OptionThreePage implements OptionPageDao {

    private static final int ROWS = 6;
    private static final int ROW_STEP = 40;

    private final Stage stage;
    private final BitmapFont font18;
    private final BitmapFont font28;

    private Label titleLabel;
    private Label countLabel;
    private Label pageLabel;
    private Label detailWordLabel;
    private Label detailExplainLabel;
    private Label statusLabel;
    private TextField searchBox;
    private TextButton clearButton;
    private TextButton prevButton;
    private TextButton nextButton;
    private TextButton addButton;
    private final TextButton[] rowButtons = new TextButton[ROWS];
    private final WordBank.Word[] rowWords = new WordBank.Word[ROWS];

    private int page = 0;
    private WordBank.Word selected;

    public OptionThreePage(Stage stage) {
        this.stage = stage;
        this.font18 = FontCache.font18();
        this.font28 = FontCache.myfont();
        init();
    }

    @Override
    public void init() {
        titleLabel = UiKit.label("词库", font28);
        titleLabel.setPosition(175, 528);

        countLabel = UiKit.label("", font18);
        countLabel.setPosition(175, 496);

        searchBox = UiKit.textField("输入单词或释义筛选...", font18);
        searchBox.setBounds(175, 450, 340, 34);
        searchBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                page = 0;
                refresh();
            }
        });

        clearButton = UiKit.button("清空", font18, 530, 450, 80, 34);
        clearButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                searchBox.setText("");
                page = 0;
                refresh();
            }
        });

        for (int i = 0; i < ROWS; i++) {
            final int row = i;
            TextButton button = UiKit.rowButton("", font18, 175, 405 - i * ROW_STEP, 340, 32);
            button.setProgrammaticChangeEvents(false);
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (!rowButtons[row].isChecked()) {
                        return;
                    }
                    selected = rowWords[row];
                    updateDetail();
                    refreshRowsChecked();
                }
            });
            rowButtons[i] = button;
        }

        prevButton = UiKit.button("上一页", font18, 175, 155, 80, 32);
        prevButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (page > 0) {
                    page--;
                    refresh();
                }
            }
        });

        nextButton = UiKit.button("下一页", font18, 265, 155, 80, 32);
        nextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                page++;
                refresh();
            }
        });

        pageLabel = UiKit.label("", font18);
        pageLabel.setPosition(360, 163);

        detailWordLabel = UiKit.label("", font28);
        detailWordLabel.setPosition(560, 400);

        detailExplainLabel = UiKit.wrapped("", font18, 245);
        detailExplainLabel.setPosition(560, 270);

        addButton = UiKit.button("加入生词本", font18, 560, 195, 130, 34);
        addButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                addSelectedToNotebook();
            }
        });

        statusLabel = UiKit.label("", font18, UiKit.TEXT_MUTED);
        statusLabel.setPosition(560, 160);
    }

    /** 重建当前页要显示的单词 */
    private void refresh() {
        String keyword = searchBox.getText() == null ? "" : searchBox.getText().trim();
        List<WordBank.Word> filtered = WordBank.search(keyword);
        int pageCount = Math.max(1, (filtered.size() + ROWS - 1) / ROWS);
        if (page > pageCount - 1) {
            page = pageCount - 1;
        }
        if (page < 0) {
            page = 0;
        }
        int start = page * ROWS;
        for (int i = 0; i < ROWS; i++) {
            int index = start + i;
            TextButton button = rowButtons[i];
            if (index >= filtered.size()) {
                rowWords[i] = null;
                button.setVisible(false);
                button.setTouchable(Touchable.disabled);
                button.setChecked(false);
                continue;
            }
            WordBank.Word word = filtered.get(index);
            rowWords[i] = word;
            button.setVisible(true);
            button.setTouchable(Touchable.enabled);
            String prefix = StudyData.inNotebook(word.getWord()) ? "* " : "";
            button.setText(prefix + TextUtil.truncate(word.getWord(), 14) + " | "
                    + TextUtil.truncate(word.getBrief(), 12));
        }
        countLabel.setText("共 " + filtered.size() + " 个单词"
                + (keyword.isEmpty() ? "" : " (筛选: " + TextUtil.truncate(keyword, 16) + ")"));
        pageLabel.setText("第 " + (page + 1) + " / " + pageCount + " 页");
        prevButton.setDisabled(page <= 0);
        nextButton.setDisabled(page >= pageCount - 1);
        refreshRowsChecked();
        updateDetail();
    }

    /** 程序里改选中状态时不要触发监听, 否则会递归 */
    private void refreshRowsChecked() {
        for (int i = 0; i < ROWS; i++) {
            WordBank.Word word = rowWords[i];
            rowButtons[i].setChecked(word != null && selected != null
                    && word.getWord().equals(selected.getWord()));
        }
    }

    private void updateDetail() {
        if (selected == null) {
            detailWordLabel.setText("点击左边单词");
            detailExplainLabel.setText("");
            addButton.setDisabled(true);
            return;
        }
        detailWordLabel.setText(TextUtil.truncate(selected.getWord(), 16));
        detailExplainLabel.setText(TextUtil.truncate(TextUtil.oneLine(selected.getExplain()), 60));
        addButton.setDisabled(false);
    }

    private void addSelectedToNotebook() {
        if (selected == null) {
            statusLabel.setText("请先点左边的单词");
            return;
        }
        boolean added = StudyData.addToNotebook(selected.getWord(), selected.getExplain());
        statusLabel.setText(added ? "已加入生词本" : "已经在生词本里了");
        refresh();
    }

    @Override
    public void addToStage() {
        refresh();
        stage.addActor(countLabel);
        for (TextButton button : rowButtons) {
            stage.addActor(button);
        }
        stage.addActor(searchBox);
        stage.addActor(clearButton);
        stage.addActor(prevButton);
        stage.addActor(nextButton);
        stage.addActor(pageLabel);
        stage.addActor(titleLabel);
        stage.addActor(detailWordLabel);
        stage.addActor(detailExplainLabel);
        stage.addActor(addButton);
        stage.addActor(statusLabel);
    }

    @Override
    public void showMessage() {
        // 光标在输入框里按回车 = 立刻重新筛选
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            refresh();
        }
    }
}
