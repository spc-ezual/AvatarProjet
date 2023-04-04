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
        val query: String = s"SELECT street_number,street_name FROM addresses WHERE organization_idx     = '$id'"
        val resultSet=statement.executeQuery(query)
        val rep =
            if(resultSet.next()) Some(resultSet.getString("street_number")+", "+resultSet.getString("street_name"))else None
        resultSet.close()
        statement.close()
        connection.close()
        rep
    }
}