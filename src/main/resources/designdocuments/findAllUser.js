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