package com.springscala.boot.modules.contact.config

import com.typesafe.scalalogging.slf4j.LazyLogging
import org.springframework.context.annotation.{Bean, Configuration, Description}
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

@Configuration
class ThymeleafConfig extends LazyLogging {

  @Bean
  @Description("Template resolver serving HTML 5 emails")
  def templateResolver = {
    val templateResolver = new ClassLoaderTemplateResolver
    templateResolver.setPrefix("mails/")
    templateResolver.setSuffix(".html")
    templateResolver.setTemplateMode("HTML5")
    templateResolver.setCharacterEncoding("UTF-8")
    templateResolver.setOrder(1)

    templateResolver
  }
}
