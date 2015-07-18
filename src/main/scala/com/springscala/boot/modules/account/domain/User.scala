package com.springscala.boot.modules.account.domain

import javax.validation.constraints.Size

import com.springscala.boot.modules.account.domain.AuthorityRole.AuthorityRole
import org.ektorp.support.CouchDbDocument

import scala.annotation.meta.beanGetter
import scala.beans.BeanProperty

object AuthorityRole extends Enumeration {
  type AuthorityRole = Value
  val ROLE_USER, ROLE_ADMIN = Value
}

case class Authority(@BeanProperty name: String)

object Authority {
  def apply(name: AuthorityRole) = new Authority(name.toString)
}

// @TODO: add firstname and language Key
// @TODO size annotation is not working
case class User
(@BeanProperty @(Size@beanGetter)(min = 1, max = 2) username: String,
 @BeanProperty lastname: String,
 @BeanProperty password: String,
 @BeanProperty email: String,

 // has to be a java arrayList, otherwise it will not be serialized for couchdb
 @BeanProperty authorities: java.util.List[Authority]) extends CouchDbDocument

object User {

  import com.springscala.boot.util.validators.Validator

  import scala.collection.JavaConverters._

  val validator = new Validator

  def apply(username: String, lastname: String, password: String, email: String, authorities: List[Authority]) = {
    if (!validator.NotNull.isValid(username)) throw new Exception(validator.NotNull.errorMessage)
    new User(username, lastname, password, email, authorities.asJava)
  }
}