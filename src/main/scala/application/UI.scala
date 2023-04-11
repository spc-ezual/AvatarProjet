package application

import scala.swing._
import java.awt.Color
import scala.swing.event._
import Outils.CreationDeRep._
import javax.swing.ImageIcon
import machine.MachineImpl

/** MainFrame realizing the CopyThat application
  */
class UI extends MainFrame {

  preferredSize = new Dimension(750, 750)
  title = "Avatar"

  val chatArea = new BoxPanel(Orientation.Vertical)
  val inputField = new TextField
  val sendButton = new Button { text = "Send" }
  val resetButton = new Button { text = "New Conversation" }
  val avatarIcon = new ImageIcon("Image/Avatar.png")
  val userIcon = new ImageIcon("Image/User.png")

  listenTo(sendButton, inputField.keys, resetButton)

  reactions += {
    case ButtonClicked(`resetButton`) =>
      MachineImpl.reinit()

    case ButtonClicked(`sendButton`) =>
      val message = inputField.text

      if (!message.isEmpty()) {
        // Create user message
        val userMessage = new FlowPanel {
          contents += new Label { icon = userIcon }
          contents += new Label { text = s"You : $message" }
        }
        // Create avatar message
        val avatarMessage = new FlowPanel {
          contents += new Label { icon = avatarIcon }
          contents += new Label {
            text = "Avatar : " + formatReponse(Reponse(message))
          }
        }
        // Add messages to chat area
        chatArea.contents += userMessage
        chatArea.contents += avatarMessage
        inputField.text = ""
        chatArea.revalidate();
        chatArea.repaint();
      }

    case KeyPressed(`inputField`, Key.Enter, _, _) =>
      val message = inputField.text

      if (!message.isEmpty()) {
        // Create user message
        val userMessage = new FlowPanel {
          contents += new Label { icon = userIcon }
          contents += new Label { text = s"You : $message" }
        }
        // Create avatar message
        val avatarMessage = new FlowPanel {
          contents += new Label { icon = avatarIcon }
          contents += new Label {
            text = "Avatar : " + formatReponse(Reponse(message))
          }
        }
        // Add messages to chat area
        chatArea.contents += userMessage
        chatArea.contents += avatarMessage
        inputField.text = ""
        chatArea.revalidate();
        chatArea.repaint();
      }
  }

  val chat = new ScrollPane(chatArea)
  chat.preferredSize = new Dimension(750, 700)

  inputField.preferredSize = new Dimension(400, 40)

  val inputAndButtons = new FlowPanel(inputField, sendButton, resetButton)
  inputAndButtons.preferredSize = new Dimension(750, 50)

  contents = new BoxPanel(Orientation.Vertical) {
    contents += chat
    contents += inputAndButtons
  }

}
