package com.savoirfairelinux.liferay.portlet

import com.escalatesoft.subcut.inject.NewBindingModule
import com.savoirfairelinux.liferay.portlet.model.User
import com.savoirfairelinux.liferay.portlet.service.UserService

object TestConfiguration extends NewBindingModule(module => {
    import module._

    bind [UserService] toSingle new UserService {
      def getUsers: List[User] = {
        User(0, "Dmitri", "Carpov", "dmitri.carpov@example.com",
             "Free Software Consultant", "", "http://example.com/img/dcarpov.png") ::
        User(1, "Jonas", "Fonseca", "jonas.fonseca@example.com",
             "Free Software Consultant", "", "http://example.com/img/jfonseca.png") ::
        Nil
      }
    }
  }
)