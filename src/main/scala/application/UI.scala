package application




import scala.swing._
import java.awt.Color
import scala.swing.event._
import Outils.CreationDeRep._

/**
 * MainFrame realizing the CopyThat application
 */
class UI extends MainFrame {
    preferredSize = new Dimension(500, 500)
    title="Avatar"
    val chatArea= new TextArea{
        editable= false
    }
    val inputField = new TextField{
        columns=20
    }
    val sendButton = new Button{
        text = "Send"
    }
    contents= new  BoxPanel(Orientation.Vertical){
        contents += new ScrollPane(chatArea)
        contents += new FlowPanel(inputField, sendButton)
    }
    listenTo(sendButton, inputField.keys)
    reactions += {
        case ButtonClicked(`sendButton`) =>
            val message = inputField.text
            if(!message.isEmpty()){
                chatArea.append(s"\nYou: $message")
                val rep= "\nAvatar: "+Reponse(message)
                chatArea.append(rep)
                inputField.text = ""}

        case KeyPressed(`inputField`, Key.Enter, _, _) =>
            val message = inputField.text
            if(!message.isEmpty()){
                chatArea.append(s"\nYou: $message")
                val rep= "\nAvatar: "+Reponse(message)
                chatArea.append(rep) 
                inputField.text = ""}
    }
}