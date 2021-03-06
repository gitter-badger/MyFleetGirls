package dat

import models._

/**
 *
 * Date: 14/03/19.
 * @author ponkotuy
 */
case class User(admiral: Admiral, basic: Basic, map: String, setting: UserSettings, logined: Option[Long]) {
  def isMine: Boolean = logined.exists(_ == admiral.id)
}
