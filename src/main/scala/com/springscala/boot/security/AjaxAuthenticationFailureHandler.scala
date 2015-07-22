package com.springscala.boot.security

import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component

@Component
class AjaxAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  @Override
  @throws(classOf[IOException])
  @throws(classOf[ServletException])
  override def onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException): Unit = {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed")
  }
}
