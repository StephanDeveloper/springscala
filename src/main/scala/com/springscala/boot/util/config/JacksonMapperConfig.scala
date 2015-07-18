package com.springscala.boot.util.config

import com.springscala.boot.util.ScalaObjectMapper
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

@Configuration
class JacksonMapperConfig {
  
  @Bean
  def mappingJacksonHttpMessageConverter: MappingJackson2HttpMessageConverter = {
    new MappingJackson2HttpMessageConverter(new ScalaObjectMapper())
  }

  @Bean
  def localValidatorFactoryBean: javax.validation.Validator = {
    new LocalValidatorFactoryBean()
  }
}
