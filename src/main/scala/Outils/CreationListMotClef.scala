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
    val corriger = Correction.correct(Correction.correct(mots,dsbLieux.getNom()),dsbPolitesse)
    (RecupPoli(mots),RecupLieux(mots))
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
    * @param mots une liste de mots contenant les noms des lieux recherchés.
    * @return Une liste de couples (String, String), où chaque couple contient le nom et l'adresse d'un lieu correspondant à un nom présent dans mots.
    */
  def RecupLieux(mots: List[String]): List[(String, String)] = {
    mots.flatMap(nom => ListLieuxDAO.getAdresse(nom).map(adresse => (nom, adresse)))
  }

}
