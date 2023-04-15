package Outils
import org.apache.commons.text.StringEscapeUtils
import java.text.Normalizer
import DB.LangDAO

object AnalysePhrase {

    /**
      * 
      *
      * @param phrase Phrase 
      * @return separe l'ensemble des mots de la phrase
      */
    def SepMots(phrase: String): List[String] = {
        phrase.split("[ ,./:!?<>();_+-={}&#|']+").toList
    }
    /**
      * 
      *
      * @param lMotsBdd
      * @param lMotsRequet
      * @return
      */
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
    /**
      * 
      *
      * @param char1 chaine de caratere à comparer
      * @param char2 chaine de caratere à comparer
      * @return retourn si c'est deux chaine de caratere sont egals en retirant les accents et la casse
      */
    def areEqualIgnoreCaseAndAccents(char1: Char, char2: Char): Boolean = {
        // Convertir les caractères en chaînes de caractères et les normaliser
        val str1 = Normalizer.normalize(char1.toString, Normalizer.Form.NFD)
        val str2 = Normalizer.normalize(char2.toString, Normalizer.Form.NFD)
        // Convertir les chaînes de caractères en minuscules et comparer
        str1.substring(0, 1).toLowerCase == str2.substring(0, 1).toLowerCase
    }
    /**
      * 
      *
      * @param mots list de mots
      * @param motsSupp list de mot a supprimé de mots
      * @return retourne mots sans les element de motsSupp
      */
    def removeWords(mots: List[String], motsSupp: List[String]): List[String] = {
        mots.filterNot(motsSupp.contains)
    }
    /**
      * 
      *
      * @param l Entier qui reprsente la langue
      * @param mots list de mots 
      * @return retourne l'entier qui corresond a la lang trvouer sinon retourn celui en entré
      */
    def VerifLang(l:Int,mots :List[String]):Int= {
        for (i <- 0 to 4)  {
            for(mot <- mots){
                if(LangDAO.getMotsLang(i).contains(mot.toLowerCase()))return i
            }
        }
        return VerifLangCorrect(l,mots)
    }
    /**
      * 
      *
      * @param l Entier qui reprsente la langue
      * @param mots list de mots 
      * @return retourne l'entier qui corresond a la lang trvouer en ayant corrigé les mots sinon retourne celui en entré
      */

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
    /**
      * 
      *
      * @param chaine une chaine de caratere
      * @return retourne en option le premier nombre trouvé sinon None
      */
    def premierNombreDansString(chaine: String): Option[Int] = {
        val regex = """\d+""".r
        val matche = regex.findFirstIn(chaine)
        matche.map(_.toInt)
    }
    /**
      * 
      *
      * @param liste liste des element restant a combinée
      * @param acc liste de combinaison actuel
      * @return retourn toute les combinaison possible des element de liste avec acc 
      */
    def combinaisons[T](liste: List[T], acc: List[List[T]]): List[List[T]] = {
        liste match {
        case Nil => acc.reverse
        case x :: xs => {
            val nouvellesCombinaisons = acc.map(_ :+ x)
            combinaisons(xs, nouvellesCombinaisons ++ acc :+ List(x))
        }
        }
    }
    /**
      * 
      *
      * @param mots lists de mots a comparer
      * @param Resto list des orthographe pour les resto
      * @return retourne en option le nom dus resto rechercher sinon None
      */
    def rechercheResto(mots :List[String],Resto :List[String]):Option[List[String]]={
        mots match {
            case Nil => None
            case head :: next => {
                if (Correction.correctMot(head,Resto).equals("")) rechercheResto(next,Resto)
                else Some(next)
            }
        }
    }
    /**
      * 
      *
      * @param phrase Chaine de caratere ou l'on doit retiere les entiter html
      * @return retourne la chaine de caratere avec les entiter html remplacer
      */
    def replaceHtmlEntities(phrase: String): String = {
        StringEscapeUtils.unescapeHtml4(phrase)
}
}
