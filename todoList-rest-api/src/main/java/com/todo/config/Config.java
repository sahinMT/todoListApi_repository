package com.todo.config;

import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.todo.model.dto.ListItemDto;
import com.todo.model.dto.TodoListDto;
import com.todo.model.entity.ListItem;
import com.todo.model.entity.TodoList;
import com.todo.model.entity.User;

@Configuration
@EnableJpaRepositories(basePackages = "com.todo")
@ComponentScan(basePackages = { "com.todo" })
@EntityScan(basePackageClasses = { TodoList.class, ListItem.class, TodoListDto.class, ListItemDto.class, User.class })
@EnableAutoConfiguration
public class Config extends WebSecurityConfigurerAdapter {
	Logger logger = LoggerFactory.getLogger(Config.class);

	@Value("${spring.application.name:toDoListApi}")
	private String applicationName;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
	}

	@Bean
	public javax.validation.Validator validator() {
		return new org.springframework.validation.beanvalidation.LocalValidatorFactoryBean();
	}

	/**
	 * 
	 * @param event
	 */
	@EventListener
	public void handleEvent(Object event) {
		if (event instanceof ContextRefreshedEvent) {
			logger.info("ContextRefreshedEvent triggered");
			load();
		} else if (event instanceof ContextStartedEvent) {
			logger.info("ContextStartedEvent triggered");
		} else if (event instanceof ContextStoppedEvent) {
			logger.info("ContextStoppedEvent triggered");
		} else if (event instanceof ContextClosedEvent) {
			logger.info("ContextClosedEvent triggered");
			unload();
		} else if (event instanceof ApplicationReadyEvent) {
			logger.info("ApplicationReadyEvent triggered");
		} else if (event instanceof ApplicationStartedEvent) {
			logger.info("ApplicationStartedEvent triggered");
		} else if (event instanceof ServletWebServerInitializedEvent) {
			logger.info("ServletWebServerInitializedEvent triggered");
		} else {
			logger.warn("An unexpected event triggered : {}, ", event);
		}
	}

	/**
	 * 
	 */
	public void load() {
		logger.info("Loading configuration for {} ", applicationName);
		logger.info("Application load succeed");
	}

	/**
	 * 
	 */
	public void unload() {
		logger.info("Unloading");
	}

	@Provider
	@Produces({ MediaType.APPLICATION_JSON })
	public static class JacksonJsonProvider
			extends org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider {
		public JacksonJsonProvider() {
			ObjectMapper objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
					.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).registerModule(new JodaModule());
			setMapper(objectMapper);
		}
	}

	@EventListener
	public void handleContextRefresh(ContextRefreshedEvent event) {
		ApplicationContext applicationContext = event.getApplicationContext();
		RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext
				.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
		map.forEach((key, value) -> logger.info("{} {}", key, value));
	}

}