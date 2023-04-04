package Outils

import DB.LangDAO

object CreationDeRep {
	
	var langues = 0 :Int
	var action = 1 : Int 
	/*
	0 -> demande validation lang
	1 -> attente demande lieux
	2 -> attente demande dans un choix 
	3 -> 
	*/
	def Reponse(message: String): List[String] = {
		//System.out.println("\n Entré: " +message)
		val messageSep=AnalysePhrase.SepMots(message)

		val langTrouve = AnalysePhrase.VerifLang(langues,messageSep)
		if(langTrouve!=langues){
			langues = langTrouve
			action = 0
			//System.out.println("\n Reponse: "+LangDAO.demandeLang(langues))
			return List(LangDAO.demandeLang(langues))
		}
		action match{
			case 0  => {
				if(message.replace(" ","").equals(LangDAO.vrai(langues))){
					action = 1
					//System.out.println("\n Reponse: "+LangDAO.demandeLieux(langues))
					return List(LangDAO.demandeLieux(langues))
				}
				else{
					langues = (langues + 1)%5
					//System.out.println("\n Reponse: "+LangDAO.demandeLang(langues))
					return List(LangDAO.demandeLang(langues))
				}
			}

			case 1 => {
				val corres = CreationListMotClef.MatchMotClef(messageSep,langues)
				var rep=List() : List[String]
				val politesse= LangDAO.politesse(langues).head
				val Array(firstPart, secondPart) = LangDAO.reponseUni(langues).split("XXX", 2)
				for(i <- 0 until corres._1){
					rep = rep :+ (politesse.capitalize)
				}
				if(messageSep.length==corres._1){
					//System.out.println("\n Reponse: "+formatReponse(rep))
					return rep
				}
				if(corres._2.isEmpty){
					rep = rep :+ LangDAO.inconnu(langues)
				}
				else{
					for((nom,adresse) <- corres._2){
						rep = rep :+ ((firstPart+" "+nom+" "+secondPart+" : "+adresse))
					}
				}
				//System.out.println("\n Reponse: "+formatReponse(rep))
				return rep
			}
		
		}
		
	}
	def formatReponse(reponse: List[String]): String = {
		reponse.map(_ + ".").mkString(" ")
	}

	def reInit():Unit={
		langues =0
		action =1
	}
}
