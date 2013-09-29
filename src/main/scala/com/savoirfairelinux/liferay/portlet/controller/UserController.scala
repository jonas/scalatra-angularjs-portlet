package com.savoirfairelinux.liferay.portlet.controller

import com.liferay.portal.kernel.log.LogFactoryUtil
import com.liferay.portal.security.auth.CompanyThreadLocal
import com.liferay.portal.security.auth.PrincipalThreadLocal
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil
import com.liferay.portal.security.permission.PermissionThreadLocal
import com.liferay.portal.util.PortalUtil
import com.savoirfairelinux.liferay.portlet.model.User
import com.savoirfairelinux.liferay.portlet.service.UserService
import play.api.libs.json.Json
import org.scalatra._

class UserController extends ScalatraServlet {
  val log = LogFactoryUtil.getLog(getClass())
  implicit val userJsonWrites = Json.writes[User]
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
    contentType = "applcication/json"
  }

  get("/services/users") {
    Json.toJson(userService.getUsers)
  } 
}