package com.reviews.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * Class to create beans that don't fit in elsewhere
 * @author varun
 *
 */
@Configuration
public class ApplicationBeanConfig {

	@Bean
	public Module jodaModule() {
		return new JodaModule();
	}
	
}
