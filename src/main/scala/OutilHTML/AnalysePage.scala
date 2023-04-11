package application

import library._

object AnalysePageObjet {


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


  def urlToHtml(url: String): Html = {
    OutilsWebObjet.obtenirHtml(url)
  }

  def lURL(html: Html): List[String] = html match {
    case Tag(_, attributes, children) =>
      val links = attributes.collect { case ("href", url) => url }
      val images = attributes.collect { case ("src", url) => url }
      (links ++ images) ++ children.flatMap(lURL)
    case Texte(_) => Nil
  }

  def selectURL(lurl: List[String], exp: List[String]): Option[String] = {
    lurl match {
      case Nil => None
      case head :: next =>
        if (exp.forall( s => if(s.length>2)head.toLowerCase().contains(s.toLowerCase())else true) ) { Some(head) }
        else { selectURL(next, exp) }
    }
  }

  def obtenir1liens(h: Html): Option[String] = h match {
    case Texte(content) => None
    
    case Tag("div", attributes, children) => {
      if (attributes.contains(("class", "grid_col w25"))){
        children.collectFirst{ case Tag(_,attributes,_) => attributes.collect{case ("href", url)=> url}(0)}
        // TODO (0) est IMMONDE trouver une alternative
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
