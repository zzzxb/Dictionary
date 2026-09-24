package com.mini.dictionary.util;

public class MenuInformation {
    // 菜单的位置
    public static final int FRAMEHEIGHT = 430; // 跟hint.png图片高度有关
    public static final int BUTTONHEIGHT = 45; // 跟select.png图片的高度相同
    // 按钮开关 true为启用按钮，反之 (3-6 号按钮是新功能: 词库/生词本/测验/统计)
    public static final boolean OPENBUTTON[] = {true,true,true,true,true,true,true};
    // 默认选项
    public static int defaultOptionOn = 0; // 默认选中按钮 0 - 5 其中一个
    // 设置选项的按钮位置
    public static final int SETTINGBUTTONPOSITION[] = {10,10};
    // 按钮0-5 的显示文本
    public static final String BUTTONTEXT[] = {"查单词","背单词","词库","生词本","测验","统计"};
    // 按钮0-6的按钮以及设置按钮的纹理路径
    public static final String BUTTONONEPATH[] = {"icon/dict.png","icon/dict-hover.png", "icon/select.png"};
    public static final String BUTTONTWOPATH[] = {"icon/wb.png","icon/wb-hover.png", "icon/select.png"};
    public static final String BUTTONTHREEPATH[] = {"icon/wb.png","icon/wb-hover.png", "icon/select.png"};
    public static final String BUTTONFOURPATH[] = {"icon/wb.png","icon/wb-hover.png", "icon/select.png"};
    public static final String BUTTONFIVEPATH[] = {"icon/wb.png","icon/wb-hover.png", "icon/select.png"};
    public static final String BUTTONSIXPATH[] = {"icon/wb.png","icon/wb-hover.png", "icon/select.png"};
    public static final String SETTINGBUTTONPATH[] = {"icon/wb-setting-normal.png","icon/wb-setting-hover.png"};
    // 字体路径 - 换成 font18: 老的 default.fnt 只带了"查单词/背单词"这几个字,
    // 新加的"词库/生词本/测验/统计"它没有字形, 菜单文字会显示不出来
    public static final String FONTPATH[] = {"font/font18.fnt","font/font18.png"};
}
