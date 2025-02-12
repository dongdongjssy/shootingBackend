package io.goose.cloud.gateway.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;




@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
	
	@Autowired
	private Global global;


	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		
		System.out.println("映射路径为 =" + global.getShooting());

		
        registry.addResourceHandler("/map_src/**").addResourceLocations("file:"+ global.getShooting());
    }
}
