package com.springscala.boot.modules.account.controller

import com.springscala.boot.modules.account.service.UserService
import org.hamcrest.Matchers._
import org.scalamock.matchers.Matchers
import org.scalamock.scalatest.MockFactory
import org.scalatest.FunSpec
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.{jsonPath, status}
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class UserControllerTest extends FunSpec with Matchers with MockFactory {

  val userServiceStub = stub[UserService]

  describe("UserController") {
    describe("GET /users") {
      it("should return 200") {
        val userController = new UserController(userService = userServiceStub)
        val mvc = standaloneSetup(userController).build()

        mvc.perform(get("/api/users")).
          andExpect(status().isOk())
      }
    }

    describe("GET /users/{username}") {
      it("should return 200") {
        val userController = new UserController(userService = userServiceStub)
        val mvc = standaloneSetup(userController).build()

        mvc.perform(get("/api/users/egon")).
          andExpect(status().isOk())
      }

      it("should return user") {
        val userController = new UserController(userService = userServiceStub)
        val mvc = standaloneSetup(userController).build()

        mvc.perform(get("/api/users/egon")).
          andExpect(status().isOk()).
          andExpect(jsonPath("$.username", is("egon"))).
          andExpect(jsonPath("$.password", is("secret")))
      }

      it("should return 404") {
        val userController = new UserController(userService = userServiceStub)
        val mvc = standaloneSetup(userController).build()

        mvc.perform(get("/api/users/test")).
          andExpect(status().is4xxClientError())
      }
    }
  }
}
