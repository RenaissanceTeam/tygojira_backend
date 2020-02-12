package ru.fors.app.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.method.HandlerTypePredicate
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
open class WebMvcConfig : WebMvcConfigurer {
    override fun configurePathMatch(configurer: PathMatchConfigurer) {
        configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController::class.java))
    }
}