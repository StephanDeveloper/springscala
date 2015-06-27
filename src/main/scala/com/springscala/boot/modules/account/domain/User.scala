package com.springscala.boot.modules.account.domain

import java.util.UUID

import scala.beans.BeanProperty

case class User(@BeanProperty username: String, @BeanProperty password: String) {

  @BeanProperty
  val id = UUID.randomUUID.toString
}
