package com.springscala.boot.modules.contact.controller

import com.springscala.boot.modules.contact.domain.{Attachment, Contact, Mail}
import com.springscala.boot.modules.contact.service.MailService
import com.typesafe.scalalogging.slf4j.LazyLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType._
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.mail.MailException
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RequestMethod, RestController}


@RequestMapping(Array("/api"))
@RestController
class ContactController @Autowired()(mailService: MailService) extends LazyLogging {

  @RequestMapping(value = Array("/contact"), method = Array(RequestMethod.POST), consumes = Array(APPLICATION_JSON_VALUE), produces = Array(APPLICATION_JSON_VALUE))
  def contact(@RequestBody contact: Contact): ResponseEntity[Void] = {
    if (contact.subject.isDefined && contact.email.isDefined) {
      val mail = Mail(
        to = contact.email.get,
        subject = contact.subject.getOrElse("Contact Inquiry"),
        content = contact.content.getOrElse("No contact content"),
        cc = None,
        bcc = None,
        attachments = Some(Array(Attachment("logo example", "images/logo_example.png")))
      )

      try {
        mailService.sendEmail(mail)
        new ResponseEntity[Void](HttpStatus.OK)
      }
      catch {
        case e: MailException =>
          logger.error(e.getMessage)
          new ResponseEntity[Void](HttpStatus.INTERNAL_SERVER_ERROR)
      }
    } else {
      new ResponseEntity[Void](HttpStatus.BAD_REQUEST)
    }
  }
}
