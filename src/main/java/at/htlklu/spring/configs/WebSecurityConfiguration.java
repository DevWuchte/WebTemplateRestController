package at.htlklu.spring.configs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter
{
    private static Logger logger = LogManager.getLogger(WebSecurityConfiguration.class);

    // Variante 1 (no Authentication)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
    }

    protected void configure(HttpSecurity http) throws Exception
    {
        // Variante 1 (ohne Security)
        http.csrf().disable();                  // sonst funktionieren Aufrufe mit 'POST' nicht!
    }

}
