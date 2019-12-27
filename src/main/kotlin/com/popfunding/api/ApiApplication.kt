package com.popfunding.api

import com.popfunding.api.config.GracefulShutdown
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ApiApplication{

	@Bean
	fun gracefulShutdown(): GracefulShutdown{
		return GracefulShutdown()
	}

	@Bean
	fun webServerFactory(gracefulShutdown: GracefulShutdown): ConfigurableServletWebServerFactory{
		val factory: TomcatServletWebServerFactory = TomcatServletWebServerFactory()
		factory.addConnectorCustomizers(gracefulShutdown)
		return factory
	}
}

fun main(args: Array<String>) {
	runApplication<ApiApplication>(*args)
}
