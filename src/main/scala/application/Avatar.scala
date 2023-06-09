package application

import scala.swing.SimpleSwingApplication
import scala.swing.MainFrame

object Avatar extends SimpleSwingApplication {
    val ui = new UI

    def top: MainFrame = ui

    def reinit(): Unit = {
        ui.chatArea.contents.clear()
        ui.inputField.text = ""
        ui.chatArea.revalidate()
        ui.chatArea.repaint()
    }
}