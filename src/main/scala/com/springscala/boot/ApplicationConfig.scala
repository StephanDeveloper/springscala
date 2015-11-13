package com.springscala.boot

import org.ektorp.CouchDbConnector
import org.ektorp.http.StdHttpClient
import org.ektorp.impl.{StdCouchDbConnector, StdCouchDbInstance}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.core.env.Environment

@Configuration
class ApplicationConfig {

  @Autowired
  var env: Environment = _

  @Bean
  def springscalaDbConnector: CouchDbConnector = {
    val db = new StdCouchDbConnector(springscalaDbName(), springscalaInstance)
    db.createDatabaseIfNotExists()
    db
  }

  @Bean
  def springscalaDbName() =
    env.getProperty("springscalaDbName.dbName", classOf[String], "springscala")


  @Bean
  def springscalaInstance: StdCouchDbInstance = {
    val httpClient = new StdHttpClient.Builder()
      .host(env.getProperty("springscalaDbName.host", "localhost"))
      .port(env.getProperty("springscalaDbName.port", "5984").toInt)
      .build()

    new StdCouchDbInstance(httpClient)
  }
}
