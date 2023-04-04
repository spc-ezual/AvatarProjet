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

    def getAdresseId(id: String): Option[String] = {
        val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
        val statement: Statement = connection.createStatement()
        val query: String = s"SELECT street_number,street_name FROM addresses WHERE organization_id= '$id'"
        val resultSet=statement.executeQuery(query)
        val rep =
            if(resultSet.next()) Some(resultSet.getString("street_number")+", "+resultSet.getString("street_name"))else None
        resultSet.close()
        statement.close()
        connection.close()
        rep
    }
    /**
      * 
      *
      * @param noms Liste non-vide des noms d'organization
      * @return
      */
    def getId(noms: List[String]): Option[List[String]] = {
        val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
        val statement: Statement = connection.createStatement()
        var req: String = "SELECT id FROM organizations WHERE"
        for(i <- 0 until noms.length){
            req = req + "organization LIKE '%" + noms(i) + "%'"
            if (i != noms.length-1) req += "AND"
        }
        req += "COLLATE utf8_general_ci;"
        val resultSet=statement.executeQuery(req)
        val result = 
             if(resultSet.next()) Some(resultSet.getString("id").split(",").map(_.trim).toList)else None
        

        resultSet.close()
        statement.close()
        connection.close()
        result
    }
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

}