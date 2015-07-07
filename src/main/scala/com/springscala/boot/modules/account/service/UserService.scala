package com.springscala.boot.modules.account.service

import com.springscala.boot.modules.account.repository.UserRepository
import org.ektorp.CouchDbConnector
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired()(dbConnector: CouchDbConnector) {
  private val userRepository = new UserRepository(dbConnector)

  def getAllUser = userRepository.findAll()
}
