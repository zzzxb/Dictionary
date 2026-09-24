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

    /** 精确查词(忽略大小写和首尾空格), 查不到返回 null */
    public static Word find(String text) {
        if (text == null) {
            return null;
        }
        String key = text.trim();
        if (key.isEmpty()) {
            return null;
        }
        for (Word word : all()) {
            if (word.getWord().trim().equalsIgnoreCase(key)) {
                return word;
            }
        }
        return null;
    }

    /** 查不到时的"你是不是想找": 先按包含匹配, 还是没命中就按编辑距离猜拼错的英文 */
    public static List<Word> suggest(String keyword, int limit) {
        if (keyword == null || keyword.trim().isEmpty() || limit <= 0) {
            return new ArrayList<Word>();
        }
        String key = keyword.trim().toLowerCase();
        List<Word> result = new ArrayList<Word>();
        for (Word word : all()) {
            if (word.getWord().toLowerCase().contains(key)) {
                result.add(word);
                if (result.size() >= limit) {
                    return result;
                }
            }
        }
        for (Word word : all()) {
            if (word.getExplain().toLowerCase().contains(key) && !result.contains(word)) {
                result.add(word);
                if (result.size() >= limit) {
                    return result;
                }
            }
        }
        return nearest(key, limit);
    }

    /** 按编辑距离找拼写最接近的几个词, 比如 memry -> memory */
    private static List<Word> nearest(String key, int limit) {
        int allowed = Math.max(1, key.length() / 4);
        List<Word> candidates = new ArrayList<Word>();
        List<Integer> distances = new ArrayList<Integer>();
        for (Word word : all()) {
            String spell = word.getWord().trim().toLowerCase();
            if (spell.isEmpty() || Math.abs(spell.length() - key.length()) > allowed) {
                continue;
            }
            int distance = distance(spell, key);
            if (distance > allowed) {
                continue;
            }
            int at = candidates.size();
            for (int i = 0; i < distances.size(); i++) {
                if (distance < distances.get(i)) {
                    at = i;
                    break;
                }
            }
            candidates.add(at, word);
            distances.add(at, distance);
        }
        return new ArrayList<Word>(candidates.subList(0, Math.min(limit, candidates.size())));
    }

    /** 编辑距离(滚动数组版) */
    private static int distance(String left, String right) {
        int[] previous = new int[right.length() + 1];
        int[] current = new int[right.length() + 1];
        for (int j = 0; j <= right.length(); j++) {
            previous[j] = j;
        }
        for (int i = 1; i <= left.length(); i++) {
            current[0] = i;
            for (int j = 1; j <= right.length(); j++) {
                int cost = left.charAt(i - 1) == right.charAt(j - 1) ? 0 : 1;
                current[j] = Math.min(Math.min(current[j - 1] + 1, previous[j] + 1), previous[j - 1] + cost);
            }
            int[] swap = previous;
            previous = current;
            current = swap;
        }
        return previous[right.length()];
    }

    /**
     * 今天要背的一批单词: 用当天日期当随机种子, 所以同一天抽到的永远是同一批,
     * 关掉软件再打开进度条还对得上。只从"释义能塞进单词卡"的词里抽。
     */
    public static List<Word> dailyCards(int count) {
        List<Word> pool = new ArrayList<Word>(quizPool());
        Collections.shuffle(pool, new Random(StudyData.getDaySeed()));
        int size = Math.max(1, Math.min(count, pool.size()));
        return new ArrayList<Word>(pool.subList(0, size));
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
