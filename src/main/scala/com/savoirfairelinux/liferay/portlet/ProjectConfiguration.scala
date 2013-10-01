package com.savoirfairelinux.liferay.portlet

import com.escalatesoft.subcut.inject.NewBindingModule
import com.savoirfairelinux.liferay.portlet.service.UserService
import com.savoirfairelinux.liferay.portlet.service.LiferayUserService

object ProjectConfiguration extends NewBindingModule(module => {
    import module._

    bind [UserService] toSingle new LiferayUserService
  }
)