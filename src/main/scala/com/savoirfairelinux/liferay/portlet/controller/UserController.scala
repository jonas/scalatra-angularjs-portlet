package com.savoirfairelinux.liferay.portlet.controller

import com.liferay.portal.kernel.log.LogFactoryUtil
import com.liferay.portal.security.auth.CompanyThreadLocal
import com.liferay.portal.security.auth.PrincipalThreadLocal
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil
import com.liferay.portal.security.permission.PermissionThreadLocal
import com.liferay.portal.util.PortalUtil
import com.savoirfairelinux.liferay.portlet.model.User
import com.savoirfairelinux.liferay.portlet.service.UserService
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json._

class UserController extends ScalatraServlet with JacksonJsonSupport {
  val log = LogFactoryUtil.getLog(getClass())
  protected implicit val jsonFormats: Formats = DefaultFormats
  var userService: UserService = new UserService

  before() {
    try {
      val user = PortalUtil.getUser(request)
      val permissionChecker = PermissionCheckerFactoryUtil.create(user)

      PermissionThreadLocal.setPermissionChecker(permissionChecker)
      PrincipalThreadLocal.setName(user.getUserId)
      CompanyThreadLocal.setCompanyId(user.getCompanyId)
    } catch {
      case ex: Exception =>
        log.debug("Cannot get users list. Caused by: " + ex.getMessage(), ex)
    }
  }

  before("/services/users") {
    contentType = formats("json")
  }

  get("/services/users") {
    userService.getUsers
  } 
}