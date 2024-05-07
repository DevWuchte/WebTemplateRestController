package at.htlklu.spring.configs;


import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
//@EnableWebMvc
public class InternationalizationConfiguration implements WebMvcConfigurer
{
   // interessasnte Links
   // https://o7planning.org/11691/create-a-multi-language-web-application-with-spring-boot#a4778949

   @Bean
   public LocaleResolver localeResolver() 
   {
      SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
      sessionLocaleResolver.setDefaultLocale(Locale.GERMAN);
      return sessionLocaleResolver;
   }
   
   @Bean
   public LocaleChangeInterceptor localeChangeInterceptor() 
   {
      LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
      localeChangeInterceptor.setParamName("lang");
      return localeChangeInterceptor;
   }
   
   @Override
   public void addInterceptors(InterceptorRegistry registry) 
   {
      registry.addInterceptor(localeChangeInterceptor());
   }
   
}