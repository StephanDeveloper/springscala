package com.springscala.boot.modules.account.repository


import com.springscala.boot.modules.account.domain.{Authority, User}
import org.ektorp.CouchDbConnector
import org.ektorp.support.{CouchDbRepositorySupport, View}
import org.json4s.ShortTypeHints
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization

class UserRepository(db: CouchDbConnector) extends CouchDbRepositorySupport[User](classOf[User], db) {
  implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[Authority])))

  initStandardDesignDocument()

  @View(name = "findAllUser", map =
    """
      function (doc) {
         emit(null, {
             'username': doc.username,
             'firstname': doc.firstname,
             'lastname': doc.lastname,
             'password': doc.password,
             'email': doc.email,
             'languageKey': doc.languageKey,
             'authorities': doc.authorities
         })
      }
    """)
  def findAll(): List[User] = {
    val rows = db.queryView(createQuery("findAllUser")).getRows
    val usersJson = for (i <- 0 to rows.size() - 1) yield rows.get(i).getValue

    usersJson.map(userJson => {
      val json = parse(userJson)

      val username = (json \ "username").extract[String]
      val firstname = (json \ "firstname").extract[String]
      val lastname = (json \ "lastname").extract[String]
      val password = (json \ "password").extract[String]
      val email = (json \ "email").extract[String]
      val languageKey = (json \ "languageKey").extract[String]
      val authorities = (json \ "authorities").extract[List[Authority]]

      User(username, firstname, lastname, password, email, languageKey, authorities)
    }).toList
  }

  def findByUsername(username: String): Option[User] = {
    val users = this.findAll()

    users.find {
      case User(`username`, _, _, _, _, _, _) if username == username => true
      case _ => false
    }
  }
}