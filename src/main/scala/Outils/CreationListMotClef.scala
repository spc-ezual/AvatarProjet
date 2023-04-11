package Outils

import DB.ListLieuxDAO
import DB.LangDAO
import DB.LieuxXMLDAO
import application.AnalysePageObjet

object CreationListMotClef {
  //Objet qui donne acces a la base de donné des lieux de base
  val dsbLieuxBase= ListLieuxDAO
  val dsbLieuxXML = LieuxXMLDAO
  /**
    * 
    * @param mots Phrase couper a chaque mots
    * @return 1ere string: les politesses, List (nom,adresse)
    */
  def MatchMotClef(mots: List[String],l:Int): (Int, List[(String, String)]) = {
    var memoire =List() : List[String]
    val sansRecherche = AnalysePhrase.removeWords(mots,LangDAO.getMotsRech(l))
    var RepNA : List[(String, String)] = List()
    var Corri :(List[String],List[String]) = Correction.correct(sansRecherche,LangDAO.politesse(l),2)
    
    
    val RepPoli = Corri._1.length
    val resto = AnalysePhrase.rechercheResto(Corri._2,LangDAO.getMotsRest(l))
    if(resto!=None){
      AnalysePageObjet.getNomAdres(resto.get) match{
        case None => return (RepPoli,Nil)
        case Some(value) => return (RepPoli,List((AnalysePhrase.replaceHtmlEntities(value._1),AnalysePhrase.replaceHtmlEntities(value._2))))
      }
    }
    for(Nele <- dsbLieuxBase.getNom()){
      val NMots = AnalysePhrase.SepMots(Nele)
      val temp =  Correction.correct(Corri._2,NMots,1)
      Corri= (Corri._1 ::: temp._1,temp._2)
      
      if(AnalysePhrase.compartList(NMots,Corri._1)){
        Corri =(AnalysePhrase.removeWords(Corri._1,NMots),Corri._2)
        RepNA=(RecupNomReel(Nele),RecupLieux(Nele))::RepNA
      }
    }
    if(RepNA.isEmpty)
        {val motsCorrect = Corri._1 ::: Correction.correct(Corri._2,dsbLieuxXML.listeMotsUniques(),4)._1

        for(nMot <- AnalysePhrase.combinaisons(motsCorrect,Nil)){
          LieuxXMLDAO.getId(nMot) match {
            case None => {}
            case Some(value) => {
              if(memoire.isEmpty||memoire.length>value.length)memoire = value
            }
          }
        }
        for( id <- memoire){
          RepNA = (RecupNomXML(id), RecupAdrXML(id))::RepNA
        }}
    (RepPoli,RepNA)
  }

/**
  * 
  *
  * @param mots une liste de mots clés permettant d'identifier la politesse à rechercher.
  * @return une chaîne de caractères contenant toutes les salutations trouvées dans la liste dsbPolitesse, séparées par un espace.
  */
  def RecupPoli(mots: List[String],dsbPolitesse :List[String]): Int = {
    var rep = 0
    for (mot <- mots) {
      if (dsbPolitesse.contains(mot)) {
        rep+=1
      }
    }
    rep
  }

  /**
    * 
    *
    * @param mots nom du lieux recherché.
    * @return l'adresse d'un lieu correspondant à un nom 
    */
  def RecupLieux(mot: String): String = {
    dsbLieuxBase.getAdresse(mot) match{
      case None => ""
      case Some(value) => value
    } 
    }

    def RecupNomReel(mot: String): String = {
    dsbLieuxBase.getNomReel(mot) match{
      case None => ""
      case Some(value) => value
    } 
    }
    def RecupNomXML(id:String):String={
      dsbLieuxXML.getNameId(id) match {
        case None => ""
        case Some(value) => value
      }
    }
    def RecupAdrXML(id:String):String={
      dsbLieuxXML.getAdresseId(id) match{
        case None => ""
        case Some(value) => value
      }
    }
}
