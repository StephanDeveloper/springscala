package com.springscala.boot.modules.contact.service

import javax.activation.{DataHandler, DataSource, FileDataSource}
import javax.mail.internet.{InternetAddress, MimeBodyPart}

import com.springscala.boot.modules.contact.domain.{Attachment, Mail}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.core.io.ResourceLoader
import org.springframework.mail.javamail.{JavaMailSenderImpl, MimeMessageHelper}
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

/**
 * Service to send mails. Mail attachments must be on the classpath
 * eg. resources/images/test.png.
 */
@Service
class MailService {

  private val log = LoggerFactory.getLogger(classOf[MailService])

  @Autowired
  var env: Environment = _

  @Autowired
  var javaMailSender: JavaMailSenderImpl = _

  @Autowired
  var resourceLoader: ResourceLoader = _

  @Async
  def sendEmail(mail: Mail) {
    val mimeMessage = javaMailSender.createMimeMessage()

    // move into separate method
    val message = new MimeMessageHelper(mimeMessage, true, "UTF-8")
    message.setFrom(new InternetAddress(env.getProperty("mail.from"), "UTF-8"))
    message.setReplyTo(new InternetAddress(env.getProperty("mail.replyTo")))
    message.setSubject(mail.subject)
    message.setTo(mail.to)
    message.setText(mail.content, true)

    if (mail.attachments.isDefined) {
      for (attachment <- mail.attachments.get) {
        message.getMimeMultipart.addBodyPart(this.getInlineAttachment(attachment))
      }
    }

    if (mail.cc.isDefined) {
      message.setCc(mail.cc.get)
    }

    if (mail.bcc.isDefined) {
      message.setBcc(mail.bcc.get)
    }

    javaMailSender.send(mimeMessage)
    log.info("Sent e-mail to User '{}'", mail.to)
  }

  private def getInlineAttachment(attachment: Attachment) = {
    val mimeBodyPart = new MimeBodyPart()

    attachment.getFile(resourceLoader) match {
      case Some(file) =>
        val ds: DataSource = new FileDataSource(file)
        mimeBodyPart.setDataHandler(new DataHandler(ds))
        mimeBodyPart.setDisposition("inline")
        mimeBodyPart.setContentID("id" + attachment.name)
        mimeBodyPart.setFileName(attachment.name)
        mimeBodyPart

      case None => null
    }
  }
}
