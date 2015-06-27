package com.springscala.boot.modules.account.service


import com.springscala.boot.modules.account.domain.User
import org.springframework.stereotype.Service

@Service
class UserService {

  def create(username: String, password: String) = User(username, password)
}
