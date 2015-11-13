package com.springscala.boot.modules.account.repository

import com.springscala.boot.ApplicationConfig
import com.springscala.boot.modules.account.domain.{AuthorityRole, Authority, User}
import org.ektorp.{CouchDbConnector, CouchDbInstance}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.context.annotation.{Bean, Primary}
import org.springframework.core.env.Environment
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.annotations.{AfterMethod, BeforeMethod, Test}

class TestConfig {

  @Autowired
  var env: Environment = _

  @Primary
  @Bean
  def springscalaDbName() =
    "test_" + env.getProperty("springscalaDbName.dbName", classOf[String], "springscala")

}

@ContextConfiguration(classes = Array(classOf[ApplicationConfig], classOf[TestConfig]), loader = classOf[SpringApplicationContextLoader])
class UserRepositoryTest extends AbstractTestNGSpringContextTests {

  @Autowired
  var db: CouchDbConnector = _

  @Autowired
  var dbInstance: CouchDbInstance = _

  var userRepository: UserRepository = _

  @BeforeMethod
  def setUp() {
    db.createDatabaseIfNotExists()
    userRepository = new UserRepository(db)
  }

  @AfterMethod
  def tearDown() {
    dbInstance.deleteDatabase(db.getDatabaseName)
  }

  private def users = {
    List(
      User("Anna", "tina", "lei", "test", "a@b.de", "EN", List(Authority(AuthorityRole.ROLE_USER))),
      User("Stephan", "hans", "dampf", "test", "a@b.de", "EN", List(Authority(AuthorityRole.ROLE_USER)))
    )
  }

  @Test
  def testFindByUsername() {
    users.foreach(userRepository.add)
    val expectedUser = userRepository.findByUsername("Anna")
    assert(expectedUser.get.firstname == "tina")
  }

  @Test
  def testFindAllUsers() {
    users.foreach(userRepository.add)
    val expectedUser = userRepository.findAll()
    assert(expectedUser.size == 2)
    assert(expectedUser.equals(users))
  }
}

