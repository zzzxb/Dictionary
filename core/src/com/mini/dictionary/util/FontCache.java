package com.mini.dictionary.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.HashMap;
import java.util.Map;

/**
 * 字体缓存(新加的)。
 *
 * 老的 ButtonFramework 每次创建按钮都会 new 一个 BitmapFont, 而一个 2048x2048 的字体图集
 * 在显存里是 16MB, 菜单按钮一多就会白白吃掉几百兆。这里按路径缓存, 同一种字体全局只加载一次,
 * 让后面新增的页面可以放心地用字体。
 */
public final class FontCache {
    public static final String FONT18_FNT = "font/font18.fnt";
    public static final String FONT18_PNG = "font/font18.png";
    public static final String MYFONT_FNT = "font/myfont.fnt";
    public static final String MYFONT_PNG = "font/myfont.png";

    private static final Map<String, BitmapFont> CACHE = new HashMap<String, BitmapFont>();

    private FontCache() {
    }

    public static synchronized BitmapFont get(String fntPath, String pngPath) {
        String key = fntPath + "|" + pngPath;
        BitmapFont font = CACHE.get(key);
        if (font == null) {
            font = new BitmapFont(Gdx.files.internal(fntPath), Gdx.files.internal(pngPath), false);
            CACHE.put(key, font);
        }
        return font;
    }

    /** 18 号字体: 正文/列表都够用, 中英文都支持 */
    public static BitmapFont font18() {
        return get(FONT18_FNT, FONT18_PNG);
    }

    /** 28 号字体: 用来做标题和大号单词 */
    public static BitmapFont myfont() {
        return get(MYFONT_FNT, MYFONT_PNG);
    }
}
