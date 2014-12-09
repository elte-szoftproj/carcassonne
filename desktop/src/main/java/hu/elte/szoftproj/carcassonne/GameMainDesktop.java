package hu.elte.szoftproj.carcassonne;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import hu.elte.szoftproj.carcassonne.service.GameService;
import hu.elte.szoftproj.carcassonne.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

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

	private static WebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation(CONFIG_LOCATION);
		context.getEnvironment().setDefaultProfiles(DEFAULT_PROFILE);
		context.refresh();
		return context;
	}

	public static void main (String[] args) {
		getContext().getBean(GameMainDesktop.class);
	}

	public GameMainDesktop() {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		System.out.println(getLobbyService());
		config.width = 1280;
		config.height = 1024;
		config.useGL20 = true;
		new LwjglApplication(new GameMain(this), config);
	}
}
