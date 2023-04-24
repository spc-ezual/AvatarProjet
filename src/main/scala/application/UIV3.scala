package application

import scala.swing._
import java.awt.Color
import scala.swing.event._
import scala.swing.FileChooser._
import Outils.CreationDeRep._
import javax.swing.ImageIcon
import machine.MachineImpl
import Speech.Discours
import java.io.FileNotFoundException

class UIV3 extends MainFrame {

    title = "Avatar"
    
    var memoire = List(): List[String]

    preferredSize = new Dimension(1000, 1000)
    
    var vocal = false
    val chatArea= new BoxPanel(Orientation.Vertical)
    val inputField = new TextField{columns=40}
    val sendButton = new Button{text = "Send"}
    val optionButton = new Button{text = "Option"}
    val closeOptionButton = new Button{text = "Fermer"}
    val resetButton = new Button{text = "New Conversation"}
    val vocalButton = new Button{text = "Synthèse vocale"}
    val saveButton = new Button{text ="Sauvegarder"}
    val loadButton = new Button {text ="Charger"}
    val avatarIcon= new ImageIcon("Image/Avatar.png")
    val userIcon = new ImageIcon("Image/User.png")
    val comboBox = new ComboBox (Seq("Francais","Anglais","Espagnol","Allemand","Italien"))
    val outils = new Dialog(){contents =  new BoxPanel(Orientation.Vertical) {
        contents += resetButton
        contents += vocalButton
        contents += comboBox
        contents+= saveButton
        contents += loadButton
        contents += closeOptionButton
        
    }}
    val chooser = new FileChooser
    listenTo(sendButton, inputField.keys,resetButton,vocalButton,optionButton,closeOptionButton,comboBox.selection,saveButton,loadButton)
    

    
    
    
    outils.modal = true
    outils.visible = false


    reactions += {
        case ButtonClicked(`optionButton`) => {
            comboBox.selection.index_=(getLangue)
            outils.visible = true
            }
        case ButtonClicked(`saveButton`) => creaSave
        
        case ButtonClicked(`loadButton`) => loadSave

        case ButtonClicked(`closeOptionButton`) => outils.visible=false
        
        case ButtonClicked(`resetButton`) => MachineImpl.reinit()
        
        case ButtonClicked(`vocalButton`)=> vocal = !vocal 
        
        case SelectionChanged(`comboBox`) => newLangue(comboBox.selection.index)
        
        case ButtonClicked(`sendButton`) => appelReponse


        case KeyPressed(`inputField`, Key.Enter, _, _) => appelReponse
            
        }
        contents= new  BoxPanel(Orientation.Vertical){
        contents += new ScrollPane(chatArea)
        contents += new FlowPanel(inputField, sendButton, optionButton)

    }

    def appelReponse{
                    
            val message = inputField.text
            
            if(!message.isEmpty()){
                // Create user message
                addMsgYou(message)
                // Create avatar message
                addMsgAva(formatReponse(Reponse(message)))
                
                // Actualisation
                inputField.text = ""
                chatArea.revalidate();
                chatArea.repaint();
            }
    }

    def addMsgYou(msg : String){
        val userMessage = new FlowPanel {
                    contents += new Label {icon = userIcon}
                    contents += new Label {text = s"You: $msg"}
                }
        chatArea.contents += userMessage
        memoire = memoire :+ msg 
    }

    def addMsgAva(msg:String){
        val avatarMessage = new FlowPanel {
                    contents += new Label {icon = avatarIcon}
                    contents += new Label {text = "Avatar: " + msg}
                }
        chatArea.contents += avatarMessage
        memoire = memoire :+ msg 
    }

    def creaSave{
        val result = chooser.showSaveDialog(outils)
        if (result == Result.Approve) {
            val file = chooser.selectedFile
            val writer = new java.io.BufferedWriter(new java.io.FileWriter(file))
            writer.write("Fichier de sauvegarde de l'Avatar\n")
            val (lang,act,mem):(Int,Int,List[(String,String)])=getAll
            writer.write(s"$lang\n$act\n")
            mem.foreach(m=> writer.write(s"${m._1}|||${m._2}\n"))
            writer.newLine()
            memoire.foreach(m => writer.write(s"$m\n"))
            writer.close()

            Dialog.showMessage(outils, "Conversation sauvegardée")
            outils.visible=false
        }
    }

    def loadSave{
        val tempMemoir : (List[String],(Int,Int,List[(String,String)]))= (memoire,getAll)
        try {
            val result = chooser.showOpenDialog(outils)
                    if (result == Result.Approve) {
                        MachineImpl.reinit()
                        val file = chooser.selectedFile
                        val reader = new java.io.BufferedReader(new java.io.FileReader(file))
                        val ligne1 = reader.readLine
                        if(!ligne1.equalsIgnoreCase("Fichier de sauvegarde de l'Avatar")) throw new Exception("le fichier n'es pas un fichier de savegarde")
                        var lang = reader.readLine().toInt
                        val act = reader.readLine().toInt
                        var teteLecture = reader.readLine()
                        var mem =List[(String,String)]()
                        while (teteLecture != null && teteLecture.nonEmpty){
                            val value = teteLecture.split("[|]+",2)
                            mem = mem :+ (value(0),value(1))
                            teteLecture = reader.readLine()
                        }
                        teteLecture=reader.readLine()
                        while (teteLecture != null && teteLecture.nonEmpty){
                            memoire = memoire :+ teteLecture
                            teteLecture = reader.readLine()
                        }
                        setAll(lang,act,mem)
                        reader.close()
                        reconstrutionMemoire
                    Dialog.showMessage(outils, "Conversation chargée")
                    outils.visible=false
                    }}
        catch{
            
            
            case erreur : NullPointerException => 
                Dialog.showMessage(outils, "Erreur lors du chargement des données : une ligne est vide alors que elle ne le devrais pas")
                memoire = tempMemoir._1
                setAll(tempMemoir._2._1,tempMemoir._2._2,tempMemoir._2._3)
            case erreur : FileNotFoundException => 
                Dialog.showMessage(outils, s"Erreur lors du chargement des données : le fichier n'as pas été trouver")
                memoire = tempMemoir._1
                setAll(tempMemoir._2._1,tempMemoir._2._2,tempMemoir._2._3)
            case erreur : IndexOutOfBoundsException => 
                Dialog.showMessage(outils, s"Erreur lors du chargement des données : les données de la memoires sont mal formé ")
                memoire = tempMemoir._1
                setAll(tempMemoir._2._1,tempMemoir._2._2,tempMemoir._2._3)
            case erreur : NumberFormatException  => 
                Dialog.showMessage(outils, s"Erreur lors du chargement des données : les donner de langue et d'action sont mal former")
                memoire = tempMemoir._1
                setAll(tempMemoir._2._1,tempMemoir._2._2,tempMemoir._2._3)
            case erreur : Exception => 
                Dialog.showMessage(outils, s"Erreur lors du chargement des données : ${erreur.getMessage}")
                memoire = tempMemoir._1
                setAll(tempMemoir._2._1,tempMemoir._2._2,tempMemoir._2._3)
            
            
        }
    }

    def reconstrutionMemoire{
        for( i <- 0 until memoire.length){
            if(i%2==0){
                addMsgYou(memoire(i))
            }
            else{
                addMsgAva(memoire(i))
            }
            
        }
        chatArea.revalidate();
        chatArea.repaint();
    }

}