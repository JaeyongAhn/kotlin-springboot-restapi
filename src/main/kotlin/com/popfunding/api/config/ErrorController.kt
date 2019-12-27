package com.popfunding.api.config

import com.popfunding.api.v1.advice.CTokenInvalidException
import com.popfunding.api.v1.advice.CValidationException
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorController {
    @ExceptionHandler(CValidationException::class)
    fun validationException(e: CValidationException): ResponseEntity<Map<String, String?>> {
        return ResponseEntity.status(400).body(
            e.errors.map { (it as FieldError).field to it.defaultMessage }.toMap()
        )
    }
    @ExceptionHandler(CTokenInvalidException::class)
    fun tokenInvalidException(e: CTokenInvalidException): ResponseEntity<String> {
        return ResponseEntity.status(400).body(
            "Token invalid"
        )
    }
}