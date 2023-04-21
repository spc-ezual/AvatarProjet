package application

import library._

object AnalysePageObjet {

  /**
    * 
    *
    * @param mots liste des mot de la recherche
    * @return retourne le couple nom adresse du restaurant si inconnu None
    */
  def getNomAdres(mots :List[String]):Option[(String,String)]={
    val req ="https://www.linternaute.com/restaurant/guide/ville-rennes-35000/?name="+mots.mkString("+").replace("é","e")
    val pageRech=urlToHtml(req)
    
    obtenir1liens(pageRech) match{
      case None => None
      case Some(value) => {
        resultatsPage("https://www.linternaute.com"+value)
      }
    }
    
  }
  /**
    * 
    *
    * @param url url sur la quel rechercher
    * @return retourne le couple nom adresse du restaurant si inconnu None
    */
  def resultatsPage(url: String): Option[(String,String)] = {
    //println("HTML")
    val html: Html = urlToHtml(url)
    //println("Adresse")
    val adresse =obtenirAdr(html)
    //println("Nom")
    val nom = obtenirNom(html)
    //println(adresse,nom)
    if(adresse!=None && nom != None) return Some((nom.get,adresse.get))
    else None
  }

  /**
    * 
    *
    * @param url url a recupere
    * @return la page html associer
    */
  def urlToHtml(url: String): Html = {
    OutilsWebObjet.obtenirHtml(url)
  }
/**
  * 
  *
  * @param h page html dans la quel rechercher le lien
  * @return retourn le 1er lien qui correspond a la recherche sinon None
  */
  def obtenir1liens(h: Html): Option[String] = h match {
    case Texte(content) => None
    
    case Tag("div", attributes, children) => {
      if (attributes.contains(("class", "grid_col w25"))){
        children.collectFirst{ case Tag(_,attributes,_) => attributes.collect{case ("href", url)=> url}(0)}
      }
      else {
        children.foldLeft[Option[String]](None)((acc, child) =>
        acc.orElse(obtenir1liens(child)))
    }}
      
      case Tag(_, _, children) =>{
      children.foldLeft[Option[String]](None)((acc, child) =>
        acc.orElse(obtenir1liens(child))
      )
    }
  }
  /**
    * 
    *
    * @param h page html dans la quel rechercher l'adresse
    * @return retourne l'adresse sinon None
    */
  def obtenirAdr(h: Html): Option[String] = h match {
    case Texte(content) => None
    
    case Tag("li", attributes, children) =>
      if (attributes.contains(("class", "icomoon-location"))) {
        //println("trouver")
        val addressNodes = children.collect {
          case Tag("span", _, List(Texte(content))) => content.trim
        }
        if (addressNodes.length >= 1) {
          //println("re trouver")
          Some(addressNodes(0))
        } else {
          //println("non trouver")
          None
        }
      } else {
        children.foldLeft[Option[String]](None)((acc, child) =>
        acc.orElse(obtenirAdr(child)))
      }
      case Tag(_, _, children) =>
      children.foldLeft[Option[String]](None)((acc, child) =>
        acc.orElse(obtenirAdr(child))
      )
  }
  /**
    * 
    *
    * @param h page html dans la quel rechercher le nom
    * @return retourne le nom sinon None
    */
  def obtenirNom(h: Html): Option[String] = h match {
    case Texte(_) => None
    case Tag("h1", attributes, children) =>{
          //println("trouver")
      children.collectFirst { case Texte(content) =>
        content
      }}
    case Tag(_, _, children) =>
      children.foldLeft[Option[String]](None)((acc, child) =>
        acc.orElse(obtenirNom(child))
      )
  }

}
