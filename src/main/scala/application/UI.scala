package application




import scala.swing._
import java.awt.Color
import scala.swing.event._
import Outils.CreationDeRep._
import javax.swing.ImageIcon
import machine.MachineImpl
import Speech.Discours

/**
 * MainFrame realizing the CopyThat application
 */
class UI extends MainFrame {

    preferredSize = new Dimension(1000, 1000)
    title="Avatar"
    var vocal = false
    val chatArea= new BoxPanel(Orientation.Vertical)
    val inputField = new TextField{columns=40}
    val sendButton = new Button{text = "Send"}
    val resetButton = new Button{text = "New Conversation"}
    val vocalButton = new Button{text="Synthèse vocale"}
    val avatarIcon= new ImageIcon("Image/Avatar.png")
    val userIcon = new ImageIcon("Image/User.png")
    
    listenTo(sendButton, inputField.keys,resetButton,vocalButton)
    
    reactions += {
        case ButtonClicked(`resetButton`) => 
            MachineImpl.reinit()
        case ButtonClicked(`vocalButton`)=> vocal = !vocal 
        case ButtonClicked(`sendButton`) =>
            
            val message = inputField.text
            
            if(!message.isEmpty()){
                // Create user message
                val userMessage = new FlowPanel {
                    contents += new Label {icon = userIcon}
                    contents += new Label {text = s"You: $message"}
                }
                // Create avatar message
                val avatarMessage = new FlowPanel {
                    contents += new Label {icon = avatarIcon}
                    contents += new Label {text = "Avatar: " + formatReponse(Reponse(message))}
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
            
            if(!message.isEmpty()){
                // Create user message
                val userMessage = new FlowPanel {
                    contents += new Label {icon = userIcon}
                    contents += new Label {text = s"You: $message"}
                }
                // Create avatar message
                val rep =formatReponse(Reponse(message))
                val avatarMessage = new FlowPanel {
                    contents += new Label {icon = avatarIcon}
                    contents += new Label {text = "Avatar: " + rep}
                }
                if(vocal)Discours.generateDiscours(rep,getLangue())
                // Add messages to chat area
                chatArea.contents += userMessage
                chatArea.contents += avatarMessage
                inputField.text = ""
                chatArea.revalidate();
                chatArea.repaint();
            }
        }
        contents= new  BoxPanel(Orientation.Vertical){
        contents += new ScrollPane(chatArea)
        contents += new FlowPanel(vocalButton,inputField, sendButton,resetButton)
    }
        
}