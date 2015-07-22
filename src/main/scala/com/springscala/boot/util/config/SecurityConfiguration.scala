package com.springscala.boot.util.config

import com.springscala.boot.security.{AjaxAuthenticationFailureHandler, AjaxAuthenticationSuccessHandler, UserDetailsServiceAuth}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.{EnableWebSecurity, WebSecurityConfigurerAdapter}
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  var userDetailsServiceAuth: UserDetailsServiceAuth = _

  @Autowired
  var ajaxAuthenticationSuccessHandler: AjaxAuthenticationSuccessHandler = _

  @Autowired
  var ajaxAuthenticationFailureHandler: AjaxAuthenticationFailureHandler = _

  @Bean
  def passwordEncoder: PasswordEncoder = new BCryptPasswordEncoder()

  @Override
  override def configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
    authenticationManagerBuilder
      .userDetailsService(userDetailsServiceAuth)
      .passwordEncoder(passwordEncoder)
  }

  @Override
  override def configure(http: HttpSecurity) {
    http
      .formLogin()
      .loginProcessingUrl("/api/login")
      .successHandler(ajaxAuthenticationSuccessHandler)
      .failureHandler(ajaxAuthenticationFailureHandler)
      .usernameParameter("username")
      .passwordParameter("password")
      .permitAll()
      .and()
      .authorizeRequests()
      .antMatchers("/api/users/**").permitAll()
      .antMatchers("/api/contact").denyAll()
  }
}
