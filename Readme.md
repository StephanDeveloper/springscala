# Description

Just another template to start spring 4 with scala. It should help you to get
an impression how simple it is to write your next microservice in scala.

# Setup
 
* Install java @ least 1.7
* Install [scala](http://www.scala-lang.org/index.html) 
* Install [gradle](https://gradle.org/)

Open Terminal switch to project and execute `gradle build`. It should resolve
all dependencies and build a jar file for you.  

Open project in your IDE and execute `Application.scala` to run spring context.
To use Mail and Couchdb you should create your own `application-my-stage.yml`.

# Api

* GET /api/users - return list of users
* GET /api/users/{username} - return a user specified by username

* POST /api/contact - send contact message
```JavaScript
{
  "subject": "Contact Modulte Test",
  "name": "Test User",
  "email": "test@domian.de",
  "content": "Hi, how are you?"
}
```
