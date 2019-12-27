package com.popfunding.api.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import java.util.*
import net.rakugakibox.util.YamlResourceBundle;
import org.springframework.context.MessageSource
import org.springframework.context.support.ResourceBundleMessageSource

@Configuration
class MessageConfiguration : WebMvcConfigurer {

    /* disable for only use browser locale
    @Bean
    fun localeResolver(): LocaleResolver{
        val slr: SessionLocaleResolver = SessionLocaleResolver()
        slr.setDefaultLocale(Locale.KOREAN)
        return slr
    }*/

    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor {
        val lci: LocaleChangeInterceptor = LocaleChangeInterceptor()
        lci.paramName = "lang"
        return lci
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeChangeInterceptor())
    }

    @Bean
    fun messageSource(
        @Value("\${spring.messages.basename}") basename: String,
        @Value("\${spring.messages.encoding}") encoding: String
    ): MessageSource {
        val ms: YamlMessageSource = YamlMessageSource()
        ms.setBasename(basename)
        ms.setDefaultEncoding(encoding)
        ms.setAlwaysUseMessageFormat(true)
        ms.setUseCodeAsDefaultMessage(true)
        ms.setFallbackToSystemLocale(true)
        return ms
    }

    class YamlMessageSource : ResourceBundleMessageSource() {
        override fun doGetBundle(basename: String, locale: Locale): ResourceBundle {
            return ResourceBundle.getBundle(basename, locale, YamlResourceBundle.Control.INSTANCE)
        }
    }
}