package com.mini.dictionary.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mini.dictionary.Dictionary;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 820;
		config.height = 560;
		config.title = "Dictionary";
		new LwjglApplication(new Dictionary(), config);
	}
}
