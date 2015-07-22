package com.springscala.boot.modules.account.controller

import com.springscala.boot.modules.account.domain.User
import com.springscala.boot.modules.account.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation._

@RequestMapping(Array("/api"))
@RestController
class UserController @Autowired()(userService: UserService) extends Serializable {

  @RequestMapping(value = Array("/users"), method = Array(RequestMethod.GET), produces = Array(APPLICATION_JSON_VALUE))
  @ResponseBody
  def getUsers: ResponseEntity[List[User]] = {
    val userList = userService.getAllUser
    new ResponseEntity[List[User]](userList, HttpStatus.OK)
  }

  @RequestMapping(value = Array("/users/{username}"), method = Array(RequestMethod.GET), produces = Array(APPLICATION_JSON_VALUE))
  def getUser(@PathVariable username: String): ResponseEntity[User] = {
    val userOption = userService.getByUsername(username)

    val response = userOption match {
      case Some(user) => new ResponseEntity[User](user, HttpStatus.OK)
      case _ => new ResponseEntity[User](HttpStatus.NOT_FOUND)
    }

    response
  }
}
