package com.springscala.boot.modules.contact.service

import java.util.Locale
import javax.mail.internet.{InternetAddress, MimeMessage}

import com.springscala.boot.modules.contact.domain.Mail
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.{Ignore, FunSpec}
import org.scalatest.mock.MockitoSugar
import org.springframework.core.env.Environment
import org.springframework.core.io.ResourceLoader
import org.springframework.mail.javamail.{JavaMailSenderImpl, MimeMessageHelper}
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.thymeleaf.context.Context
import org.thymeleaf.spring4.SpringTemplateEngine

@Ignore
@RunWith(classOf[SpringJUnit4ClassRunner])
class MailServiceTest extends FunSpec with MockitoSugar {


  val environmentMock = mock[Environment]
  val javaMailSenderMock = mock[JavaMailSenderImpl]
  val resourceLoaderMock = mock[ResourceLoader]
  val templateEngineMock = mock[SpringTemplateEngine]

  describe("MailService function sendMail") {
    val mailService = new MailService
    mailService.env = environmentMock
    mailService.javaMailSender = javaMailSenderMock
    mailService.resourceLoader = resourceLoaderMock
    mailService.templateEngine = templateEngineMock

    val mh = mock[MimeMessageHelper]
    mh.setFrom(new InternetAddress("springscala"))

    val m = mock[MimeMessage]
    when(javaMailSenderMock.createMimeMessage()).thenReturn(m)

    when(environmentMock.getProperty("mail.from")).thenReturn("springscala@tool.com")
    when(environmentMock.getProperty("mail.replyTo")).thenReturn("service@tool.com")

    //@TODO: split impl to simplify test
    describe("should create mail") {
      it("with form property") {
        when(templateEngineMock.process("test", new Context(Locale.ENGLISH))).thenReturn("")
        doNothing().when(javaMailSenderMock).send(m)

        mailService.sendEmail(Mail("test@domain.com", "test", "test content", None, None, None))

        println(m)
        //verify(javaMailSenderMock).send(mh)
      }

    }
  }
}
