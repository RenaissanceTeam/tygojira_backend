package ru.fors.app.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
import org.springframework.format.support.DefaultFormattingConversionService
import org.springframework.format.support.FormattingConversionService
import java.time.format.DateTimeFormatter


@Configuration
open class DateTimeConfig(
        @Value("\${app.dateFormat}")
        private val dateFormat: String,
        @Value("\${app.dateTimeFormat}")
        private val dateTimeFormat: String
) {
    @Bean
    open fun conversionService(): FormattingConversionService {
        val conversionService = DefaultFormattingConversionService(false)
        val registrar = DateTimeFormatterRegistrar()
        registrar.setDateFormatter(DateTimeFormatter.ofPattern(dateFormat))
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(dateTimeFormat))
        registrar.registerFormatters(conversionService)
        return conversionService
    }
}