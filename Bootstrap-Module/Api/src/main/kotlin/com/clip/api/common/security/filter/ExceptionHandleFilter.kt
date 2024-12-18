package com.clip.api.common.security.filter

import com.clip.api.common.exception.ExceptionResponse
import com.clip.common.exception.BusinessException
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
@Order(0)
class ExceptionHandleFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            handleException(e, response)
        }
    }

    private fun handleException(e: Exception, response: HttpServletResponse) {
        run {
            when (e) {
                is BusinessException -> {
                    response.status = e.httpStatus
                    response.outputStream.write(
                        objectMapper.writeValueAsString(
                            ExceptionResponse.of(e)
                        ).toByteArray()
                    )
                    response.contentType = "application/json"
                }

                else -> {
                    response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                    response.outputStream.write(
                        objectMapper.writeValueAsString(
                            ExceptionResponse(
                                message = e.message ?: "알 수 없는 오류가 발생했습니다.",
                                code = "UNKNOWN-ERROR"
                            )
                        ).toByteArray()
                    )
                    response.contentType = "application/json"
                }
            }
        }

    }
}