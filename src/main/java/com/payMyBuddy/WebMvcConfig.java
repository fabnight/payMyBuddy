package com.payMyBuddy;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/menu").setViewName("menu");
		registry.addViewController("/contact").setViewName("contact");
		registry.addViewController("/registration").setViewName("registration");
		registry.addViewController("/transfer").setViewName("transfer");
		registry.addViewController("/").setViewName("home");
	}
}
