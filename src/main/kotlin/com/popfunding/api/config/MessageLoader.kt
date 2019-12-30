package com.popfunding.api.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component

@Component
class MessageLoader {
    @Autowired
    lateinit var messageSource: MessageSource

    fun getCode(code: String): Int {
        return getCode(code, null)
    }

    fun getCode(code: String, args: Array<Any>?): Int {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale()).toInt()
    }

    fun getMessage(code: String): String {
        return getMessage(code, null)
    }

    fun getMessage(code: String, args: Array<Any>?): String {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale())
    }
}