package com.popfunding.api.v1.advice

import org.springframework.validation.ObjectError
import java.lang.RuntimeException

class CValidationException(val errors: List<ObjectError>) : RuntimeException()