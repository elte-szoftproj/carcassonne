package hu.elte.szoftproj.carcassonne.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import hu.elte.szoftproj.carcassonne.java.core.GameMain;

public class GameMainDesktop {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 1024;
		config.useGL20 = true;
		new LwjglApplication(new GameMain(), config);
	}
}
