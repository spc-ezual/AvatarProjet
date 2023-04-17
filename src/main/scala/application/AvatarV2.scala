package application
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, ScrollPane, TextArea, TextField}
import scalafx.scene.layout.{HBox, Priority, VBox}
import Outils.CreationDeRep._
import Speech.Discours

object AvatarV2 extends JFXApp {
  // Création de la fenêtre principale
  var voc = false
  stage = new PrimaryStage {
    title = "Avatar"
    width = 900
    height = 600
  }
  // Champ pour l'entré
  val messageInput = new TextField{
    promptText="Entrz votre message"
  }
  // boutton pour l'envoi
  val sendButton = new Button("Envoyer")
  val resetButton = new Button("New Conversation")
  val vocalButton = new Button("Synthèse vocale")
  //conteneur pour la saisie
  val inputBox = new HBox{
    children = Seq(messageInput, sendButton,resetButton,vocalButton)
    spacing = 5
    padding = Insets(10)
    alignment = Pos.CenterRight
  }
  // boîte pour afficher les messages
  val messageBox = new VBox {
    spacing = 5
    padding = Insets(10)
  }

  //zone de défilement pour la boîte de messages
  val messageScrollPane = new ScrollPane {
    content = messageBox
    vvalue <== messageBox.heightProperty
    hbarPolicy = ScrollPane.ScrollBarPolicy.Never
    fitToWidth = true
  }
  val root = new VBox{
    children = Seq(
      messageScrollPane,
      inputBox
    )
    spacing = 10
    padding = Insets(10)
    vgrow = Priority.Always
  }
  // Ajout des listeners pour la saisie de messages et l'envoi de messages
  messageInput.onAction = () => sendMessage()
  sendButton.onAction = () => sendMessage()
  resetButton.onAction = () => ()
  vocalButton.onAction = () => voc= !voc

  // Fonction pour envoyer un message
  def sendMessage(): Unit = {
    val message = messageInput.text.value
    if (message.nonEmpty) {
      val messageLabel = new Label(message) {
        style = "-fx-background-color: #ADD8E6; -fx-padding: 5px;"
        maxWidth = 250
        wrapText = true
        alignment=Pos.TopRight
      }
      messageBox.children.add(messageLabel)
      for(rep <- Reponse(message)){
        if(voc)Discours.generateDiscours(rep,getLangue())
        val messageLabel = new Label(rep) {
        style = "-fx-background-color: #ADD8E6; -fx-padding: 5px;"
        maxWidth = 250
        wrapText = true
        alignment=Pos.TopLeft
      }
      val messageContainer = new HBox {
    children = Seq(messageLabel)
    alignment = Pos.TopRight
    spacing = 5
  }
      messageBox.children.add(messageContainer)
      }

      // Réinitialisation du champ de texte pour la saisie de messages
      messageInput.text = ""
    }
  }

  // Affichage de la fenêtre principale
  stage.scene = new Scene(root)
  stage.show()
}
