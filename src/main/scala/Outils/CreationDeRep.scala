package Outils

import DB.LangDAO

object CreationDeRep {
	
	var langues = 0 :Int
	var action = 1 : Int 
	var memoire =List() :List[(String, String)]
	/*
	0 -> demande validation lang
	1 -> attente demande lieux
	2 -> attente demande dans un choix 
	3 -> 
	*/
	def Reponse(message: String): List[String] = {
		System.out.println("\n Entré: " +message)
		val messageSep=AnalysePhrase.SepMots(message)

		val langTrouve = AnalysePhrase.VerifLang(langues,messageSep)
		if(langTrouve!=langues){
			langues = langTrouve
			action = 0
			System.out.println("\n Reponse: "+LangDAO.demandeLang(langues))
			return List(LangDAO.demandeLang(langues))
		}
		action match{
			case 0  => {
				if(message.replace(" ","").equals(LangDAO.vrai(langues))){
					action = 1
					System.out.println("\n Reponse: "+LangDAO.demandeLieux(langues))
					return List(LangDAO.demandeLieux(langues))
				}
				else{
					langues = (langues + 1)%5
					System.out.println("\n Reponse: "+LangDAO.demandeLang(langues))
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
					System.out.println("\n Reponse: "+formatReponse(rep))
					return rep
				}
				if(corres._2.isEmpty){
					rep = rep :+ LangDAO.inconnu(langues)
				}
				else if(corres._2.length==1){
						rep = rep :+ ((firstPart+" "+corres._2.head._1+" "+secondPart+" : "+corres._2.head._2))
					}
				else{
					
					memoire=corres._2
					memoire = memoire.sortBy(_._1)
					rep = rep :+ LangDAO.multReponse(langues).replace("XXX",corres._2.length.toString())
					for( i <- 0 until memoire.length){
						rep = rep :+ ((i+1)+") "+memoire(i)._1)
					}
					rep = rep :+ LangDAO.demandeChoix(langues)
					action=2
				}
				System.out.println("\n Reponse: "+formatReponse(rep))
				return rep
			}
			case 2 =>{
				
				val reponse = AnalysePhrase.premierNombreDansString(message)
				val Array(firstPart, secondPart) = LangDAO.reponseUni(langues).split("XXX", 2)
				action=1
				reponse match{
					case Some(value) => {
						if(value <= memoire.length&&value>0){
							System.out.println( "\n Reponse:"+ firstPart+" "+memoire(value-1)._1+" "+secondPart+" : "+memoire(value-1)._2)
							return List((firstPart+" "+memoire(value-1)._1+" "+secondPart+" : "+memoire(value-1)._2))}
						else {
							System.out.println("\n Reponse :"+LangDAO.inconnu(langues))
							return List(LangDAO.inconnu(langues)) 
						}
					}
					case None => {
						System.out.println("\n Reponse :"+LangDAO.inconnu(langues))
						return List(LangDAO.inconnu(langues))
					}
				}
				

			}
		
		}
		
	}
	def formatReponse(reponse: List[String]): String = {
		reponse.map(_ + ".").mkString(" ")
	}

	def reInit{
		langues =0
		action =1
		memoire =List()
	}
}
