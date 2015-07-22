package com.springscala.boot.modules.account.service

import com.springscala.boot.modules.account.domain.User
import com.springscala.boot.modules.account.repository.UserRepository
import org.ektorp.CouchDbConnector
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.stereotype.Service

@Service
class UserService @Autowired()(dbConnector: CouchDbConnector) {
  private val userRepository = new UserRepository(dbConnector)

  def getAllUser = userRepository.findAll()

  def getByUsername(username: String) = {
    @scala.annotation.tailrec
    def findUsername(xs: List[User]): Option[User] =
      xs match {
        case Nil => None
        case head :: _ if head.username.equals(username) => Some(head)
        case _ :: tail => findUsername(tail)
      }

    findUsername(getAllUser)
  }
}
