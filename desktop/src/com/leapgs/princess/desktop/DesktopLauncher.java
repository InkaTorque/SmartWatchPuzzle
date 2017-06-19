package com.leapgs.princess.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.leapgs.princess.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Princess Puzzle";
		config.width = 400;
		config.height = 400;
		new LwjglApplication(new MainGame(), config);
	}
}
