package com.springscala.boot.security

import com.springscala.boot.modules.account.repository.UserRepository
import com.springscala.boot.modules.account.service.UserService
import com.typesafe.scalalogging.slf4j.LazyLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.{UserDetailsService, UsernameNotFoundException}
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceAuth extends UserDetailsService with LazyLogging {

  @Autowired
  private val userService: UserService = null

  @Override
  override def loadUserByUsername(username: String) = {
    logger.info(s"Authentication $username")

    val user = userService.getByUsername(username.toLowerCase)
    //println(user)
    //    userRepository.findByUsername(username.toLowerCase) match {
    //      case Some(user) => new User("a", "b", List("User"))
    //      case None =>
    //    }

    throw new UsernameNotFoundException("as")
  }
}
