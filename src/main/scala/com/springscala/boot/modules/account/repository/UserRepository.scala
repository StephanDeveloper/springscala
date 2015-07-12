package com.springscala.boot.modules.account.repository


import com.springscala.boot.modules.account.domain.{Authority, User}
import org.ektorp.CouchDbConnector
import org.ektorp.support.{CouchDbRepositorySupport, View}
import org.json4s.{ShortTypeHints, DefaultFormats}
import org.json4s.JsonAST.{JString, JField, JObject}
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization

class UserRepository(db: CouchDbConnector) extends CouchDbRepositorySupport[User](classOf[User], db) {
  implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[Authority])))

  initStandardDesignDocument()

  @View(name = "findAllUser", map = "function (doc) {emit(null, {'username': doc.username, 'lastname': doc.lastname,'password': doc.password,'email': doc.email,'authorities': doc.authorities})}")
  def findAll(): List[User] = {
    val rows = db.queryView(createQuery("findAllUser")).getRows
    val usersJson = for (i <- 1 until rows.size()) yield rows.get(i).getValue

    usersJson.map(userJson => {
      val json = parse(userJson)

      val username = (json \ "username").extract[String]
      val lastname = (json \ "lastname").extract[String]
      val password = (json \ "password").extract[String]
      val email = (json \ "email").extract[String]
      val authorities = (json \ "authorities").extract[List[Authority]]

      User(username, lastname, password, email, authorities)
    }).toList
  }
}