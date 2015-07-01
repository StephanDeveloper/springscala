package com.springscala.boot.modules.contact.service

import javax.mail.internet.MimeMessage

import com.springscala.boot.modules.contact.domain.Mail
import org.mockito.Mockito._
import org.scalatest.FunSpec
import org.scalatest.mock.MockitoSugar
import org.springframework.core.env.Environment
import org.springframework.core.io.ResourceLoader
import org.springframework.mail.javamail.JavaMailSenderImpl

class MailServiceTest extends FunSpec with MockitoSugar {

  val environmentMock = mock[Environment]
  val javaMailSenderMock = mock[JavaMailSenderImpl]
  val resourceLoaderMock = mock[ResourceLoader]

  describe("MailService function sendMail") {
    it("should create mail") {
      val mailService = new MailService

      val m = mock[MimeMessage]
      when(javaMailSenderMock.createMimeMessage()).thenReturn(m)
      doNothing().when(javaMailSenderMock).send(m)

      mailService.env = environmentMock
      mailService.javaMailSender = javaMailSenderMock
      mailService.resourceLoader = resourceLoaderMock

      mailService.sendEmail(Mail("test@domain.com", "test", "test content", None, None, None))
    }
  }
}
