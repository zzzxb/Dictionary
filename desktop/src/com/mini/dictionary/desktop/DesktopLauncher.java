package com.mini.dictionary.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mini.dictionary.DictionaryMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = ""; // 窗口标题
		config.width = 820; // 窗口宽度
		config.height = 580; // 窗口高度
		new LwjglApplication(new DictionaryMain(), config);
	}
}
