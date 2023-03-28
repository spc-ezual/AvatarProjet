package Outils

object CreationDeRep {

  def Reponse(message: String): List[String] = {
    val a = CreationListMotClef.MatchMotClef(AnalysePhrase.SepMots(message))
    var rep=List() : List[String]
    for(i <- 0 until a._1){
      rep= rep :+("Bonjour")
    }
    if(a._2.isEmpty){
      rep = rep:+("Je ne comprends pas votre demande")
    }
    else{
      for((nom,adresse) <- a._2 ){
        rep=rep:+(("L'adresse de " + nom + " est : " + adresse ))
      }
    }
    rep
  }
  def formatReponse(reponse: List[String]): String = {
    reponse.map(_ + ".").mkString(" ")
  }

}
