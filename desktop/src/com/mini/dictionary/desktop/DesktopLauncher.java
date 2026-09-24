package com.mini.dictionary.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mini.dictionary.DictionaryMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Dictionary"); // 窗口标题
		config.setWindowedMode(820, 580); // 窗口宽度和高度
		config.setResizable(false);
		config.setWindowIcon("icon/appicon.png"); // macOS 上无效, 其它平台有效
		new Lwjgl3Application(new DictionaryMain(), config);
	}
}
