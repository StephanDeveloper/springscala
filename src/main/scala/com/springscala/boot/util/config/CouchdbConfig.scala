package com.springscala.boot.util.config

import org.ektorp.CouchDbConnector
import org.ektorp.http.StdHttpClient
import org.ektorp.impl.{StdCouchDbConnector, StdCouchDbInstance}
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class CouchdbConfig {

  @Bean
  def springscalaDbConnector: CouchDbConnector = {
    val db = new StdCouchDbConnector("springscala", springscalaInstance)
    db.createDatabaseIfNotExists()
    db
  }

  @Bean
  def springscalaInstance: StdCouchDbInstance = {
    val httpClient = new StdHttpClient.Builder()
      .host("localhost")
      .port(5984)
      .build()

    new StdCouchDbInstance(httpClient)
  }
}