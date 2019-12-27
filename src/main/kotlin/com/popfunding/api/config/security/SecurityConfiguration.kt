package com.popfunding.api.config.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var jwtTokenProvider: JwtTokenProvider

    override fun configure(http: HttpSecurity?) {
        http
            ?.httpBasic()?.disable()
            ?.csrf()?.disable()
            ?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ?.and()
            ?.authorizeRequests()
            ?.antMatchers("/api/v1/admin/signup/**", "/api/v1/admin/signin/**")?.permitAll()
            ?.antMatchers("/api/v1/admin/**")?.hasRole("ADMIN")
            ?.antMatchers("/user/**")?.permitAll()
            ?.anyRequest()?.hasRole("USER")
            ?.and()
            ?.addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
    }
}