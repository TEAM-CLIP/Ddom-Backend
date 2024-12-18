package com.clip.api.common.exception

import com.clip.api.common.exception.ExceptionResponse
import com.clip.common.exception.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<ExceptionResponse> {
        return ResponseEntity.status(e.httpStatus).body(ExceptionResponse.of(e))
    }
}