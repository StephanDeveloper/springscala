package com.springscala.boot.modules.contact.domain

import org.springframework.core.io.ResourceLoader

import scala.beans.BeanProperty

case class Attachment(name: String, path: String) {

  def getFile(resourceLoader: ResourceLoader) = {
    val resource = resourceLoader.getResource("classpath:" + path)
    if (resource.exists()) Some(resource.getFile) else None
  }

  def getFileExtension = path.take(1 + path.lastIndexOf("."))
}

case class Mail(to: String, @BeanProperty subject: String, @BeanProperty content: String, cc: Option[Array[String]], bcc: Option[Array[String]], attachments: Option[Array[Attachment]])
