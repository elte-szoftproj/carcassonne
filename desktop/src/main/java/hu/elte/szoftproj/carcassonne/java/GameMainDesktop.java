package hu.elte.szoftproj.carcassonne.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import hu.elte.szoftproj.carcassonne.core.GameMain;

public class GameMainDesktop {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL20 = true;
		new LwjglApplication(new GameMain(), config);
	}
}
