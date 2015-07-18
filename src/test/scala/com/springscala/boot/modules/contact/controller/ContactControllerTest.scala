package com.springscala.boot.modules.contact.controller

import com.springscala.boot.modules.contact.service.MailService
import com.springscala.boot.util.ScalaObjectMapper
import org.mockito.Mockito._
import org.scalatest.FunSpec
import org.scalatest.mock.MockitoSugar
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders._
import org.springframework.test.web.servlet.result.MockMvcResultMatchers._
import org.springframework.test.web.servlet.setup.MockMvcBuilders._

class ContactControllerTest extends FunSpec with MockitoSugar {

  val mailServiceStub = mock[MailService]

  describe("ContactController") {
    val jsonObjectMapper = new ScalaObjectMapper()

    describe("POST /api/contact") {
      it("should return 200") {
        doNothing().when(mailServiceStub).sendEmail _
        val contactController = new ContactController(mailService = mailServiceStub)
        val mvc = standaloneSetup(contactController).build()

        mvc.perform(post("/api/contact")
          .content(jsonObjectMapper.writeValueAsString(Map(
          "email" -> "test@domain.com",
          "subject" -> "test"
        )))
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
      }

      it("should return 404") {
        doNothing().when(mailServiceStub).sendEmail _
        val contactController = new ContactController(mailService = mailServiceStub)
        val mvc = standaloneSetup(contactController).build()

        mvc.perform(post("/api/contact")
          .content(jsonObjectMapper.writeValueAsString(Map.empty))
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().is4xxClientError())
      }

      // @TODO: cant mock exception
      //      it("should return 500") {
      //        val mail = Mail("test@domain.com", "test", "test content", None, None, None)
      //        //when(mailServiceStub.sendEmail(mail)).thenReturn(new RuntimeException)
      //
      //        when(mailServiceStub.sendEmail(mail)).thenThrow(new IllegalArgumentException("test"))
      //
      //        val contactController = new ContactController(mailService = mailServiceStub)
      //        val mvc = standaloneSetup(contactController).build()
      //
      //        mvc.perform(post("/api/contact")
      //          .content(jsonObjectMapper.writeValueAsString(Map(
      //          "email" -> "test@domain.com",
      //          "subject" -> "test"
      //        )))
      //          .contentType(MediaType.APPLICATION_JSON))
      //          .andExpect(status().is5xxServerError())
      //      }
    }
  }
}
