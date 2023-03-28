package Outils

object CreationDeRep {

  def Reponse(message: String): List[String] = {
    //System.out.println("\n Entré: " +message)
    val messageSep=AnalysePhrase.SepMots(message)
    val a = CreationListMotClef.MatchMotClef(messageSep)
    var rep=List() : List[String]
    for(i <- 0 until a._1){
      rep= rep :+("Bonjour")
    }
    if(messageSep.length==a._1){
      //System.out.println("\n Reponse: "+formatReponse(rep))
      return rep
    }
    if(a._2.isEmpty){
      rep = rep:+("Je ne comprends pas votre demande")
    }
    else{
      for((nom,adresse) <- a._2 ){
        rep=rep:+(("L'adresse de " + nom + " est : " + adresse ))
      }
    }
    //System.out.println("\n Reponse: "+formatReponse(rep))
    rep
  }
  def formatReponse(reponse: List[String]): String = {
    reponse.map(_ + ".").mkString(" ")
  }

}
