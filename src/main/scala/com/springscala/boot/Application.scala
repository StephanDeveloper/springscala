package com.springscala.boot

import javax.annotation.PostConstruct

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.core.env.{AbstractEnvironment, Environment}

/**
 * Bootstrap spring - scala application.
 */
@SpringBootApplication
class Application {

  private val log = LoggerFactory.getLogger(classOf[Application])

  @Autowired
  var env: Environment = _

  @PostConstruct
  def init() = {
    if (env.getActiveProfiles.isEmpty) {
      log.info("No Spring profile configured, running with default configuration")
      log.info("Default tomcat port: " + env.getProperty("server.port"))
    }
    else {
      log.info("Running with Spring profile(s): {}", env.getActiveProfiles.toString)
    }
  }
}

object Application extends App {
  System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "sma-stage")
  SpringApplication.run(classOf[Application])
}