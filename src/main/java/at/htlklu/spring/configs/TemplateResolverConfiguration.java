package at.htlklu.spring.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class TemplateResolverConfiguration
{
//	https://www.baeldung.com/spring-thymeleaf-template-directory
//	http://www.javabyexamples.com/thymeleaf-multiple-template-locations-using-spring-boot

    @Bean
	public ClassLoaderTemplateResolver TemplateResolver1()
	{
		ClassLoaderTemplateResolver TemplateResolver = new ClassLoaderTemplateResolver();
		TemplateResolver.setPrefix("views/glf1/");						// den schließenden Slash nicht vergessen !!!
		TemplateResolver.setSuffix(".html");
		TemplateResolver.setTemplateMode(TemplateMode.HTML);
		TemplateResolver.setCharacterEncoding("UTF-8");
		TemplateResolver.setOrder(1);
		TemplateResolver.setCheckExistence(true);
		return TemplateResolver;
	}

}
