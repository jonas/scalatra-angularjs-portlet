package com.savoirfairelinux.liferay.portlet

import com.escalatesoft.subcut.inject.Injectable
import com.liferay.portal.security.auth.CompanyThreadLocal
import com.liferay.portal.security.auth.PrincipalThreadLocal
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil
import com.liferay.portal.security.permission.PermissionThreadLocal
import com.liferay.portal.util.PortalUtil
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.scalatra.auth.{ScentrySupport, ScentryConfig, ScentryStrategy}
import org.scalatra.ScalatraBase
import scala.util.Try

case class LiferayAuthUser(id: String)

trait LiferayAuthStrategy extends ScentryStrategy[LiferayAuthUser]

class PermissionCheckerAuthStrategy(protected val app: ScalatraBase) extends LiferayAuthStrategy {

  def authenticate()(implicit request: HttpServletRequest, response: HttpServletResponse) = {
    def findUserAndSetupPermissions = {
      val user = PortalUtil.getUser(request)
      val permissionChecker = PermissionCheckerFactoryUtil.create(user)

      PermissionThreadLocal.setPermissionChecker(permissionChecker)
      PrincipalThreadLocal.setName(user.getUserId)
      CompanyThreadLocal.setCompanyId(user.getCompanyId)

      LiferayAuthUser(user.getEmailAddress)
    }

    Try(findUserAndSetupPermissions).toOption
  }

}

trait AuthenticationSupport extends ScentrySupport[LiferayAuthUser] with Injectable { self: ScalatraBase =>

  protected def fromSession = { case id: String => LiferayAuthUser(id)  }
  protected def toSession   = { case usr: LiferayAuthUser => usr.id }

  override protected def registerAuthStrategies = {
    scentry.register("LiferayAuth", app =>
        injectOptional [LiferayAuthStrategy] getOrElse { new PermissionCheckerAuthStrategy(app) })
  }
                     
  override protected val scentryConfig = new ScentryConfig {
    override val login = "/c/portal/login"
    override val returnTo = "/"
    override val returnToKey = "redirect"
    override val failureUrl = "/c/portal/logout"
  }.asInstanceOf[ScentryConfiguration]
}