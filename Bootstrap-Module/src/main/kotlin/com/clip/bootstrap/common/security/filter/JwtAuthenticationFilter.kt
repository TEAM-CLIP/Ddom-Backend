package com.clip.bootstrap.common.security.filter

import com.clip.application.user.port.`in`.TokenResolveUsecase
import com.clip.bootstrap.common.security.vo.UserAuthentication
import com.clip.common.security.SecurityContext
import com.clip.common.security.SecurityContextHolder
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
@Order(1)
class JwtAuthenticationFilter(
    private val tokenResolveUsecase: TokenResolveUsecase
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader(AUTHORIZATION_HEADER)
        val userAuthentication = if (token != null && token.startsWith(BEARER_PREFIX)) {
            val accessToken = token.substring(BEARER_PREFIX.length)
            val resolveResponse = tokenResolveUsecase.resolveAccessToken(accessToken)
            UserAuthentication(resolveResponse.userId)
        } else {
            UserAuthentication("Anonymous")
        }
        SecurityContextHolder.setContext(SecurityContext(userAuthentication))


        val filterResponse = filterChain.doFilter(request, response)

        SecurityContextHolder.clearContext()

        return filterResponse
    }

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_PREFIX = "Bearer "
    }
}