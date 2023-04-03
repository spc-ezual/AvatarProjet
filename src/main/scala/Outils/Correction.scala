package Outils

import scala.collection.immutable
class LongDiffException(message: String) extends Exception(message)
object Correction {

    /** prend en entre une list de mots a corriger et une list de mots de référance et renvoie une liste de mot corriger si cela est possible
        * @param mots une list de mot a corriger
        * @param motsCorrect une liste de mots de référance
        * @return la list de mot corriger
        */
    def correct(mots: List[String], motsCorrect: List[String]): List[String] = {
        mots.map(x => {if(x.length>2)correctMot(x, motsCorrect) else x})
    }
    //TODO A refaire / finir
    // L'on souhaite que correct concat en cas de nececiter
    /*
        exemple :
            phrase =("bonjour","ou","est","la","mairie") => phrase =("bonjour","ou","est","la","Mairie")
            phrase =("bonjour","ou","est","la","airie") => phrase =("bonjour","ou","est","la","Mairie")
            phrase =("bonjour","ou","est","la","mairie","de","rennes") => phrase =("bonjour","ou","est","la","Mairie de Rennes")
            phrase =("bonjour","ou","est","la","mairie","Rennes") => phrase =("bonjour","ou","est","la","Mairie de Rennes"")
    */

    /** prend en entre un mot a corriger et une list de mots de référance et renvoie un mot corriger si cela est possible
        * @param mot un string non vide a corrige
        * @param motsCorrect une liste de mots de référance
        * @return le mot corriger si il y en a un, sinon mot
        */
    def correctMot(mot: String, motsCorrect: List[String]): String = {
        motsCorrect match {
        case head :: next =>
            compartMot(mot, head) match {
            case None        => correctMot(mot, next)
            case Some(value) => value
            }
        case Nil => mot
        }
    }

    /** prend en entre un mot a corriger et un mot correct si la distance de hamming est egale a 0 ou a 1 renvoie le mot corriger, sinon ne renvoie rien
        * @param mot un string a corrige
        * @param mot Correct
        * @return le mot corriger ou rien
        */
    def compartMot(s1: String, s2: String): Option[String] = {
        val (shorter, longer) =
        if (s1.length() < s2.length()) (s1, s2) else (s2, s1)
        val sl = shorter.length()
        val ll = longer.length()
        if (sl == ll) {
        if (hammingDistance(s1, s2) <= 1) {
            Some(s2)
        } else {
            None
        }
        } else if (ll - sl == 1) {
        var diff = 0
        for (x <- 0 until sl) {
            if (
            !AnalysePhrase.areEqualIgnoreCaseAndAccents(
                shorter.charAt(x),
                longer.charAt(x + diff)
            )
            ) {
            diff += 1
            }
            if (
            diff > 1 || !AnalysePhrase.areEqualIgnoreCaseAndAccents(
                shorter.charAt(x),
                longer.charAt(x + diff)
            )
            ) {
            return None
            }
        }
        Some(s2)
        } else {
        None
        }
    }

    /** prend en entrer 2 de même longueur mot et calcule la distance de hamming sans prendre compte des majuscule et des accent
        * @param s1 un string
        * @param s2 un string
        * @return le mot corriger ou rien
        */
    def hammingDistance(s1: String, s2: String): Int = {
        if (s1.length != s2.length) {
        throw new LongDiffException("Les chaînes doivent être de même longueur.")
        } else {
        s1.zip(s2).count { case (c1, c2) =>
            !AnalysePhrase.areEqualIgnoreCaseAndAccents(c1, c2)
        }
        }
    }

}
