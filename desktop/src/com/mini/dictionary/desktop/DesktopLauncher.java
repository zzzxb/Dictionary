package com.mini.dictionary.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mini.dictionary.DictionaryMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Dictionary"; // 窗口标题
		config.addIcon("icon/appicon.png", Files.FileType.Internal);
		config.width = 820; // 窗口宽度
		config.height = 580; // 窗口高度
        config.resizable = false; // 禁止窗口最大化
		new LwjglApplication(new DictionaryMain(), config);
	}
}
