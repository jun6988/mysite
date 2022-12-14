package com.bitacademy.mysite.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:com/bitacademy/mysite/config/web/fileupload.properties")
public class FileUploadConfig implements WebMvcConfigurer{
	
	@Autowired
	private Environment env;
	
	// Multipart Resolver 파일업로드
	@Bean
	public MultipartResolver multipartResolver() { 
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		
		multipartResolver.setMaxUploadSize(env.getProperty("fileupload.maxUploadSize", Long.class));
		multipartResolver.setMaxInMemorySize(env.getProperty("fileupload.maxInMemorySize", Integer.class));
		multipartResolver.setDefaultEncoding(env.getProperty("fileupload.defaultEncoding"));
		
		return multipartResolver;
	}
	
	// MVC Resource Mapping 저장한 파일과 url을 맵핑
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler(env.getProperty("fileupload.resourceMapping") + "/**")
		.addResourceLocations("file:" + env.getProperty("fileupload.uploadLocation") + "/");
	}
}