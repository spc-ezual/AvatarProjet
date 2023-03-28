package Outils

object CreationDeRep {
  def Reponse(message: String): String = {
    val a = CreationListMotClef.MatchMotClef(AnalysePhrase.SepMots(message))
    a match {
      case ("", Nil)        => "La demande entrée est invalide."
      case (politesse, Nil) => politesse + ", la demande entrée est invalide."
      case ("", reste) =>
        var rep = ""
        for (ele <- reste) {
          rep += ("L'adresse de " + ele._1 + " est : " + ele._2 + "./n")
        }
        rep
      case (politesse, reste) =>
        var rep = politesse+" "
        for (ele <- reste) {
          rep += ("L'adresse de " + ele._1 + " est : " + ele._2 + ". ")
        }
        rep
    }
  }
}
