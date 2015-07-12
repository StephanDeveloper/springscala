package com.springscala.boot.modules.account.domain

import com.springscala.boot.modules.account.domain.AuthorityRole.AuthorityRole
import org.ektorp.support.CouchDbDocument

import scala.beans.BeanProperty

object AuthorityRole extends Enumeration {
  type AuthorityRole = Value
  val ROLE_USER, ROLE_ADMIN = Value
}

case class Authority(@BeanProperty name: String)

object Authority {
  def apply(name: AuthorityRole) = new Authority(name.toString)
}

case class User
(@BeanProperty username: String,
 @BeanProperty lastname: String,
 @BeanProperty password: String,
 @BeanProperty email: String,

 // has to be a java arrayList, otherwise it will not be serialized for couchdb
 @BeanProperty authorities: java.util.List[Authority]) extends CouchDbDocument

object User {

  import scala.collection.JavaConverters._

  def apply(username: String, lastname: String, password: String, email: String, authorities: List[Authority]) = {
    new User(username, lastname, password, email, authorities.asJava)
  }
}