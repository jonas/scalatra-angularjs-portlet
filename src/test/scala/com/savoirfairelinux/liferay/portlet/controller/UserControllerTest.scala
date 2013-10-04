package com.savoirfairelinux.liferay.portlet.controller

import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.scalatest.FunSuite
import org.scalatra.ScalatraServlet
import org.scalatra.test.scalatest.ScalatraSuite
import com.escalatesoft.subcut.inject.NewBindingModule
import com.savoirfairelinux.liferay.portlet.model.User
import com.savoirfairelinux.liferay.portlet.service.UserService

class UserControllerTestServlet extends ScalatraServlet with UserController {
  def bindingModule = new NewBindingModule(module => {
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
  })
}

class UserControllerTest extends ScalatraSuite with FunSuite {

  protected implicit val jsonFormats: Formats = DefaultFormats
  addServlet(classOf[UserControllerTestServlet], "/*")

  test("/service/users returns two users") {
    get("/services/users") {
      status should equal (200)
      body should include ("Free Software Consultant")

      val json = parse(body)

      (json(0) \ "emailAddress") must equal (JString("dmitri.carpov@example.com"))

      val users = json.extract[List[User]]

      users.length should equal (2)
      users(1).lastName should equal ("Fonseca")
    }
  }
}