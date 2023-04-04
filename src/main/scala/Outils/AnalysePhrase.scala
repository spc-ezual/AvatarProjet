package Outils

import java.text.Normalizer
import DB.LangDAO

object AnalysePhrase {

    /** @return
        */
    def SepMots(phrase: String): List[String] = {
        phrase.split("[ ,./:!?<>();_+-={}&#|']+").toList
    }

    def compartList(
        lMotsBdd: List[String],
        lMotsRequet: List[String]
    ): Boolean = {
        lMotsBdd match {
        case head :: next =>
            lMotsRequet.contains(head) && compartList(next, lMotsRequet)
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
    def VerifLang(l:Int,mots :List[String]):Int= {
        for (i <- 0 to 4)  {
            for(mot <- LangDAO.getMotsLang(i)){
                if(mots.contains(mot))return i
            }
        }
        return VerifLangCorrect(l,mots)
    }
    def VerifLangCorrect(l:Int,mots :List[String]):Int={
            for( i <- 0 to 4 ){
            val MotsPol = LangDAO.politesse(i)
            val NomLang = LangDAO.nom(i)
                for(mot <- Correction.correct(mots,MotsPol,3)._1){
                    if(MotsPol.contains(mot))return i
                }
                if(Correction.correct(mots,List(NomLang),2)._1.contains(NomLang)) return i   
            }
                return l
    }
}
