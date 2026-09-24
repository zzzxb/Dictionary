package com.mini.dictionary.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 学习数据(新加的): 设置 / 生词本 / 学习统计, 统一存在 libGDX 的 Preferences 里。
 *
 * 桌面端会落到用户目录的 .prefs 下面, 关掉软件再打开数据还在, 不用数据库也不用自己写文件。
 */
public final class StudyData {

    private static final String PREFS_NAME = "dictionary-study";
    private static final String DAY_PATTERN = "yyyy-MM-dd";

    private static final int DEFAULT_DAILY_GOAL = 20;
    private static final int DEFAULT_QUIZ_SIZE = 10;

    private static Preferences prefs;
    private static int version = 0;

    private StudyData() {
    }

    private static Preferences p() {
        if (prefs == null) {
            prefs = Gdx.app.getPreferences(PREFS_NAME);
        }
        return prefs;
    }

    /** 数据每变一次 +1, 页面靠它判断要不要刷新界面 */
    public static int version() {
        return version;
    }

    private static void changed() {
        version++;
        p().flush();
    }

    private static String today() {
        return new SimpleDateFormat(DAY_PATTERN, Locale.CHINA).format(new Date());
    }

    /** 今天的日期数字(比如 20260924), 给"每日单词"当随机种子: 同一天抽到同一批词 */
    public static int getDaySeed() {
        return Integer.parseInt(today().replace("-", ""));
    }

    private static boolean isYesterday(String day) {
        if (day == null || day.isEmpty()) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return new SimpleDateFormat(DAY_PATTERN, Locale.CHINA).format(calendar.getTime()).equals(day);
    }

    // ------------------------------------------------------------------ 设置

    public static int getDailyGoal() {
        return p().getInteger("dailyGoal", DEFAULT_DAILY_GOAL);
    }

    public static void setDailyGoal(int value) {
        p().putInteger("dailyGoal", clamp(value, 5, 200));
        changed();
    }

    public static int getQuizSize() {
        return p().getInteger("quizSize", DEFAULT_QUIZ_SIZE);
    }

    public static void setQuizSize(int value) {
        p().putInteger("quizSize", clamp(value, 5, 50));
        changed();
    }

    public static boolean isAutoNext() {
        return p().getBoolean("autoNext", true);
    }

    public static void setAutoNext(boolean value) {
        p().putBoolean("autoNext", value);
        changed();
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    // ---------------------------------------------------------------- 生词本

    /** 生词本内容: 每项是 {单词, 释义} */
    public static List<String[]> getNotebook() {
        List<String[]> list = new ArrayList<String[]>();
        String raw = p().getString("notebook", "");
        for (String line : raw.split("\n")) {
            if (line.trim().isEmpty()) {
                continue;
            }
            int tab = line.indexOf('\t');
            if (tab < 0) {
                list.add(new String[]{line.trim(), ""});
            } else {
                list.add(new String[]{line.substring(0, tab), line.substring(tab + 1)});
            }
        }
        return list;
    }

    public static int notebookSize() {
        return getNotebook().size();
    }

    public static boolean inNotebook(String word) {
        if (word == null) {
            return false;
        }
        for (String[] item : getNotebook()) {
            if (item[0].equalsIgnoreCase(word.trim())) {
                return true;
            }
        }
        return false;
    }

    /** @return true 表示这次真的加进去了, false 表示本来就有 */
    public static boolean addToNotebook(String word, String explain) {
        if (word == null || word.trim().isEmpty()) {
            return false;
        }
        String name = word.trim();
        if (inNotebook(name)) {
            return false;
        }
        List<String[]> list = getNotebook();
        list.add(new String[]{name, TextUtil.oneLine(explain)});
        saveNotebook(list);
        return true;
    }

    public static boolean removeFromNotebook(String word) {
        if (word == null) {
            return false;
        }
        List<String[]> list = getNotebook();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i)[0].equalsIgnoreCase(word.trim())) {
                list.remove(i);
                saveNotebook(list);
                return true;
            }
        }
        return false;
    }

    private static void saveNotebook(List<String[]> list) {
        StringBuilder builder = new StringBuilder();
        for (String[] item : list) {
            if (builder.length() > 0) {
                builder.append('\n');
            }
            builder.append(item[0]).append('\t').append(TextUtil.oneLine(item[1]));
        }
        p().putString("notebook", builder.toString());
        changed();
    }

    // ------------------------------------------------------------------ 统计

    /** 背单词页点一次"认识/不认识"就记一次 */
    public static void recordReview(boolean known) {
        touchToday();
        p().putInteger("totalReviewed", getTotalReviewed() + 1);
        if (known) {
            p().putInteger("knownCount", getKnownCount() + 1);
        } else {
            p().putInteger("unknownCount", getUnknownCount() + 1);
        }
        p().putInteger("todayReviewed", getTodayReviewedRaw() + 1);
        changed();
    }

    /** 测验答一题 */
    public static void recordQuiz(boolean correct) {
        touchToday();
        p().putInteger("quizTotal", getQuizTotal() + 1);
        p().putInteger("todayQuiz", getTodayQuizRaw() + 1);
        if (correct) {
            p().putInteger("quizCorrect", getQuizCorrect() + 1);
        }
        changed();
    }

    public static int getTodayReviewed() {
        return today().equals(p().getString("lastDay", "")) ? getTodayReviewedRaw() : 0;
    }

    public static int getTodayQuiz() {
        return today().equals(p().getString("lastDay", "")) ? getTodayQuizRaw() : 0;
    }

    private static int getTodayReviewedRaw() {
        return p().getInteger("todayReviewed", 0);
    }

    private static int getTodayQuizRaw() {
        return p().getInteger("todayQuiz", 0);
    }

    public static int getTotalReviewed() {
        return p().getInteger("totalReviewed", 0);
    }

    public static int getKnownCount() {
        return p().getInteger("knownCount", 0);
    }

    public static int getUnknownCount() {
        return p().getInteger("unknownCount", 0);
    }

    public static int getQuizTotal() {
        return p().getInteger("quizTotal", 0);
    }

    public static int getQuizCorrect() {
        return p().getInteger("quizCorrect", 0);
    }

    public static int getStreak() {
        return p().getInteger("streak", 0);
    }

    public static int getBestStreak() {
        return p().getInteger("bestStreak", 0);
    }

    public static int getStudyDays() {
        return p().getInteger("studyDays", 0);
    }

    /** 每天第一次学习时结算打卡: 昨天学过就 +1, 否则从 1 重新开始 */
    private static void touchToday() {
        String today = today();
        if (today.equals(p().getString("lastDay", ""))) {
            return;
        }
        int streak = isYesterday(p().getString("lastDay", "")) ? getStreak() + 1 : 1;
        p().putInteger("streak", streak);
        p().putInteger("bestStreak", Math.max(streak, getBestStreak()));
        p().putInteger("studyDays", getStudyDays() + 1);
        p().putInteger("todayReviewed", 0);
        p().putInteger("todayQuiz", 0);
        p().putString("lastDay", today);
    }

    // ------------------------------------------------------------------ 重置

    /** 只清统计, 生词本和设置保留 */
    public static void resetStats() {
        for (String key : new String[]{"totalReviewed", "knownCount", "unknownCount", "quizTotal",
                "quizCorrect", "todayReviewed", "todayQuiz", "streak", "bestStreak", "studyDays", "lastDay"}) {
            p().remove(key);
        }
        changed();
    }

    /** 恢复出厂: 统计 + 生词本 + 设置全清 */
    public static void resetAll() {
        p().clear();
        changed();
    }
}
