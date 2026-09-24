package com.mini.dictionary.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 词库(新加的)。
 *
 * 直接读 assets/character.json, 里面是 {"words":[{"word":..., "explain":...}]} 的格式,
 * 一共一千多个词条。这里是懒加载 + 缓存, 只解析一次。
 */
public final class WordBank {

    /** 一条词条 */
    public static final class Word {
        private final String word;
        private final String explain;

        public Word(String word, String explain) {
            this.word = word;
            this.explain = explain;
        }

        public String getWord() {
            return word;
        }

        public String getExplain() {
            return explain;
        }

        /** 列表里显示的短释义 */
        public String getBrief() {
            return TextUtil.brief(explain, 40);
        }

        @Override
        public String toString() {
            return word;
        }
    }

    private static final String JSON_PATH = "character.json";
    private static final Random RANDOM = new Random();

    private static List<Word> words;
    private static List<Word> quizPool;

    private WordBank() {
    }

    /** 全部词条(只读) */
    public static synchronized List<Word> all() {
        if (words == null) {
            load();
        }
        return words;
    }

    /** 适合做选择题的词条: 释义比较短的, 否则选项长得放不下 */
    public static synchronized List<Word> quizPool() {
        all();
        return quizPool;
    }

    public static int size() {
        return all().size();
    }

    /** 按关键字筛选: 单词或释义包含都算命中 */
    public static List<Word> search(String keyword) {
        List<Word> all = all();
        if (keyword == null || keyword.trim().isEmpty()) {
            return all;
        }
        String key = keyword.trim().toLowerCase();
        List<Word> result = new ArrayList<Word>();
        for (Word word : all) {
            if (word.getWord().toLowerCase().contains(key)
                    || word.getExplain().toLowerCase().contains(key)) {
                result.add(word);
            }
        }
        return result;
    }

    public static Word random() {
        List<Word> all = all();
        return all.get(RANDOM.nextInt(all.size()));
    }

    public static Word randomQuizWord() {
        List<Word> pool = quizPool();
        return pool.get(RANDOM.nextInt(pool.size()));
    }

    private static void load() {
        List<Word> list = new ArrayList<Word>();
        try {
            String json = Gdx.files.internal(JSON_PATH).readString("UTF-8");
            JsonValue root = new JsonReader().parse(json);
            JsonValue array = root.get("words");
            for (JsonValue item = array == null ? null : array.child; item != null; item = item.next) {
                String word = item.getString("word", "").trim();
                String explain = item.getString("explain", "").trim();
                if (word.isEmpty() || explain.isEmpty()) {
                    continue;
                }
                list.add(new Word(word, explain));
            }
        } catch (Exception e) {
            Gdx.app.error("WordBank", "解析 " + JSON_PATH + " 失败, 使用内置词条兜底", e);
        }

        if (list.isEmpty()) {
            // 词库文件缺失或者坏了也不至于白屏
            list.add(new Word("memory", "n. 记忆,记忆力;内存,[计]存储器,回忆;"));
            list.add(new Word("happy", "adj. 高兴的; 愉快的; 开心的;"));
            list.add(new Word("dictionary", "n. 词典,字典;"));
        }

        List<Word> pool = new ArrayList<Word>();
        for (Word word : list) {
            String brief = word.getBrief();
            if (brief.length() >= 2 && brief.length() <= 34) {
                pool.add(word);
            }
        }
        if (pool.size() < 8) {
            pool = new ArrayList<Word>(list);
        }

        words = Collections.unmodifiableList(list);
        quizPool = Collections.unmodifiableList(pool);
    }
}
