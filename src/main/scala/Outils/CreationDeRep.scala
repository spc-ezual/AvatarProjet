package Outils

object CreationDeRep {
  def Reponse(message: String): List[String] = {
    val a = CreationListMotClef.MatchMotClef(AnalysePhrase.SepMots(message))
    var rep=List() : List[String]
    for(i <- 0 until a._1){
      rep= rep :+("Bonjour")
    }
    if(a._2.isEmpty){
      rep = rep:+("La demande entrée est invalide.")
    }
    else{
      for((nom,adresse) <- a._2 ){
        rep=rep:+(("L'adresse de " + nom + " est : " + adresse ))
      }
    }
    rep
  }
}
