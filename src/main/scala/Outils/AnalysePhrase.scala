package Outils

import java.text.Normalizer

object AnalysePhrase {

  /** @return
    */
  def SepMots(phrase: String): List[String] = {
  phrase.split("[ ,./:!?<>();_+-={}&#|']+").toList
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

def removeWords(mots: List[String], motsSupp: List[String]): List[String] = {
  mots.filterNot(motsSupp.contains)
}
}
