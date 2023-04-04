package application

import library._

object AnalysePageObjet {

  /** A partir d’une URL de requete sur le site de reference et d’une expression
    * exp, retourne une liste de pages issues de la requeete et satisfaisant
    * l’expression.
    * @param url
    *   l’URL de la requ^ete sur le site de r´ef´erence
    * @param exp
    *   l’expression `a v´erifier sur les pages trouv´ees
    * @return
    *   la liste des couples (titre,ref) o`u ref est l’URL d’une page
    *   satisfaisant l’expression et titre est son titre.
    */
  def resultats(url: String): String = {
    val html: Html = urlToHtml(url)
    obtenirAddr(html)
  }

  /** A partir d'une URL revoie la page Html associée
    * @param url
    *   l'URL de la requête sur le site de référence
    */
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

  def selectURL(lurl:List[String],exp:String):Option[Html]={
    lurl match {
        case Nil => None
        case head :: next => if(head.contains(exp)){Some(urlToHtml(head))}else{selectURL(next,exp)}
      }
  }

  /** A partir d'un HTML, renvoie le titre de la page
    * @param h
    *   la page HTML
    */

  
  def obtenirAddr(h: Html): String = ???
}
