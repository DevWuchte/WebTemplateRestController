package at.htlklu.spring.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//https://spring.io/guides/gs/rest-service-cors/#global-cors-configuration
//https://howtodoinjava.com/spring-boot2/spring-cors-configuration/#global-cors

// Wird für die React-Application benätigt, weil die Portnummer der React-App und die Portnummer des WebServides unterschiedlich sind
@Configuration
public class CorsConfiguration
{
    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer()
        {
            @Override
            public void addCorsMappings(CorsRegistry registry)
            {
//                registry.addMapping("/**").allowedOrigins("http://localhost:3000");
                registry.addMapping("/**").allowedMethods("*").allowedOrigins("*").allowedHeaders("*");
            }
        };
    }
}
