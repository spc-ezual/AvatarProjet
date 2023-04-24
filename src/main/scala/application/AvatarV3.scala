package application

import scala.swing._
import scala.swing.event._

object  AvatarV3 extends SimpleSwingApplication {
    val ui = new UIV3

    def top: MainFrame = ui

    def reinit(): Unit = {
        ui.outils.visible = false
        ui.memoire = Nil
        ui.chatArea.contents.clear()
        ui.inputField.text = ""
        ui.chatArea.revalidate()
        ui.chatArea.repaint()
    }
}