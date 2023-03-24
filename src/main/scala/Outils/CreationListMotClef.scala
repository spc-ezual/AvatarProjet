package Outils

import DB.ListLieuxDAO
import DB.PolitesseDAO

object CreationListMotClef {
  val dsbLieux = ListLieuxDAO
  val dsbPolitesse = PolitesseDAO
  /**
    * 
    * @param mots Phrase couper a chaque mots
    * @return 1ere string: les politesses, List (nom,adresse)
    */
  def MatchMotClef(mots: List[String]): (String, List[(String, String)]) = {
    ("Les politesses",List(("nom1","Adresse1"),("nom2","Adresse2")))
  }

  def MatchPolitesse(mots : List[String]): String={
    ???
  }
}
