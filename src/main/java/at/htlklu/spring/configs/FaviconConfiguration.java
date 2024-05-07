package at.htlklu.spring.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class FaviconConfiguration
{
    // not working
//    @Bean
//    public SimpleUrlHandlerMapping customFaviconHandlerMapping()
//    {
//        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
//        mapping.setOrder(Integer.MIN_VALUE);
//        mapping.setUrlMap(Collections.singletonMap("/static/images/Umbrella.png", faviconRequestHandler()));
//        return mapping;
//    }
//
//    @Bean
//    protected ResourceHttpRequestHandler faviconRequestHandler()
//    {
//        ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
//        requestHandler.setLocations(Collections.singletonList(new ClassPathResource("/")));
//        return requestHandler;
//    }


//    @Bean
//    public SimpleUrlHandlerMapping myFaviconHandlerMapping()
//    {
//        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
//        mapping.setOrder(Integer.MIN_VALUE);
//        mapping.setUrlMap(Collections.singletonMap("/favicon.ico", myFaviconRequestHandler()));
//        return mapping;
//    }
//
//    @Autowired
//    ApplicationContext applicationContext;
//
//    @Bean
//    protected ResourceHttpRequestHandler myFaviconRequestHandler()
//    {
//        ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
//        requestHandler.setLocations(Arrays.<Resource> asList(applicationContext.getResource("/")));
//        requestHandler.setCacheSeconds(0);
//        return requestHandler;
//    }

}
