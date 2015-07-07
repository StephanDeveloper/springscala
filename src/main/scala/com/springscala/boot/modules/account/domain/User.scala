package com.springscala.boot.modules.account.domain

import org.ektorp.support.CouchDbDocument

import scala.beans.BeanProperty

case class User(@BeanProperty username: String, @BeanProperty password: String) extends CouchDbDocument
