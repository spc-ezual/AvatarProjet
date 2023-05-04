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

    preferredSize = new Dimension(700, 500)
    var customBackground = Color.WHITE
    var customText = Color.BLACK
    var vocal = false
    val chatArea= new BoxPanel(Orientation.Vertical)
    val scrollPane = new ScrollPane(chatArea)
    val inputField = new TextField{columns=40}
    val sendButton = new Button{text = "Envoyer"}
    val optionButton = new Button{text = "Option"}
    val closeOptionButton = new Button{text = "Fermer"}
    val resetButton = new Button{text = "nouvelle conversation"}
    val vocalButton = new Button{text = "Synthèse vocale"}
    val saveButton = new Button{text ="Sauvegarder"}
    val loadButton = new Button {text ="Charger"}
    val applyButton = new Button{text ="Appliquer"}
    val avatarIcon= new ImageIcon("Image/Avatar.png")
    val userIcon = new ImageIcon("Image/User.png")
    val comboBoxLang = new ComboBox (Seq("Francais","Anglais","Espagnol","Allemand","Italien"))
    val comboBoxColor = new ComboBox (Seq("Clair","Sombre","Personalisé"))
    val backgroundChooser = new ColorChooser
    val textChooser = new ColorChooser
    val chooser = new FileChooser
    val testColor = new Label("TEXTE EXEMPLE TEXTE EXEMPLE TEXTE EXEMPLE"){
        opaque=true
        preferredSize = new Dimension(200, 200)
    }
    val outils = new Dialog(){
        title = "Outils"
        contents =  new BoxPanel(Orientation.Vertical) {
        contents += resetButton
        contents += vocalButton
        contents += comboBoxLang
        contents += comboBoxColor
        contents+= saveButton
        contents += loadButton
        contents += closeOptionButton
        
    }}
    val colorChoose = new Dialog(){
        title = "Page de selection des couleurs"

        contents =  new BoxPanel(Orientation.Vertical){
            contents+= new BoxPanel(Orientation.Horizontal){
                contents+= new BoxPanel(Orientation.Vertical){
                    contents+= new Label("Couleur du texte")
                    contents+= textChooser
                }
                contents+= new BoxPanel(Orientation.Vertical){
                    contents+= new Label("Couleur du font")
                    contents+= backgroundChooser
                }
            }
            contents+= testColor
            contents += new Separator
            contents+= applyButton
        }
    }

    
    listenTo(sendButton, inputField.keys,resetButton,vocalButton,optionButton,closeOptionButton,comboBoxLang.selection,saveButton,loadButton,comboBoxColor.selection,applyButton,backgroundChooser,textChooser)
    

    
    
    
    outils.modal = true
    outils.visible = false
    colorChoose.modal = true


    reactions += {
        case ButtonClicked(`optionButton`) => {
            comboBoxLang.selection.index_=(getLangue)
            outils.visible = true
            }
        case ButtonClicked(`saveButton`) => creaSave
        
        case ButtonClicked(`loadButton`) => loadSave

        case ButtonClicked(`closeOptionButton`) => outils.visible=false
        
        case ButtonClicked(`resetButton`) => MachineImpl.reinit()
        
        case ButtonClicked(`vocalButton`)=> vocal = !vocal 
        
        case SelectionChanged(`comboBoxLang`) => newLangue(comboBoxLang.selection.index)

        case ButtonClicked(`applyButton`) => {
            customBackground=backgroundChooser.color
            customText=textChooser.color
            colorChoose.visible=false
            refresh
        }
        
        case ColorChanged(`backgroundChooser`,color) => testColor.background=color

        case ColorChanged(`textChooser`,color) => testColor.foreground=color

        case SelectionChanged(`comboBoxColor`) =>
            {
            if(comboBoxColor.selection.index==0){
                customBackground = Color.WHITE
                customText = Color.BLACK
                refresh
            }
            else if(comboBoxColor.selection.index==1){
                customBackground = Color.BLACK
                customText = Color.WHITE
                refresh
            }
            else{
                colorChoose.visible=true
            }
        }
            
        
        case ButtonClicked(`sendButton`) => appelReponse

        
        case KeyPressed(`inputField`, Key.Enter, _, _) => appelReponse
    }

        contents = new BoxPanel(Orientation.Vertical) {
            contents += scrollPane
            val flowPanel = new FlowPanel(inputField, sendButton, optionButton)
            flowPanel.maximumSize_=(new Dimension(Short.MaxValue, flowPanel.preferredSize.height))
            contents += flowPanel
}

    

    def appelReponse{
                    
            val message = inputField.text
            
            if(!message.isEmpty()){
                // Create user message
                addMsgYou(message)
                memoire = memoire :+ message
                // Create avatar message
                val reponse = Reponse(message)
                addMsgAva(reponse,false)
                if(vocal)Discours.generateDiscours(reponse.mkString(" "),getLangue())
                // Actualisation
                inputField.text = ""
                refresh
            }
    }

    def addMsgYou(msg : String){
        val userMessage = new FlowPanel {
                    contents += new Label {icon = userIcon}
                    contents += new Label {text = s"You: $msg"}
                }
        chatArea.contents += userMessage
    }

    def addMsgAva(msg:List[String],deMem : Boolean){
        val rep = 
            if(msg.length>1)"<html> Avatar: "+msg.mkString(" <br> ")+"</html>"
            else if(msg.length==1) {
                if(deMem)msg(0)
            else "Avatar: " + msg(0)}
                
            else "Erreur dans la reponse"
        val avatarMessage = new FlowPanel {
                    contents += new Label {icon = avatarIcon}
                    contents += new Label {text =  rep}
                }
        chatArea.contents += avatarMessage
        if(!deMem)memoire = memoire :+ rep 
        
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
                    refresh
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
                addMsgAva(List(memoire(i)),true)
            }
            
        }
        
        chatArea.revalidate();
        chatArea.repaint();
    }
    def setComponentColors(component: Component, bg: Color, fg: Color): Unit = {
    component.background = bg
    component.foreground = fg
    component match {
        case container: Container =>
        for (child <- container.contents) {
            setComponentColors(child, bg, fg)
        }
        case _ =>
    }
}
    def setComponentColors(dialog: Dialog, bg: Color, fg: Color): Unit = {
        dialog.background = bg
        dialog.foreground = fg
        for (child <- dialog.contents) {
            setComponentColors(child, bg, fg)
        }
    }
    def refresh{
        scrollPane.revalidate()
        chatArea.revalidate()
        scrollPane.verticalScrollBar.value = scrollPane.verticalScrollBar.maximum
        chatArea.repaint()
        scrollPane.repaint()

        for (cont <- contents)setComponentColors(cont,customBackground,customText)
        setComponentColors(outils,customBackground,customText)
        setComponentColors(colorChoose,customBackground,customText)
    }
}