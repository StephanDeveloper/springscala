package com.springscala.boot.modules.contact.controller

import com.springscala.boot.modules.contact.domain.{Attachment, Contact, Mail}
import com.springscala.boot.modules.contact.service.MailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType._
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RequestMethod, RestController}


@RequestMapping(Array("/api"))
@RestController
class ContactController @Autowired()(mailService: MailService) {

  @RequestMapping(value = Array("/contact"), method = Array(RequestMethod.POST), consumes = Array(APPLICATION_JSON_VALUE), produces = Array(APPLICATION_JSON_VALUE))
  def contact(@RequestBody contact: Contact): ResponseEntity[Void] = {
    if (contact.subject.isDefined && contact.email.isDefined) {
      val mail = Mail(contact.email.get, contact.subject.get, contact.content.getOrElse("No contact content"), None, None, Some(Array(Attachment("testblabla", "images/test.png"))))

      try {
        mailService.sendEmail(mail)
        new ResponseEntity[Void](HttpStatus.OK)
      }
      catch {
        case e: Exception =>
          println(e)
          new ResponseEntity[Void](HttpStatus.INTERNAL_SERVER_ERROR)
      }
    }

    new ResponseEntity[Void](HttpStatus.BAD_REQUEST)
  }
}
