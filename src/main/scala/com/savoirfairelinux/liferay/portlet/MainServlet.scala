package com.savoirfairelinux.liferay.portlet

import com.savoirfairelinux.liferay.portlet.controller.UserController
import org.scalatra._

class MainServlet extends ScalatraServlet
                     with AuthenticationSupport
                     with UserController {

  before() {
    scentry.authenticate('LiferayAuth)
  }

}