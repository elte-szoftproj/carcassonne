package hu.elte.szoftproj.carcassonne.config;

import hu.elte.szoftproj.carcassonne.GameMainDesktop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GuiConfiguration {

    @Bean
    GameMainDesktop getMainClass() {
        return new GameMainDesktop();
    }

}
