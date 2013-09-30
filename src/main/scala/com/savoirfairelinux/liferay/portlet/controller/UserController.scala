package com.savoirfairelinux.liferay.portlet.controller

import com.liferay.portal.kernel.log.LogFactoryUtil
import com.savoirfairelinux.liferay.portlet.service.UserService
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json._

class UserController extends ScalatraServlet with AuthenticationSupport with JacksonJsonSupport {
  val log = LogFactoryUtil.getLog(getClass())
  protected implicit val jsonFormats: Formats = DefaultFormats
  var userService: UserService = new UserService

  before() {
    scentry.authenticate('LiferayAuth)
  }

  before("/services/users") {
    contentType = formats("json")
  }

  get("/services/users") {
    userService.getUsers
  } 
}