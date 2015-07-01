package com.springscala.boot.modules.contact.controller

import com.springscala.boot.modules.contact.service.MailService
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FunSpec, Ignore}
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders._
import org.springframework.test.web.servlet.result.MockMvcResultMatchers._
import org.springframework.test.web.servlet.setup.MockMvcBuilders._

@Ignore // scala.collection.immutable.$colon$colon.hd$1()Ljava/lang/Object;
class ContactControllerTest extends FunSpec with MockitoSugar {

  val mailServiceStub = mock[MailService]

  describe("ContactController") {
    describe("POST /api/contact") {
      it("should return 200") {
        val contactController = new ContactController(mailService = mailServiceStub)
        val mvc = standaloneSetup(contactController).build()

        mvc.perform(post("/api/contact")).
          andExpect(status().isOk())
      }
    }
  }
}
