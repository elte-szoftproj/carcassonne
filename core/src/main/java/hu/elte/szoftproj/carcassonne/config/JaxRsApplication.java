package hu.elte.szoftproj.carcassonne.config;

import hu.elte.szoftproj.carcassonne.persistence.server.BadJsonExceptionMapper;
import hu.elte.szoftproj.carcassonne.persistence.server.UnrecognizedPropertyExceptionMapper;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by Zsolt on 2014.12.07..
 */
public class JaxRsApplication extends ResourceConfig {

    public JaxRsApplication() {
        packages("hu.elte.szoftproj.carcassonne.persistence.server.rest");

        // debugging
        register(LoggingFilter.class);
        register(UnrecognizedPropertyExceptionMapper.class);
        register(BadJsonExceptionMapper.class);
    }
}
