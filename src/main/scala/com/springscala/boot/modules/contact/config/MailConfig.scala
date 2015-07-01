package com.springscala.boot.modules.contact.config

import org.slf4j.LoggerFactory
import org.springframework.boot.bind.RelaxedPropertyResolver
import org.springframework.context.EnvironmentAware
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.core.env.Environment
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class MailConfig extends EnvironmentAware {

  private val log = LoggerFactory.getLogger(classOf[MailConfig])

  private var propertyResolver: RelaxedPropertyResolver = _

  @Override
  override def setEnvironment(environment: Environment) = {
    this.propertyResolver = new RelaxedPropertyResolver(environment, "mail.")
  }

  @Bean(name = Array("JavaMailSender"))
  def mailSender() = {
    log.info("Execute Bean for mail configuration")

    val host = propertyResolver.getProperty("host", "host")
    val port = propertyResolver.getProperty("port", "0")
    val user = propertyResolver.getProperty("username")
    val password = propertyResolver.getProperty("password")
    val protocol = propertyResolver.getProperty("protocol", "smtp")
    val tls = propertyResolver.getProperty("tls", "false")
    val auth = propertyResolver.getProperty("auth", "false")

    val sender = new JavaMailSenderImpl()
    if (host != null && !host.isEmpty) {
      sender.setHost(host)
    }
    else {
      sender.setHost("127.0.0.1")
    }

    sender.setPort(port.toInt)
    sender.setUsername(user)
    sender.setPassword(password)

    val sendProperties = new java.util.Properties()
    sendProperties.setProperty("mail.smtp.auth", auth)
    sendProperties.setProperty("mail.smtp.starttls.enable", tls)
    sendProperties.setProperty("mail.transport.protocol", protocol)
    sender.setJavaMailProperties(sendProperties)

    sender
  }
}
