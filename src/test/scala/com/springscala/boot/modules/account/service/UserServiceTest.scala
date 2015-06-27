package com.springscala.boot.modules.account.service

import com.springscala.boot.modules.account.domain.User
import org.scalatest.{FunSpec, Inside, Matchers}

class UserServiceTest extends FunSpec with Inside with Matchers {

  describe("UserService function create") {
    it("should return user") {
      val userService = new UserService
      val expectedUser = userService.create("username", "password")

      inside(expectedUser) {
        case User(username: String, password: String) =>
          username should be("username")
          password should be("password")
      }
    }
  }
}

