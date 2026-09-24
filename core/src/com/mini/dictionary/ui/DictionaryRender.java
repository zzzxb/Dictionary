package com.mini.dictionary.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.mini.dictionary.ui.layout.DictionaryLayout;
import com.mini.dictionary.util.FontCache;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DictionaryRender implements Disposable {
    // 左上角蓝色块的位置和大小, 跟下面画 hintTexture 的地方保持一致
    private static final float HINT_X = 0f;
    private static final float HINT_Y = 430f;
    private static final float HINT_SIZE = 150f;
    // 日期和星期几在蓝色块里的纵向位置(字号 18, 两行居中)
    private static final float DATE_Y = HINT_Y + 98f;
    private static final float WEEK_Y = HINT_Y + 70f;

    private static final String DAY_PATTERN = "yyyy-MM-dd";
    private static final String[] WEEK_DAYS = {
            "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    private DictionaryLayout dictionaryLayout;
    private SpriteBatch batch;

    private Texture menuTexture;
    private Texture hintTexture;

    // 蓝色块里的日期和星期几(以前有, 后来弄丢了, 这里补回来)
    private final SimpleDateFormat dayFormat = new SimpleDateFormat(DAY_PATTERN, Locale.CHINA);
    private final Date date = new Date();
    private BitmapFont dateFont;
    private final GlyphLayout layout = new GlyphLayout();
    private String shownDay;
    private String dateText = "";
    private String weekText = "";

    public DictionaryRender(DictionaryLayout dictionaryLayout) {
        this.dictionaryLayout = dictionaryLayout;
        init();
    }

    public void init() {
        batch = new SpriteBatch();
        menuTexture = new Texture(Gdx.files.internal("icon/menu_background.png"));
        hintTexture = new Texture(Gdx.files.internal("icon/hint.png"));
        dateFont = FontCache.font18(); // 跟菜单共用一份字体, 不额外占显存
    }

    public void render() {
        renderTestObject();
    }

    public void renderTestObject() {
        batch.begin();
        batch.draw(menuTexture,0,0,150,580); // 更改菜单灰色块的位置和大小
        batch.draw(hintTexture,HINT_X,HINT_Y,HINT_SIZE,HINT_SIZE); // 更改菜单蓝色块的位置和大小
        drawDate(); // 蓝色块里的日期 + 星期几
        batch.end();
        dictionaryLayout.render();
    }

    /** 蓝色块里居中显示日期和星期几, 只有跨天了才重新算一次 */
    private void drawDate() {
        date.setTime(System.currentTimeMillis());
        String day = dayFormat.format(date);
        if (!day.equals(shownDay)) {
            shownDay = day;
            dateText = day;
            Calendar calendar = Calendar.getInstance();
            weekText = WEEK_DAYS[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        }
        dateFont.setColor(1f, 1f, 1f, 1f); // 字体本身是深色的, 给白色相当于不上色
        layout.setText(dateFont, dateText);
        dateFont.draw(batch, dateText, HINT_X + (HINT_SIZE - layout.width) / 2f, DATE_Y);
        layout.setText(dateFont, weekText);
        dateFont.draw(batch, weekText, HINT_X + (HINT_SIZE - layout.width) / 2f, WEEK_Y);
    }

    @Override
    public void dispose() {
        batch.dispose();
        menuTexture.dispose();
        hintTexture.dispose();
        dictionaryLayout.dispose();
        // dateFont 是 FontCache 里共享的, 不在这里 dispose
    }
}
