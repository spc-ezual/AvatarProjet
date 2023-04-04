package application

import library._

object AnalysePageObjet extends AnalysePage {

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
  override def resultats(url: String): String = {
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

  /** A partir d'une liste de couple (URL, page HTML) revoie la liste de couple
    * (URL,page HTML) filtrée qui répond à la requète
    * @param lurlsHtml
    *   La liste de couple référence
    * @param exp
    *   l'expretion correspondente a la requète
    */

  /** A partir d'un HTML, renvoie le titre de la page
    * @param h
    *   la page HTML
    */
  def obtenirAddr(h: Html): String = {
    def loop(html: Html): Option[String] = {
      html match {
        case Tag("title", _, List(Texte(title))) => Some(title)
        case Tag(_, _, children) =>
          children.flatMap(loop).headOption
        case _ => None
      }
    }

    loop(h).getOrElse(
      throw new NoSuchElementException("Aucun titre trouvé.")
    )
  }
}
