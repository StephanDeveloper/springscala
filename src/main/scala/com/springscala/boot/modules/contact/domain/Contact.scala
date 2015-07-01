package com.springscala.boot.modules.contact.domain

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.springscala.boot.util.OptionDeserializer

import scala.beans.BeanProperty


class Contact {

  @BeanProperty
  @JsonDeserialize(using = classOf[OptionDeserializer[String]])
  var name: Option[String] = None

  @BeanProperty
  @JsonDeserialize(using = classOf[OptionDeserializer[String]])
  var subject: Option[String] = None

  @BeanProperty
  @JsonDeserialize(using = classOf[OptionDeserializer[String]])
  var email: Option[String] = None

  @BeanProperty
  @JsonDeserialize(using = classOf[OptionDeserializer[String]])
  var content: Option[String] = None
}
