package com.mini.dictionary.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.HashMap;
import java.util.Map;

/**
 * 新页面的公共 UI 小工具(新加的)。
 *
 * 背景色/小图标都用代码画出来, 这样不用往仓库里塞新图片, 也不用动老的 ButtonFramework:
 *  - solid(color)  纯色矩形, 可以做按钮底色/进度条
 *  - icon(kind)    16x16 的小图标(书/清单/对勾/柱状图)
 *  - button(...)   统一风格的文字按钮
 *  - textField(...) 带提示文字的输入框
 * 生成的纹理按颜色缓存, 不会重复创建。
 */
public final class UiKit {

    // 主题色, 跟原来的蓝色菜单/粉色选中色是一个调子
    public static final Color PRIMARY = new Color(0.38f, 0.70f, 0.92f, 1f);
    public static final Color PRIMARY_DARK = new Color(0.28f, 0.58f, 0.82f, 1f);
    public static final Color ORANGE = new Color(0.95f, 0.60f, 0.24f, 1f);
    public static final Color GREEN = new Color(0.30f, 0.72f, 0.45f, 1f);
    public static final Color RED = new Color(0.85f, 0.35f, 0.35f, 1f);
    public static final Color PURPLE = new Color(0.55f, 0.50f, 0.85f, 1f);

    public static final Color ROW_UP = new Color(0.95f, 0.96f, 0.98f, 1f);
    public static final Color ROW_OVER = new Color(0.86f, 0.91f, 0.96f, 1f);
    public static final Color ROW_CHECKED = new Color(0.76f, 0.87f, 0.96f, 1f);
    public static final Color BUTTON_UP = new Color(0.38f, 0.70f, 0.92f, 1f);
    public static final Color BUTTON_OVER = new Color(0.32f, 0.63f, 0.87f, 1f);
    public static final Color BUTTON_DOWN = new Color(0.26f, 0.55f, 0.80f, 1f);
    public static final Color BUTTON_DISABLED = new Color(0.80f, 0.82f, 0.84f, 1f);
    public static final Color TRACK = new Color(0.90f, 0.91f, 0.93f, 1f);
    public static final Color TEXT_MUTED = new Color(0.45f, 0.47f, 0.50f, 1f);

    private static final Map<Integer, TextureRegionDrawable> SOLID_CACHE =
            new HashMap<Integer, TextureRegionDrawable>();
    private static final Map<String, TextureRegionDrawable> ICON_CACHE =
            new HashMap<String, TextureRegionDrawable>();

    private UiKit() {
    }

    /** 纯色矩形 drawable, 全局按颜色缓存 */
    public static TextureRegionDrawable solid(Color color) {
        int key = Color.rgba8888(color);
        TextureRegionDrawable drawable = SOLID_CACHE.get(key);
        if (drawable == null) {
            Pixmap pixmap = new Pixmap(4, 4, Pixmap.Format.RGBA8888);
            pixmap.setColor(color);
            pixmap.fill();
            drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
            pixmap.dispose();
            SOLID_CACHE.put(key, drawable);
        }
        return drawable;
    }

    /**
     * 代码画出来的 16x16 菜单小图标。
     * kind: book(词库) / list(生词本) / check(测验) / chart(统计)
     */
    public static TextureRegionDrawable icon(String kind) {
        TextureRegionDrawable cached = ICON_CACHE.get(kind);
        if (cached != null) {
            return cached;
        }
        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        pixmap.setBlending(Pixmap.Blending.None);
        pixmap.setColor(0, 0, 0, 0);
        pixmap.fill();

        if ("book".equals(kind)) {
            pixmap.setColor(PRIMARY);
            pixmap.fillRectangle(2, 2, 12, 1);
            pixmap.fillRectangle(2, 13, 12, 1);
            pixmap.fillRectangle(2, 2, 1, 12);
            pixmap.fillRectangle(13, 2, 1, 12);
            pixmap.fillRectangle(6, 3, 1, 10);
            pixmap.fillRectangle(8, 6, 4, 1);
            pixmap.fillRectangle(8, 9, 4, 1);
        } else if ("list".equals(kind)) {
            pixmap.setColor(ORANGE);
            for (int i = 0; i < 3; i++) {
                int y = 4 + i * 4;
                pixmap.fillRectangle(3, y, 2, 2);
                pixmap.fillRectangle(7, y, 6, 2);
            }
        } else if ("check".equals(kind)) {
            pixmap.setColor(GREEN);
            pixmap.drawLine(3, 8, 6, 4);
            pixmap.drawLine(4, 8, 7, 4);
            pixmap.drawLine(6, 4, 12, 12);
            pixmap.drawLine(7, 4, 13, 12);
        } else if ("chart".equals(kind)) {
            pixmap.setColor(PURPLE);
            pixmap.fillRectangle(2, 10, 3, 4);
            pixmap.fillRectangle(7, 5, 3, 9);
            pixmap.fillRectangle(12, 8, 3, 6);
        } else {
            pixmap.setColor(TEXT_MUTED);
            pixmap.fillRectangle(4, 4, 8, 8);
        }

        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        ICON_CACHE.put(kind, drawable);
        return drawable;
    }

    /** 蓝色按钮, 文字用字体本身的颜色 */
    public static TextButton.TextButtonStyle buttonStyle(BitmapFont font) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        style.up = solid(BUTTON_UP);
        style.over = solid(BUTTON_OVER);
        style.down = solid(BUTTON_DOWN);
        style.checked = solid(BUTTON_DOWN);
        style.disabled = solid(BUTTON_DISABLED);
        style.disabledFontColor = TEXT_MUTED;
        return style;
    }

    /** 列表行按钮: 浅灰底, 选中变色 */
    public static TextButton.TextButtonStyle rowStyle(BitmapFont font) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        style.up = solid(ROW_UP);
        style.over = solid(ROW_OVER);
        style.down = solid(ROW_CHECKED);
        style.checked = solid(ROW_CHECKED);
        return style;
    }

    public static TextButton button(String text, BitmapFont font, float x, float y, float width, float height) {
        TextButton button = new TextButton(text, buttonStyle(font));
        button.setBounds(x, y, width, height);
        return button;
    }

    public static TextButton rowButton(String text, BitmapFont font, float x, float y, float width, float height) {
        TextButton button = new TextButton(text, rowStyle(font));
        button.setBounds(x, y, width, height);
        button.getLabel().setEllipsis(true);
        return button;
    }

    public static Label label(String text, BitmapFont font) {
        return new Label(text, new Label.LabelStyle(font, null));
    }

    public static Label label(String text, BitmapFont font, Color fontColor) {
        return new Label(text, new Label.LabelStyle(font, fontColor));
    }

    /** 自动换行的段落, 记住要配合 setWidth/宽度使用 */
    public static Label wrapped(String text, BitmapFont font, float width) {
        Label label = new Label(text, new Label.LabelStyle(font, null));
        label.setWrap(true);
        label.setWidth(width);
        return label;
    }

    public static Image bar(Color color, float x, float y, float width, float height) {
        Image image = new Image(solid(color));
        image.setBounds(x, y, width, height);
        return image;
    }

    /** 输入框: 浅灰底 + 深色光标 + 半角提示文字 */
    public static TextField textField(String message, BitmapFont font) {
        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.background = solid(new Color(0.93f, 0.94f, 0.95f, 1f));
        style.focusedBackground = solid(new Color(0.88f, 0.93f, 0.97f, 1f));
        TextureRegionDrawable cursor = solid(new Color(0.20f, 0.22f, 0.25f, 1f));
        cursor.setMinWidth(2);
        cursor.setMinHeight(0);
        style.cursor = cursor;
        style.font = font;
        style.fontColor = Color.WHITE; // 字体本身是深色的, 这里给白色相当于不着色
        style.messageFontColor = TEXT_MUTED;
        TextField field = new TextField("", style);
        if (message != null) {
            field.setMessageText(message);
        }
        return field;
    }
}
