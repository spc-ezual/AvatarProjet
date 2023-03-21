package Outils

import scala.collection.immutable

object Correction {
    /**
      * prend en entre une list de mots a corriger et une list de mots de référance et renvoie une liste de mot corriger si cela est possible
      * @param mots une list de mot a corriger
      * @param motsCorrect une liste de mots de référance
      * @return
      */
    def correct(mots: List[String], motsCorrect: List[String]): List[String] = {
        mots.map(x => correctMot(x,motsCorrect))
    }

    def correctMot(mot: String, motsCorrect: List[String]): String = {
        motsCorrect match {
            case head :: next => compartMot(mot,head) match {
                case None => correctMot(mot,next)
                case Some(value) => value
            }
            case Nil => mot
        }
    }

    def compartMot(s1: String, s2: String): Option[String] = {
        val (shorter, longer) = if (s1.length() < s2.length()) (s1, s2) else (s2, s1)
        val sl = shorter.length()
        val ll = longer.length()
        if (sl == ll){
            if(hammingDistance(s1,s2) <= 1){
                return Some(s2)
            }else{
               return None
            }
        }else if(ll- sl == 1){
            var diff = 0
            for(x <- 0 until(ll)){
                if(shorter.charAt(x + diff) != longer.charAt(x)){
                    diff += 1
                }
                if(diff > 1){
                    return None
                }
            }
            return Some(s2)
        }else{
            return None
        }

    }
    def hammingDistance(s1: String, s2: String): Int = {
        if (s1.length != s2.length){
            throw new Exception("les deux mots ont une taille differante")
        }
        else{
            s1.zip(s2).count{case (c1, c2) => c1.toUpper != c2.toUpper}
        }
    }
    
}
