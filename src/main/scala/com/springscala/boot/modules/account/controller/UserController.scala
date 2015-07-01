package com.springscala.boot.modules.account.controller

import com.springscala.boot.modules.account.domain.User
import com.springscala.boot.modules.account.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation._

@RequestMapping(Array("/api"))
@RestController
class UserController @Autowired()(userService: UserService) {

  @RequestMapping(value = Array("/users"), method = Array(RequestMethod.GET), produces = Array(APPLICATION_JSON_VALUE))
  @ResponseBody
  def getUsers: ResponseEntity[List[User]] = {
    val userList = List[User](userService.create("stephan", "secret"), userService.create("anna", "secret"))
    new ResponseEntity[List[User]](userList, HttpStatus.OK)
  }

  @RequestMapping(value = Array("/users/{username}"), method = Array(RequestMethod.GET), produces = Array(APPLICATION_JSON_VALUE))
  def getUser(@PathVariable username: String): ResponseEntity[User] = {
    val userList = List(User("egon", "secret"), User("bert", "secret"), User("hans", "secret"))

    def response(xs: List[User]): ResponseEntity[User] =
      xs match {
        case Nil => new ResponseEntity[User](HttpStatus.NOT_FOUND)
        case head :: _ if head.username.equals(username) => new ResponseEntity[User](head, HttpStatus.OK)
        case _ :: tail => response(tail)
      }

    response(userList)
  }
}
