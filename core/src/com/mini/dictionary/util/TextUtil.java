package com.mini.dictionary.util;

import java.util.ArrayList;
import java.util.List;

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

    /** 中日韩字符按两个单位算宽度, 其它按一个(位图字体里全角字符差不多就是半角的两倍宽) */
    private static boolean isWide(char c) {
        return c >= 0x2E80;
    }

    /**
     * 按显示宽度折行, 让释义能塞进单词卡/search 结果那种固定宽度的地方。
     * 超过 maxLines 行就把最后一行结尾换成 ... , 免得文字溢出到别的控件上。
     */
    public static String wrap(String text, int unitsPerLine, int maxLines) {
        String value = oneLine(text);
        if (value.isEmpty() || unitsPerLine <= 0 || maxLines <= 0) {
            return "";
        }
        List<String> lines = new ArrayList<String>();
        StringBuilder line = new StringBuilder();
        int units = 0;
        int index = 0;
        while (index < value.length()) {
            char c = value.charAt(index);
            int width = isWide(c) ? 2 : 1;
            if (units + width > unitsPerLine) {
                lines.add(line.toString().trim());
                line.setLength(0);
                units = 0;
                if (lines.size() == maxLines) {
                    break;
                }
                if (c == ' ') {
                    index++;
                    continue;
                }
            }
            line.append(c);
            units += width;
            index++;
        }
        if (lines.size() < maxLines && line.length() > 0) {
            lines.add(line.toString().trim());
        }
        if (index < value.length() && !lines.isEmpty()) {
            int last = lines.size() - 1;
            String tail = lines.get(last);
            lines.set(last, (tail.length() > 2 ? tail.substring(0, tail.length() - 2) : tail) + "...");
        }
        StringBuilder result = new StringBuilder();
        for (String item : lines) {
            if (result.length() > 0) {
                result.append('\n');
            }
            result.append(item);
        }
        return result.toString();
    }
}
