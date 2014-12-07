package hu.elte.szoftproj.carcassonne.persistence.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.io.IOException;

public class ServerMain {

    private static final int DEFAULT_PORT = 8080;

    private static final String CONFIG_LOCATION = "hu.elte.szoftproj.carcassonne.config";
    private static final String DEFAULT_PROFILE = "dev";
    private static final String MAPPING_URL = "/*";

    public ServerMain() throws Exception {
        startEmbeddedServer();
    }

    static void startEmbeddedServer() throws Exception {
        Server server = configureServer(getContext());
        server.start();
        server.join();
    }

    private static Server configureServer(WebApplicationContext context) throws IOException {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setContextPath("/");
        ServletHolder sh = new ServletHolder(new ServletContainer());
        sh.setInitParameter("javax.ws.rs.Application", "hu.elte.szoftproj.carcassonne.config.JaxRsApplication");
        contextHandler.addServlet(sh, MAPPING_URL);
        contextHandler.addEventListener(new ContextLoaderListener(context));
        contextHandler.setResourceBase(new ClassPathResource("/").getURI().toString());

        Server server = new Server(DEFAULT_PORT);
        server.setHandler(contextHandler);
        return server;
    }

    private static WebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION);
        context.getEnvironment().setDefaultProfiles(DEFAULT_PROFILE);
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
