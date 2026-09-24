package com.mini.dictionary.util;

/**
 * 文本处理小工具(新加的)。
 *
 * 注意: 项目里用的位图字体(font18/myfont)没有全角标点(，。：)和 —★→ 这类符号,
 * 所以拼界面文字时统一用半角标点, 这里也只会生成半角字符。
 */
public final class TextUtil {
    private TextUtil() {
    }

    /** 取第一行 */
    public static String firstLine(String text) {
        if (text == null) {
            return "";
        }
        int index = text.indexOf('\n');
        String line = index >= 0 ? text.substring(0, index) : text;
        return line.trim();
    }

    /** 把换行/制表符/连续空格压成单个空格, 方便在列表里显示 */
    public static String oneLine(String text) {
        if (text == null) {
            return "";
        }
        String value = text.replace('\r', ' ').replace('\n', ' ').replace('\t', ' ');
        while (value.contains("  ")) {
            value = value.replace("  ", " ");
        }
        return value.trim();
    }

    /** 超长截断, 尾巴用 ... 代替 */
    public static String truncate(String text, int max) {
        if (text == null) {
            return "";
        }
        if (max <= 3 || text.length() <= max) {
            return text;
        }
        return text.substring(0, max - 3) + "...";
    }

    /** 压成一行再截断, 列表用 */
    public static String brief(String text, int max) {
        return truncate(oneLine(text), max);
    }

    public static String percent(int part, int total) {
        if (total <= 0) {
            return "0%";
        }
        return Math.round(part * 100f / total) + "%";
    }
}
