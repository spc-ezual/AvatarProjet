package Outils

import DB.ListLieuxDAO
import DB.LangDAO

object CreationListMotClef {
  //Objet qui donne acces a la base de donné des lieux
  val dsbLieux = ListLieuxDAO
  /**
    * 
    * @param mots Phrase couper a chaque mots
    * @return 1ere string: les politesses, List (nom,adresse)
    */
  def MatchMotClef(mots: List[String],l:Int): (Int, List[(String, String)]) = {
    var RepNA : List[(String, String)] = List()
    var Corri :(List[String],List[String]) = Correction.correct(mots,LangDAO.politesse(l),2)
    
    
    val RepPoli = Corri._1.length
    for(Nele <- dsbLieux.getNom()){
      val NMots = AnalysePhrase.SepMots(Nele)
      val temp =  Correction.correct(Corri._2,NMots,1)
      Corri= (Corri._1 ::: temp._1,temp._2)
      
      if(AnalysePhrase.compartList(NMots,Corri._1)){
        Corri =(AnalysePhrase.removeWords(Corri._1,NMots),Corri._2)
        RepNA=(RecupNomReel(Nele),RecupLieux(Nele))::RepNA
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
  def RecupPoli(mots: List[String],dsbPolitesse :List[String]): Int = {
    var rep = 0
    for (mot <- mots) {
      if (dsbPolitesse.contains(mot)) {
        rep+=1
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

    def RecupNomReel(mot: String): String = {
    dsbLieux.getNomReel(mot) match{
      case None => ""
      case Some(value) => value
    } 
    }
}
