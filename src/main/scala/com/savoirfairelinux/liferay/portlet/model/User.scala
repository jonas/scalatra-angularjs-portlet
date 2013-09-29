package com.savoirfairelinux.liferay.portlet.model

case class User(
  id: Long,
  firstName: String,
  lastName: String,
  emailAddress: String,
  jobTitle: String,
  comments: String,
  pictureUrl: String
)

object User {
  import com.liferay.portal.model.{User => LiferayUser}

  def toUser(user: LiferayUser): User = {
    val pictureUrlBuilder = new StringBuilder()
    val sex = if (user.isFemale) "female" else "male"
    val pictureUrl = s"/image/user_${sex}_portrait?img_id${user.getPortraitId}"

    User(user.getUserId(),
         user.getFirstName(),
         user.getLastName(),
         user.getEmailAddress(),
         user.getJobTitle(),
         user.getComments(),
         pictureUrl)
  }
}