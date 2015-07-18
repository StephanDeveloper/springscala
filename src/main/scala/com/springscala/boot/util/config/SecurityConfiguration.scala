package com.springscala.boot.util.config

import com.springscala.boot.security.UserDetailsServiceAuth
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.{EnableWebSecurity, WebSecurityConfigurerAdapter}

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  var x: UserDetailsServiceAuth = _

  @Override
  override def configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
    authenticationManagerBuilder.inMemoryAuthentication()


    x.loadUserByUsername("stephan")
  }

  @Override
  override def configure(http: HttpSecurity) {

  }
}
