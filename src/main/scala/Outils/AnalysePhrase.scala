package Outils

object AnalysePhrase {

  /** @return
    */
  def SepMots(phrase: String): List[String] = {
  val mots = phrase.split("[ ,./:!?<>();_+-={}&#|]+")
  mots.flatMap { mot =>
    mot match {
      case motAvecApostrophe if motAvecApostrophe.endsWith("'") =>
        List(motAvecApostrophe.dropRight(1), "'")
      case motAvecApostrophe if motAvecApostrophe.contains("'") =>
        val (avantApostrophe, apresApostrophe) = motAvecApostrophe.splitAt(motAvecApostrophe.indexOf("'")+1)
        List(avantApostrophe, apresApostrophe)
      case _ => List(mot)
    }
  }.toList
}


}
