package DB

import java.sql._
import java.io.File
/**
 * Cette classe permet d'accéder à la table "Politesse" de la base de données "Politesse.db"
 */
object  PolitesseDAO {
    // Établir une connexion à la base de données
    val dbFilePath = "BaseDeDonnees/Politesse.db"
    // Vérification que le fichier existe

    if (!new File(dbFilePath).exists() || !new File(dbFilePath).isFile()) {
        throw new RuntimeException("Le fichier de base de données n'existe pas ou n'est pas un fichier valide.")
    }

    val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)

    /**
     * Ajouter une donnée à la table "Politesse"
     * @param adresse l'adresse à ajouter à la table
     * @param reponse la réponse associée à l'adresse
     */
    def ajouterDonnee(adresse: String, reponse: String): Unit = {
        val statement = connection.prepareStatement("INSERT INTO Politesse (Adresse, Reponse) VALUES (?, ?)")
        statement.setString(1, adresse)
        statement.setString(2, reponse)
        statement.executeUpdate()
        statement.close()
    }
    /**
     * Supprime une ligne dans la table "Politesse" en fonction de l'adresse donnée
     * @param adresse l'adresse de la ligne à supprimer
     */
    def deleteByAdresse(adresse: String): Unit = {
        val stmt: Statement = connection.createStatement()
        val query: String = s"DELETE FROM Politesse WHERE Adresse = '$adresse'"
        stmt.executeUpdate(query)
        stmt.close()
    }
    /**
     * Récupère la liste des adresses présentes dans la table "Politesse"
     * @return une liste des adresses
     */
    def getAdresses(): List[String] = {
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT Adresse FROM Politesse")
        var adresses = List[String]()
        while (resultSet.next()) {
        val adresse = resultSet.getString("Adresse")
        adresses = adresse :: adresses
        }
        resultSet.close()
        statement.close()
        adresses
    }

    /**
     * Récupère la réponse associée à l'adresse donnée
     * @param adresse l'adresse de la réponse recherchée
     * @return une Option[String] contenant la réponse si elle existe, None sinon
     */
    def getReponse(adresse: String): Option[String] = {
        val statement = connection.prepareStatement("SELECT Reponse FROM Politesse WHERE Adresse = ?")
        statement.setString(1, adresse)
        val resultSet = statement.executeQuery()
        val adresseOpt = if (resultSet.next()) Some(resultSet.getString("Reponse")) else None
        resultSet.close()
        statement.close()
        adresseOpt
    }
}
