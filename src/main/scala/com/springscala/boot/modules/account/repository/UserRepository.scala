package com.springscala.boot.modules.account.repository


import com.springscala.boot.modules.account.domain.User
import org.ektorp.CouchDbConnector
import org.ektorp.support.{CouchDbRepositorySupport, View}
import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods._

class UserRepository(db: CouchDbConnector) extends CouchDbRepositorySupport[User](classOf[User], db) {

  initStandardDesignDocument()

  @View(name = "findAllUser", map = "function (doc) {emit(null, {'username': doc.username, 'password': doc.password})}")
  def findAll(): List[User] = {
    implicit val formats = DefaultFormats

    val rows = db.queryView(createQuery("findAllUser")).getRows
    val users = for (i <- 1 until rows.size()) yield rows.get(i).getValue
    users.map(userJson => parse(userJson).extract[User]).toList
  }

  def findByKey(key: String) = if (db.contains(key)) db.get(classOf[User], key) else null

  def findByUsername(username: String) = db.queryView(createQuery("username"), classOf[User])
}