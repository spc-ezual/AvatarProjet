package DB

import java.sql._
import java.io.File
/**
  * Classe permettant d'interagir avec la table ListLieux de la base de données ListLieux.db
  */
object  LangDAO {
    val dbFilePath = "BaseDeDonnees/Lang.db"
    // Vérification que le fichier existe
    if (!new File(dbFilePath).exists() || !new File(dbFilePath).isFile()) {
        throw new RuntimeException("Le fichier de base de données n'existe pas ou n'est pas un fichier valide.")
    }

    val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
    
    /**
    * Ajouter une donnée à la table ListLieux
    *
    * @param nom le nom à ajouter
    * @param adresse l'adresse correspondante au nom
    */
    def ajouterDonnee(): Unit = {???}
    /**
     * Supprime une ligne dans la table "ListLieux" en fonction du nom donnée
     * @param nom nom de la ligne à supprimer
     */
    def deleteByID(id: Int): Unit = {
            val stmt: Statement = connection.createStatement()
            val query: String = s"DELETE FROM langues WHERE id = '$id'"
            stmt.executeUpdate(query)
            stmt.close()
    }

    /**
     * Récupère le nom de l'id dans la table "langues"
     * @return nom
     */
    def getNomLangue(id :Int): Option[String] = {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(s"SELECT nom FROM Lieux WHERE id = '$id'" )
        val nom = if (resultSet.next()) Some(resultSet.getString("nom")) else None
        resultSet.close()
        statement.close()
        nom
    }
    
}