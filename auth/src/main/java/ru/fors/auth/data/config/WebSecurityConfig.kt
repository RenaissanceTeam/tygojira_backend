package ru.fors.auth.data.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import ru.fors.auth.data.jwt.CustomUserDetailsService
import ru.fors.auth.data.jwt.JwtAuthenticationEntryPoint
import ru.fors.auth.data.jwt.JwtAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
open class WebSecurityConfig(
        private val tokenAuthFilter: JwtAuthenticationFilter,
        private val customUserDetailsService: CustomUserDetailsService,
        private val unauthorizedHandler: JwtAuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore(tokenAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
                    .authorizeRequests()
                    .antMatchers("/login", "/signup").permitAll()
                    .anyRequest().authenticated()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth ?: return

        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
    }

    @Bean(name = [BeanIds.AUTHENTICATION_MANAGER])
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }
}
