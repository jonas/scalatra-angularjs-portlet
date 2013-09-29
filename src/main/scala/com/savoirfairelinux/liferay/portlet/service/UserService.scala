package com.savoirfairelinux.liferay.portlet.service;

import com.liferay.portal.kernel.dao.orm.QueryUtil
import com.liferay.portal.security.auth.CompanyThreadLocal
import com.liferay.portal.service.UserServiceUtil
import com.savoirfairelinux.liferay.portlet.model.User
import collection.JavaConversions._

/**
 * @author Dmitri Carpov
 */
class UserService {
    def getUsers: List[User] = {
      val companyId = CompanyThreadLocal.getCompanyId()
      val users = UserServiceUtil.getCompanyUsers(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS)

      users.map(User.toUser(_)).toList
    }
}