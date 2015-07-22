package com.springscala.boot.security

import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}

import org.springframework.security.core.{Authentication, AuthenticationException}
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class AjaxAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  @Override
  @throws(classOf[IOException])
  @throws(classOf[ServletException])
  override def onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) = {
    response.sendError(HttpServletResponse.SC_OK)
  }
}
