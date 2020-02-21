package ru.fors.util.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.fors.util.StringToDateMapper

@Configuration
open class UtilConfiguration {

    @Bean
    open fun provideDateMapper(): StringToDateMapper {
        return StringToDateMapper()
    }
}