package com.springscala.boot.modules.account.controller

import com.springscala.boot.modules.account.domain.User
import com.springscala.boot.modules.account.service.UserService
import org.hamcrest.Matchers._
import org.mockito.Mockito.when
import org.scalatest.FunSpec
import org.scalatest.mock.MockitoSugar
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.{jsonPath, status}
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class UserControllerTest extends FunSpec with MockitoSugar {

  val userServiceStub = mock[UserService]
  val user = User("tian09", "egon", "weider", "secret", "a@b.de", "EN", List.empty)

  describe("UserController") {
    describe("GET /api/users") {
      it("should return 200") {
        val userController = new UserController(userService = userServiceStub)
        val mvc = standaloneSetup(userController).build()

        mvc.perform(get("/api/users")).
          andExpect(status().isOk)
      }
    }

    describe("GET /api/users/{username}") {
      it("should return 200") {
        when(userServiceStub.getByUsername("tian09")).thenReturn(Some(user))
        val userController = new UserController(userService = userServiceStub)
        val mvc = standaloneSetup(userController).build()

        mvc.perform(get("/api/users/tian09")).
          andExpect(status().isOk)
      }

      it("should return user") {
        when(userServiceStub.getByUsername("tian09")).thenReturn(Some(user))
        val userController = new UserController(userService = userServiceStub)
        val mvc = standaloneSetup(userController).build()

        mvc.perform(get("/api/users/tian09")).
          andExpect(status().isOk).
          andExpect(jsonPath("$.username", is("tian09"))).
          andExpect(jsonPath("$.firstname", is("egon"))).
          andExpect(jsonPath("$.lastname", is("weider"))).
          andExpect(jsonPath("$.password", is("secret"))).
          andExpect(jsonPath("$.email", is("a@b.de"))).
          andExpect(jsonPath("$.languageKey", is("EN")))
      }

      it("should return 404") {
        when(userServiceStub.getByUsername("tian09")).thenReturn(None)
        val userController = new UserController(userService = userServiceStub)
        val mvc = standaloneSetup(userController).build()

        mvc.perform(get("/api/users/test")).
          andExpect(status().is4xxClientError())
      }
    }
  }
}
