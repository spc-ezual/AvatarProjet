package DB

import java.sql._
import java.io.File

/** Classe permettant d'interagir avec la table ListLieux de la base de données ListLieux.db
  */
object LangDAO {
	val dbFilePath = "BaseDeDonnees/Lang.db"
	// Vérification que le fichier existe
	if (!new File(dbFilePath).exists() || !new File(dbFilePath).isFile()) {
		throw new RuntimeException(
		"Le fichier de base de données n'existe pas ou n'est pas un fichier valide."
		)
	}
	
	/**
	  * @param l Id de la langue
	  * @return	retourne la list des politesse, mot de recherche et le nom de la langue d'id l
	  */
	def getMotsLang(l: Int): List[String] = {
		var rep = List(): List[String]
		val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
		val statement = connection.createStatement()
		val resultSet = statement.executeQuery(
		s"SELECT nom,politesse,recherche FROM langues WHERE id=$l"
		)
		while (resultSet.next()) {
		val nom = resultSet.getString("nom")
		val politesseValues =
			resultSet.getString("politesse").split(",").map(_.trim)
		val rechercheValues =
			resultSet.getString("recherche").split(",").map(_.trim)
		rep = nom +: (politesseValues ++ rechercheValues).toList

		}
		connection.close()
		rep
	}

	/**
	  * @param l Id de la langue
	  * @return	retourne la list des politesse de la langue d'id l
	  */
	def politesse(l: Int): List[String] = {
		val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
		val statement = connection.createStatement()
		val resultSet =
		statement.executeQuery(s"SELECT politesse FROM langues WHERE id=$l")
		val result = if (resultSet.next()) resultSet.getString("politesse") else ""
		connection.close()
		result.split(",").map(_.trim).toList
	}

	/**
	  * @param l Id de la langue
	  * @return	retourne la list des mot de recherche de la langue d'id l
	  */
	def recherche(l: Int): List[String] = {
		var rep = List(): List[String]
		val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
		val statement = connection.createStatement()
		val resultSet = statement.executeQuery(
		s"SELECT recherche FROM langues WHERE id=$l"
		)
		while (resultSet.next()) {
		rep= resultSet.getString("recherche").split(",").map(_.trim).toList
		}
		connection.close()
		rep
	}

	/**
	  * @param l Id de la langue
	  * @return	retourne le nom de la langue d'id l
	  */
	def nom(l: Int): String = {
		val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
		val statement = connection.createStatement()
		val resultSet =
		statement.executeQuery(s"SELECT nom FROM langues WHERE id=$l")
		val result = if (resultSet.next()) resultSet.getString("nom") else ""
		connection.close()
		result
	}

	/**
	  * @param l Id de la langue
	  * @return	retourne le mot qui veut dire true de la langue d'id l
	  */
	def vrai(l: Int): String = {
		val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
		val statement = connection.createStatement()
		val resultSet =
		statement.executeQuery(s"SELECT vrai FROM langues WHERE id=$l")
		val result = if (resultSet.next()) resultSet.getString("vrai") else ""
		connection.close()
		result
	}

	/**
	  * @param l Id de la langue
	  * @return	retourne le mot qui veut dire false de la langue d'id l
	  */
	def faux(l: Int): String = {
		val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
		val statement = connection.createStatement()
		val resultSet =
		statement.executeQuery(s"SELECT faux FROM langues WHERE id=$l")
		val result = if (resultSet.next()) resultSet.getString("faux") else ""
		connection.close()
		result
	}

	/**
	  * @param l Id de la langue
	  * @return	retourne la phrase pour une reponse unique de la langue d'id l
	  */
	def reponseUni(l: Int): String = {
		val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
		val statement = connection.createStatement()
		val resultSet =
		statement.executeQuery(s"SELECT reponse_unique FROM langues WHERE id=$l")
		val result =
		if (resultSet.next()) resultSet.getString("reponse_unique") else ""
		connection.close()
		result
	}

	/**
	  * @param l Id de la langue
	  * @return	retourne la phrase une incompehension de la langue d'id l
	  */
	def inconnu(l: Int): String = {
		val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
		val statement = connection.createStatement()
		val resultSet =
		statement.executeQuery(s"SELECT inconnu FROM langues WHERE id=$l")
		val result = if (resultSet.next()) resultSet.getString("inconnu") else ""
		connection.close()
		result
	}

	/**
	  * @param l Id de la langue
	  * @return	retourne la phrase pour une demande lieux de la langue d'id l
	  */
	def demandeLieux(l: Int): String = {
		val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
		val statement = connection.createStatement()
		val resultSet =
		statement.executeQuery(s"SELECT demande FROM langues WHERE id=$l")
		val result = if (resultSet.next()) resultSet.getString("demande") else ""
		connection.close()
		result
	
	}

	/**
	  * @param l Id de la langue
	  * @return	retourne la phrase pour une demande de langue de la langue d'id l
	  */
	def demandeLang(l: Int): String = {
		val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
		val statement = connection.createStatement()
		val resultSet =
		statement.executeQuery(s"SELECT demande_lang FROM langues WHERE id=$l")
		val result =
		if (resultSet.next()) resultSet.getString("demande_lang") else ""
		connection.close()
		result
	}

	/**
	  * @param l Id de la langue
	  * @return	retourne la phrase pour une demande de choix de la langue d'id l
	  */
	def demandeChoix(l: Int): String = {
		val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
		val statement = connection.createStatement()
		val resultSet =
		statement.executeQuery(s"SELECT demande_choix FROM langues WHERE id=$l")
		val result =
		if (resultSet.next()) resultSet.getString("demande_choix") else ""
		connection.close()
		result
	}

	/**
	  * @param l Id de la langue
	  * @return	retourne la phrase pour de multiple reponse de la langue d'id l
	  */
	def multReponse(l: Int): String = {
		val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
		val statement = connection.createStatement()
		val resultSet =
		statement.executeQuery(s"SELECT choix_multiple FROM langues WHERE id=$l")
		val result =
		if (resultSet.next()) resultSet.getString("choix_multiple") else ""
		connection.close()
		result
	}

	/**
	  * @param l Id de la langue
	  * @return	retourne la liste des nom qui sont compris comme des restaurant de la langue d'id l
	  */
    def getMotsRest(l:Int):List[String] ={
		var rep = List(): List[String]
		val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
		val statement = connection.createStatement()
		val resultSet = statement.executeQuery(
		s"SELECT mots_site FROM langues WHERE id=$l"
		)
		while (resultSet.next()) {
		rep= resultSet.getString("mots_site").split(",").map(_.trim).toList
		}
		connection.close()
		rep
	}

}