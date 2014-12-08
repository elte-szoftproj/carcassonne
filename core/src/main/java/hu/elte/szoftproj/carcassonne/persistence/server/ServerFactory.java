package hu.elte.szoftproj.carcassonne.persistence.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class ServerFactory {

    public  static final int DEFAULT_PORT = 8080;

    @Autowired
    WebApplicationContext context;

    public Server getServer(int port) throws Exception {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setContextPath("/");
        ServletHolder sh = new ServletHolder(new ServletContainer());
        sh.setInitParameter("javax.ws.rs.Application", "hu.elte.szoftproj.carcassonne.config.JaxRsApplication");
        contextHandler.addServlet(sh, "/*");
        contextHandler.addEventListener(new ContextLoaderListener(context));
        contextHandler.setResourceBase(new ClassPathResource("/").getURI().toString());

        Server server = new Server(port);
        server.setHandler(contextHandler);

        return server;
    }
}
