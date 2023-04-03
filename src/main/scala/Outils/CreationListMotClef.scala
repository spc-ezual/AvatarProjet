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
    var Corri :List[String] = mots
    if(l!=1){
      Corri = Correction.correct(Corri,LangDAO.politesse(l))
    }
    val RepPoli = RecupPoli(Corri,LangDAO.politesse(l))
    for(Nele <- dsbLieux.getNom()){
      val NMots = AnalysePhrase.SepMots(Nele)
      Corri=Correction.correct(Corri,NMots)
      if(AnalysePhrase.compartList(NMots,Corri)){
        Corri= AnalysePhrase.removeWords(Corri,NMots)
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
