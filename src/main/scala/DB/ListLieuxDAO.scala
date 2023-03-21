package DB

import java.sql._
import java.io.File
/**
  * Classe permettant d'interagir avec la table ListLieux de la base de données ListLieux.db
  */
object  ListLieuxDAO {
    val dbFilePath = "BaseDeDonnees/ListLieux.db"
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
    def ajouterDonnee(nom: String, adresse: String): Unit = {
        val statement = connection.prepareStatement("INSERT INTO ListLieux (nom, adresse) VALUES (?, ?)")
        statement.setString(1, nom)
        statement.setString(2, adresse)
        statement.executeUpdate()
        statement.close()
    }
    /**
     * Supprime une ligne dans la table "ListLieux" en fonction du nom donnée
     * @param nom nom de la ligne à supprimer
     */
    def deleteByNom(nom: String): Unit = {
            val stmt: Statement = connection.createStatement()
            val query: String = s"DELETE FROM ListLieux WHERE nom = '$nom'"
            stmt.executeUpdate(query)
            stmt.close()
    }

    /**
     * Récupère la liste des nom présentes dans la table "ListLieux"
     * @return une liste des nom
     */
    def getNom(): List[String] = {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT nom FROM ListLieux")
        var noms: List[String] = List()
        while (resultSet.next()) {
        noms = resultSet.getString("nom") :: noms
        }
        resultSet.close()
        statement.close()
        noms
    }
    /**
     * Récupère la réponse associée à un nom donnée
     * @param nom nom de la réponse recherchée
     * @return une Option[String] contenant la réponse si elle existe, None sinon
     */
    def getAdresse(nom: String): Option[String] = {
        val statement = connection.prepareStatement("SELECT adresse FROM ListLieux WHERE nom = ?")
        statement.setString(1, nom)
        val resultSet = statement.executeQuery()
        val adresseOpt = if (resultSet.next()) Some(resultSet.getString("adresse")) else None
        resultSet.close()
        statement.close()
        adresseOpt
    }
}
