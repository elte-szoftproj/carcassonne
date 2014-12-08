package hu.elte.szoftproj.carcassonne.persistence.server.test;

import org.eclipse.jetty.server.Server;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class ServerMain {

    private Server server;

    private static final int DEFAULT_PORT = 8080;

    private static final String CONFIG_LOCATION = "hu.elte.szoftproj.carcassonne.config";
    private static final String DEFAULT_PROFILE = "dev";

    public ServerMain() throws Exception {
        server = getContext().getBean(Server.class);
        startEmbeddedServer();
    }

    void startEmbeddedServer() throws Exception {
        server.start();
        server.join();
    }

    private static WebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION);
        context.getEnvironment().setDefaultProfiles(DEFAULT_PROFILE);
        context.refresh();
        return context;
    }

    public static void main(String[] args) throws Exception {
        try {
            new ServerMain();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
