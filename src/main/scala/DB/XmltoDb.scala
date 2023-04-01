package DB

import java.sql._
import java.io.File
import scala.xml.{Elem, Node, XML}

object XmlToSql {
  val dbFilePath = "BaseDeDonnees/DataLieuxXML.db"
    // Vérification que le fichier existe
    if (!new File(dbFilePath).exists() || !new File(dbFilePath).isFile()) {
        throw new RuntimeException("Le fichier de base de données n'existe pas ou n'est pas un fichier valide.")
    }

    val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)
  
  // Define SQL statements to create tables
  val organizationsTableSql = "CREATE TABLE IF NOT EXISTS organizations (" +
    "id VARCHAR(50) PRIMARY KEY," +
    "acronym VARCHAR(50)," +
    "name VARCHAR(255) NOT NULL," +
    "description TEXT," +
    "email VARCHAR(255)," +
    "web VARCHAR(255)," +
    "schedule TEXT," +
    "theme TEXT[] NOT NULL," +
    "receptiontype VARCHAR(255)," +
    "latitude VARCHAR(50)," +
    "longitude VARCHAR(50)," +
    "accessibility TEXT" +
    ")"

  val addressesTableSql = "CREATE TABLE IF NOT EXISTS addresses (" +
    "id INT PRIMARY KEY AUTO_INCREMENT," +
    "organization_id VARCHAR(50) NOT NULL," +
    "street_number VARCHAR(50)," +
    "street_extension VARCHAR(50)," +
    "building VARCHAR(50)," +
    "street_name VARCHAR(255)," +
    "zipcode VARCHAR(50) NOT NULL," +
    "pobox VARCHAR(50)," +
    "city VARCHAR(255)," +
    "district VARCHAR(255)," +
    "phone TEXT[]," +
    "fax VARCHAR(50)," +
    "latitude VARCHAR(50)," +
    "longitude VARCHAR(50)," +
    "accessibility TEXT," +
    "FOREIGN KEY (organization_id) REFERENCES organizations(id) ON DELETE CASCADE" +
    ")"

  // Define SQL statement to insert organization
  val organizationInsertSql = "INSERT INTO organizations (id, acronym, name, description, email, web, schedule, theme, receptiontype, latitude, longitude, accessibility) " +
    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"

  // Define SQL statement to insert address
  val addressInsertSql = "INSERT INTO addresses (organization_id, street_number, street_extension, building, street_name, zipcode, pobox, city, district, phone, fax, latitude, longitude, accessibility) " +
    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"

  def main(args: Array[String]): Unit = {
    // Parse the XML file
    val xmlFile = "var.xml"
    val xmlData = XML.loadFile(xmlFile)

    // Connect to the database
    val url = "jdbc:mysql://localhost:3306/mydatabase"
    val username = "myusername"
    val password = "mypassword"
    var connection: Connection = null

    try {
      Class.forName("com.mysql.cj.jdbc.Driver")
      connection = DriverManager.getConnection(url, username, password)

      // Iterate over the organizations
      val organizations = xmlData \\ "organization"
      organizations.foreach(org => {
        // Extract the organization data
        val id = (org \ "id").text
        val acronym = (org \ "acronym").text
        val name = (org \ "name").text
        val description = (org \ "description").text
        val email = (org \ "email").text
        val web = (org \ "web").text
        val schedule = (org \ "schedule").text
        val receptiontype = (org \ "receptiontype").text
        val theme = (org \ "theme").map(_.text)

        // Insert the organization into the database
        val orgInsert = s"INSERT INTO organizations (id, acronym, name, description, email, web, schedule, theme, receptiontype) " +
          s"VALUES ('$id', '$acronym', '$name', '$description', '$email', '$web', '$schedule', '${theme.mkString(",")}', '$receptiontype')"
        val orgStatement = connection.createStatement()
        orgStatement.executeUpdate(orgInsert)

        // Extract the addresses
        val addresses = org \ "addresses" \ "address"
        addresses.foreach(addr => {
          // Extract the address data
          val streetNumber = (addr \ "street" \ "number").text
          val streetExtension = (addr \ "street" \ "extension").text
          val building = (addr \ "building").text
          val streetName = (addr \ "street" \ "name").text
          val zipcode = (addr \ "zipcode").text
          val pobox = (addr \ "pobox").text
          val city = (addr \ "city").text
          val district = (addr \ "district").text
          val phone = (addr \ "phone").map(_.text)
          val fax = (addr \ "fax").text
          val latitude = (addr \ "latitude").text
          val longitude = (addr \ "longitude").text
          val accessibility = (addr \ "accessibility").text

          // Insert the address into the database
          val addrInsert = s"INSERT INTO addresses (organization_id, street_number, street_extension, building, street_name, zipcode, pobox, city, district, phone, fax, latitude, longitude, accessibility) " +
            s"VALUES ('$id', '$streetNumber', '$streetExtension', '$building', '$streetName', '$zipcode', '$pobox', '$city', '$district', '${phone.mkString(",")}', '$fax', '$latitude', '$longitude', '$accessibility')"
          val addrStatement = connection.createStatement()
          addrStatement.executeUpdate(addrInsert)
        })
      })
      println("Data imported successfully!")
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      connection.close()
    }
  }

}
