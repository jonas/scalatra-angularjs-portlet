package com.savoirfairelinux.liferay.portlet.controller

import com.escalatesoft.subcut.inject.Injectable
import com.savoirfairelinux.liferay.portlet.service.UserService
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json._

trait UserController extends JacksonJsonSupport with Injectable {

  protected implicit val jsonFormats: Formats = DefaultFormats
  var userService = inject[UserService]

  before("/services/users") {
    contentType = formats("json")
  }

  get("/services/users") {
    userService.getUsers
  } 
}