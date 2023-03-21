package Outils

class Correction {
    /**
      * prend en entre une list de mots a corriger et une list de mots de référance et renvoie une liste de mot corriger si cela est possible
      * @param mots une list de mot a corriger
      * @param motsCorrect une liste de mots de référance
      * @return
      */
    def Correct(mots: List[String], motsCorrect: List[String]): List[String] = ???

    def CorrectMot(mot: String, motsCorrect: List[String]): String = ???


    def hammingDistance(s1: String, s2: String): Int = {
        if (s1.length != s2.length) {
            throw new IllegalArgumentException("Les deux chaînes doivent avoir la même longueur.")
        } else {
            s1.zip(s2).count{case (c1, c2) => c1 != c2}
        }
    }
}
