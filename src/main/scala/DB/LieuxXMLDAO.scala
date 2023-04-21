package DB

import java.sql._
import java.io.File

/** Classe permettant d'interagir avec la table ListLieux de la base de données ListLieux.db
  */
object LieuxXMLDAO {
    val dbFilePath = "BaseDeDonnees/DataLieuxXML.db"
    // Vérification que le fichier existe
    if (!new File(dbFilePath).exists() || !new File(dbFilePath).isFile()) {
        throw new RuntimeException(
        "Le fichier de base de données n'existe pas ou n'est pas un fichier valide."
        )
    }
    /**
      * 
      *
      * @param id id d'une organisation valide
      * @return retourne une adresse si elle existe sinon None
      */
    def getAdresseId(id: String): Option[String] = {
        val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
        val statement: Statement = connection.createStatement()
        val query: String = s"SELECT street_number,street_name FROM addresses WHERE organization_id= '$id'"
        val resultSet=statement.executeQuery(query)
        val rep =
            if(resultSet.next()) {
              val street_number = resultSet.getString("street_number")
              var street_name = resultSet.getString("street_name")
              if(street_name.length()>0) street_name = street_name.split(" ").map(word => word.head.toUpper  + word.tail.toLowerCase).mkString(" ")
            Some(street_number+
            ", "+street_name)}
            else None
        statement.close()
        connection.close()
        rep
    }
    /**
      * 
      *
      * @param mots Liste non-vide de mot a rechercher dans les name d'organisation
      * @return retourn l'ensemble des id d'organisation qui contiens noms dans name , None si ensemble vide
      */
    def getId(mots: List[String]): Option[List[String]] = {
        val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
        val statement: Statement = connection.createStatement()
        var req: String = "SELECT id FROM organizations WHERE id IN (SELECT organization_id FROM addresses WHERE city = 'Rennes') AND "
        for(i <- 0 until mots.length){
            req = req + " name LIKE '%" + mots(i) + "%' "
            if (i != mots.length-1) req += " AND "
        }
        req += " COLLATE utf8_general_ci; "
        val resultSet=statement.executeQuery(req)

        var resultList = List[String]()
        while (resultSet.next()) {
            resultList = resultList ++ resultSet.getString("id").split(",").map(_.trim).toList
        }

        resultSet.close()
        statement.close()
        connection.close()

        if (resultList.nonEmpty) Some(resultList) else None
}
    /**
      * 
      *
      * @param id id d'organisation valide 
      * @return name de l'organisation si elle existe sinon None
      */
    def getNameId(id: String): Option[String] = {
        val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
        val statement: Statement = connection.createStatement()
        val query: String = s"SELECT name FROM organizations WHERE id= '$id'"
        val resultSet=statement.executeQuery(query)
        val rep =
            if(resultSet.next()) Some(resultSet.getString("name"))else None
        resultSet.close()
        statement.close()
        connection.close()
        rep
    }
    /**
      * 
      *
      * @return l'ensemble des mots contenue dans les name de maniere unique
      */
    def listeMotsUniques(): List[String]={
        val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
        val statement: Statement = connection.createStatement()
        val query: String = s"SELECT name FROM organizations;"
        val resultSet=statement.executeQuery(query)
        var result: List[String] = List()
        while (resultSet.next()) {
        result = resultSet.getString("name").split("[ ,./:!?<>();_+-={}&#|']+").toList ::: result
        }
        resultSet.close()
        statement.close()
        connection.close()
        result.distinct
        

    }
    /**
      * 
      *
      * @return liste de tous les name des organisation
      */
    def getNom(): List[String] = {
        val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT nom FROM organizations")
        var noms: List[String] = List()
        while (resultSet.next()) {
        noms = resultSet.getString("nom") :: noms
        }
        resultSet.close()
        statement.close()
        connection.close()
        noms
    }

}