package ru.fors.app.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
import org.springframework.format.support.DefaultFormattingConversionService
import org.springframework.format.support.FormattingConversionService
import ru.fors.util.dateFormat
import ru.fors.util.dateTimeFormat
import java.time.format.DateTimeFormatter


@Configuration
open class DateTimeConfig {
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