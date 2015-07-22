package com.springscala.boot.modules.account.domain

import javax.validation.Validation
import javax.validation.constraints.{Pattern, Size}

import com.fasterxml.jackson.annotation.JsonIgnore
import com.springscala.boot.modules.account.domain.AuthorityRole.AuthorityRole
import org.ektorp.support.CouchDbDocument
import org.hibernate.validator.constraints.Email

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

case class User
(
  @BeanProperty
  @(Size@beanGetter)(min = 1, max = 50)
  @(Pattern@beanGetter)(regexp = "^[a-z0-9]*$")
  username: String,

  @BeanProperty
  @(Size@beanGetter)(max = 50)
  firstname: String,

  @BeanProperty
  @(Size@beanGetter)(max = 50)
  lastname: String,

  @BeanProperty
  @JsonIgnore
  @(Size@beanGetter)(min = 1, max = 100)
  password: String,

  @BeanProperty
  @(Email@beanGetter)
  @(Size@beanGetter)(max = 100)
  email: String,

  @BeanProperty
  languageKey: String,

  // has to be a java arrayList, otherwise it will not be serialized for couchdb
  @BeanProperty authorities: java.util.List[Authority])
  extends CouchDbDocument {
  def activated: Boolean = false
}

object User {

  import scala.collection.JavaConversions._
  import scala.collection.JavaConverters._

  val factory = Validation.buildDefaultValidatorFactory()
  val validator = factory.getValidator

  def apply(username: String, firstname: String, lastname: String,
            password: String, email: String, languageKey: String,
            authorities: List[Authority]) =
  {
    val newUser = new User(username, firstname, lastname, password, email, languageKey, authorities.asJava)
    val validation = validator.validate(newUser)

    if (validation.size() > 0) {
      val errorMessages = for (a <- validation) yield s"Value: ${a.getInvalidValue} Constraints: ${a.getMessage}"
      throw new Exception(errorMessages.foldLeft("")(_ + _))
    }

    newUser
  }
}