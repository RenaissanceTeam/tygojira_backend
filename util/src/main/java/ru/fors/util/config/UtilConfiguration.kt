package ru.fors.util.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.fors.util.StringDateMapper

@Configuration
open class UtilConfiguration {

    @Bean
    open fun provideDateMapper(): StringDateMapper {
        return StringDateMapper()
    }
}