package Outils

import DB.ListLieuxDAO

object CreationListMotClef {
  //Objet qui donne acces a la base de donné des lieux
  val dsbLieux = ListLieuxDAO
  //Objet qui donne acces a la base de donné des politesses
  val dsbPolitesse = List("Bonjour","Salut","Bonsoir")
  /**
    * 
    * @param mots Phrase couper a chaque mots
    * @return 1ere string: les politesses, List (nom,adresse)
    */
  def MatchMotClef(mots: List[String]): (String, List[(String, String)]) = {
    var RepPoli=""
    var RepNA =Nil
    var Corri = Correction.correct(mots,dsbPolitesse)
    RepPoli = RecupPoli(Corri)
    for(Nele <- dsbLieux.getNom()){
      val NMots = AnalysePhrase.SepMots(Nele)
      Corri=Correction.correct(Corri,NMots)
      if(AnalysePhrase.compartList(NMots,Corri)){
        RepNA.appended((Nele,RecupLieux(Nele)))
      }
    }
    
    (RepPoli,RepNA)
  }

/**
  * 
  *
  * @param mots une liste de mots clés permettant d'identifier la politesse à rechercher.
  * @return une chaîne de caractères contenant toutes les salutations trouvées dans la liste dsbPolitesse, séparées par un espace.
  */
  def RecupPoli(mots: List[String]): String = {
    var rep = ""
    for (mot <- mots) {
      if (dsbPolitesse.contains(mot)) {
        rep += "Bonjour "
      }
    }
    rep
  }

  /**
    * 
    *
    * @param mots nom du lieux recherché.
    * @return l'adresse d'un lieu correspondant à un nom 
    */
  def RecupLieux(mot: String): String = {
    dsbLieux.getAdresse(mot) match{
      case None => ""
      case Some(value) => value
    } 
    }

}
