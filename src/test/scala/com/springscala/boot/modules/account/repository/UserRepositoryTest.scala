package com.springscala.boot.modules.account.repository

import com.springscala.boot.modules.account.domain.{Authority, AuthorityRole, User}
import org.ektorp.CouchDbConnector
import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FunSpec, Ignore, Inside, Matchers}

@Ignore // uncommented , ViewResult has to be mocked
class UserRepositoryTest extends FunSpec with Inside with Matchers with MockitoSugar {

  val couchdbConnector = mock[CouchDbConnector]

  describe("UserRepository") {
    val userRepository = new UserRepository(couchdbConnector)
    // when(couchdbConnector.queryView(_)).thenReturn(List.empty[ViewResult])


    describe("function findByUsername") {
      it("should return user") {
        when(userRepository.findAll()).thenReturn(List(
          User("Anna", "tina", "lei", "test", "a@b.de", "EN", List(Authority(AuthorityRole.ROLE_USER))),
          User("Stephan", "hans", "dampf", "test", "a@b.de", "EN", List(Authority(AuthorityRole.ROLE_USER)))
        ))

        val userOption = userRepository.findByUsername("Anna")
        userOption match {
          case Some(user) =>
            inside(user) {
              case User(username: String, _, _, _, _, _, _) =>
                username should be("Anna")
            }
          case None =>
        }
      }
    }
  }



  //  val couchdbConnector = mock[CouchDbConnector]
  //
  //  describe("UserRepository function create") {
  //    it("should return user") {
  //      val userService = new UserRepository(couchdbConnector)
  ////      val expectedUser = userService.create("username", "password")
  //      //
  //      //      inside(expectedUser) {
  //      //        case User(username: String, password: String) =>
  //      //          username should be("username")
  //      //          password should be("password")
  //      //      }
  //    }
  //  }
}

