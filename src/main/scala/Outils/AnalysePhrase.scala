package Outils

import java.text.Normalizer

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

def compartList(lMotsBdd:List[String], lMotsRequet:List[String]): Boolean ={
        lMotsBdd match {
            case head :: next => lMotsRequet.contains(head)&&compartList(next,lMotsRequet)
            case Nil => true
        }
    }
    
def areEqualIgnoreCaseAndAccents(char1: Char, char2: Char): Boolean = {
            // Convertir les caractères en chaînes de caractères et les normaliser
            val str1 = Normalizer.normalize(char1.toString, Normalizer.Form.NFD)
            val str2 = Normalizer.normalize(char2.toString, Normalizer.Form.NFD)
            // Convertir les chaînes de caractères en minuscules et comparer
            str1.substring(0, 1).toLowerCase == str2.substring(0, 1).toLowerCase
        }


}
