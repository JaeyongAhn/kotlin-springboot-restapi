package com.popfunding.api.config

import com.popfunding.api.v1.advice.CInvalidTokenException
import com.popfunding.api.v1.advice.CValidationException
import com.popfunding.api.v1.response.JsonDataResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorController {
    @Autowired
    lateinit var messageLoader: MessageLoader

    @ExceptionHandler(CValidationException::class)
    fun validationException(e: CValidationException): ResponseEntity<JsonDataResponse<Map<String, String?>>> {
        return ResponseEntity.status(400).body(
            JsonDataResponse<Map<String, String?>>(
                -1,
                "",
                e.errors.map { (it as FieldError).field to it.defaultMessage }.toMap()
            )
        )
    }

    @ExceptionHandler(CInvalidTokenException::class)
    fun tokenInvalidException(e: CInvalidTokenException): ResponseEntity<String> {
        return ResponseEntity.status(400).body(
            messageLoader.getMessage("invalidToken.msg")
        )
    }
}