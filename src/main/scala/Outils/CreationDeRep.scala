package Outils

object CreationDeRep {
  def Reponse(message: String): List[String] = {
    val a = CreationListMotClef.MatchMotClef(AnalysePhrase.SepMots(message))
    a match {
      case ("", Nil)        => List("La demande entrée est invalide.")
      case (politesse, Nil) => List(politesse, "la demande entrée est invalide.")
      case ("", reste) =>
        var rep:List[String] = List()
        for (ele <- reste) {
          rep = rep :+ ("L'adresse de " + ele._1 + " est : " + ele._2 + ".")
        }
        rep
      case (politesse, reste) =>
        var rep:List[String] = List(politesse)
        for (ele <- reste) {
          rep = rep :+ ("L'adresse de " + ele._1 + " est : " + ele._2 + ".") 
        }
        rep
    }
  }
}
