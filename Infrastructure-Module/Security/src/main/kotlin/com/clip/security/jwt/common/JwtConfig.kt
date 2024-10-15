package com.clip.security.jwt.common

import com.clip.security.jwt.user.UserJwtProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(UserJwtProperties::class)
class JwtConfig {
}