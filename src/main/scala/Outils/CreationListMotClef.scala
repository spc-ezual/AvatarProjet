package Outils

import DB.ListLieuxDAO
import DB.PolitesseDAO

object CreationListMotClef {
  val dsbLieux = ListLieuxDAO
  val dsbPolitesse = PolitesseDAO
  def MatchMotClef(
      mots: List[String]
  ): (String, List[(String, String)]) = ???
}
