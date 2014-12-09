package hu.elte.szoftproj.carcassonne;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import hu.elte.szoftproj.carcassonne.persistence.client.ClientFactory;
import hu.elte.szoftproj.carcassonne.service.GameService;
import hu.elte.szoftproj.carcassonne.service.LobbyService;
import hu.elte.szoftproj.carcassonne.service.impl.GameServiceImplSwitchable;
import hu.elte.szoftproj.carcassonne.service.impl.LobbyServiceImplSwitchable;
import org.eclipse.jetty.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GameMainDesktop implements CarcassonneServiceProvider {

	@Autowired
	GameServiceImplSwitchable gameService;

	@Autowired
	LobbyServiceImplSwitchable lobbyService;

	@Autowired
	private Server server;

	@Autowired
	ClientFactory clientFactory;

	@Override
	public GameServiceImplSwitchable getGameService() {
		return gameService;
	}

	@Override
	public LobbyServiceImplSwitchable getLobbyService() {
		return lobbyService;
	}

	@Override
	public ClientFactory getClientFactory() {
		return clientFactory;
	}

	@Override
	public Server getServer() {
		return server;
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

		if (false && args.length < 2) {
			throw new RuntimeException("Requires arguments!");
		}

		System.out.println(lobbyService + " xxx");

		LinkedList<String> players = new LinkedList<>(Arrays.asList(args));
		players.removeFirst();
		String address = null;
		if (args[0].equals("client")) {
			players.removeFirst();
			address = args[1];
		}

		System.out.println(players);

		new LwjglApplication(new GameMain(this, args[0], address, players), config);
	}
}
