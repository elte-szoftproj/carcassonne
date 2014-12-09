package hu.elte.szoftproj.carcassonne;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import hu.elte.szoftproj.carcassonne.service.GameService;
import hu.elte.szoftproj.carcassonne.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GameMainDesktop implements CarcassonneServiceProvider {

	@Autowired
	GameService gameService;

	@Autowired
	LobbyService lobbyService;

	@Override
	public GameService getGameService() {
		return gameService;
	}

	@Override
	public LobbyService getLobbyService() {
		return lobbyService;
	}

	private static final String CONFIG_LOCATION = "hu.elte.szoftproj.carcassonne.config";
	private static final String DEFAULT_PROFILE = "dev";

	private static String[] args;

	private static WebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation(CONFIG_LOCATION);
		context.getEnvironment().setDefaultProfiles(DEFAULT_PROFILE);
		context.refresh();
		return context;
	}

	public static void main (String[] args) {
		GameMainDesktop.args = args;
		getContext().getBean(GameMainDesktop.class).startApp();
	}

	public GameMainDesktop() {
	}

	public void startApp() {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		System.out.println(getLobbyService());
		config.width = 1280;
		config.height = 1024;
		config.useGL20 = true;

		if (false && args.length < 3) {
			throw new RuntimeException("Requires arguments!");
		}

		System.out.println(lobbyService + " xxx");

		LinkedList<String> players = new LinkedList<>(Arrays.asList(args));
		players.removeFirst();
		String address = null;
		if (args[1].equals("server")) {
			players.removeFirst();
			address = args[2];
		}

		new LwjglApplication(new GameMain(this, args[1], address, players), config);
	}
}
