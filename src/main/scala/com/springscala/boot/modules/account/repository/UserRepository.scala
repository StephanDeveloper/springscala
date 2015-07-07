package com.springscala.boot.modules.account.repository


import com.springscala.boot.modules.account.domain.User
import org.ektorp.CouchDbConnector
import org.ektorp.support.CouchDbRepositorySupport

class UserRepository(db: CouchDbConnector) extends CouchDbRepositorySupport[User](classOf[User], db) {

  initStandardDesignDocument()

  // @TODO: use getAll , need a View in CouchDb named 'all'
  def findAll() = List(User("test", "test"))

  def findByKey(key: String) = if (db.contains(key)) db.get(classOf[User], key) else null

  def findByUsername(username: String) = db.queryView(createQuery("username"), classOf[User])
}