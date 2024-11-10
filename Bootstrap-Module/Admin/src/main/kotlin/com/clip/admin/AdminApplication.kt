package com.clip.admin

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
        PersistenceConfig::class,
        ApplicationConfig::class,
        ClientConfig::class,
        SecurityConfig::class
    ]
)
class AdminApplication

fun main(args: Array<String>) {
    runApplication<AdminApplication>(*args)
}
