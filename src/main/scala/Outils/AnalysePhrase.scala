package Outils

object AnalysePhrase {

  /** @return
    */
  def SepMots(phrase: String): List[String] = {
    phrase.split(" ").toList
  }

}
