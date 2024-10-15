package com.clip.bootstrap

import com.clip.application.ApplicationConfig
import com.clip.client.ClientConfig
import com.clip.persistence.PersistenceConfig
import com.clip.security.SecurityConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(
	value = [
		ApplicationConfig::class,
		ClientConfig::class,
		PersistenceConfig::class,
		SecurityConfig::class
	]
)
class BootstrapModuleApplication

fun main(args: Array<String>) {
	runApplication<BootstrapModuleApplication>(*args)
}
