package com.springscala.boot.util.config

import com.springscala.boot.util.ScalaObjectMapper
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

@Configuration
class JacksonMapperConfig {
  
  @Bean
  def mappingJacksonHttpMessageConverter: MappingJackson2HttpMessageConverter = {
    new MappingJackson2HttpMessageConverter(new ScalaObjectMapper())
  }
}
