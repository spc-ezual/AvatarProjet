package library

trait AnalysePage{
/** A partir d'une URL de requête sur le site de référence et d'une expression exp, 
    retourne de pages issues de la requête et satisfaisant l'expression.

    @param url l'URL de la requête sur le site de référence 
    @param exp l'expression à vérifier sur les pages trouvées
    @return la liste des couples (titre,ref) où ref est l'URL d'une page
            satisfaisant l'expression et titre est son titre. */
  def resultats(url:String):String
}




trait ProductionResultat{
/** A partir d'une liste de couples (titre,URL), produit un document Html, qui
    liste les solutions sous la forme de liens cliquables

    @param l la liste des couples solution (titre,URL)
    @return le document Html listant les solutions
 */
  def resultatVersHtml(l:List[(String,String)]):Html
}

trait HtmlVersString{
/** Produit la chaîne de caractère correspondant à un document Html
    
    @param h le document Html
    @return la chaîne de caractère représentant h
 */
  def traduire(h:Html):String
}