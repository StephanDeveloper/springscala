package com.springscala.boot.util

import org.ektorp.http.StdHttpClient
import org.ektorp.impl.{StdCouchDbConnector, StdCouchDbInstance}

class CouchDbImporter(xs: List[Any]) {

  val httpClient = new StdHttpClient.Builder()
    .host("localhost")
    .port(5984)
    .build()

  val couchdbInstance = new StdCouchDbInstance(httpClient)
  val connector = new StdCouchDbConnector("springscala", couchdbInstance)

  xs.foreach(anyObject => connector.create(anyObject))
}
