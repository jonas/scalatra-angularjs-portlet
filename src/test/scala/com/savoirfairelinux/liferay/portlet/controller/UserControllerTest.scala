package com.savoirfairelinux.liferay.portlet.controller

import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.scalatest.FunSuite
import org.scalatra.ScalatraServlet
import org.scalatra.test.scalatest.ScalatraSuite
import com.savoirfairelinux.liferay.portlet.TestConfiguration
import com.savoirfairelinux.liferay.portlet.model.User

class UserControllerServlet extends ScalatraServlet with UserController {
  def bindingModule = TestConfiguration
}

class UserControllerTest extends ScalatraSuite with FunSuite {

  protected implicit val jsonFormats: Formats = DefaultFormats
  addServlet(classOf[UserControllerServlet], "/*")

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