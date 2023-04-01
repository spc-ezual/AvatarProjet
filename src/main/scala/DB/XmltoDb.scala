package DB

import  java.sql.{Connection, DriverManager}
import java.io.File
import scala.xml.{Elem, Node, XML}




object XmlToSql {
  
  def main(args: Array[String]): Unit = {
    // Parse the XML file
    val xmlFile = "BaseDeDonnees/vAr.xml"
    val xmlData = XML.loadFile(xmlFile)

    System.out.println("Connecter au fichier xml")
    // Connect to the database
    val dbFilePath = "BaseDeDonnees/DataLieuxXML.db"
    // Vérification que le fichier existe
    if (!new File(dbFilePath).exists() || !new File(dbFilePath).isFile()) {
        throw new RuntimeException("Le fichier de base de données n'existe pas ou n'est pas un fichier valide.")
    }

    val connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath)

    System.out.println("Connecter au fichier db")
    connection.createStatement().execute("DROP TABLE organizations")
    connection.createStatement().execute("CREATE TABLE organizations (id VARCHAR(50) PRIMARY KEY,acronym VARCHAR(50),name VARCHAR(255) NOT NULL,description TEXT,email VARCHAR(255),web VARCHAR(255),schedule TEXT,theme VARCHAR(255) NOT NULL,receptiontype VARCHAR(255));")
    try {

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
        
    System.out.println("Fin recup orga")

        // Insert the organization into the database
        val orgInsert = s"INSERT INTO organizations (id, acronym, name, description, email, web, schedule, theme, receptiontype) " +
          s"VALUES ('$id', '$acronym', '$name', '$description', '$email', '$web', '$schedule', '${theme.mkString(",")}', '$receptiontype')"
        val orgStatement = connection.createStatement()
        System.out.print(orgInsert)
        orgStatement.executeUpdate(orgInsert)

    System.out.println("fin insert orga")
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

    System.out.println("fin recup add")
          // Insert the address into the database
          val addrInsert = s"INSERT INTO addresses (organization_id, street_number, street_extension, building, street_name, zipcode, pobox, city, district, phone, fax, latitude, longitude, accessibility) " +
            s"VALUES ('$id', '$streetNumber', '$streetExtension', '$building', '$streetName', '$zipcode', '$pobox', '$city', '$district', '${phone.mkString(",")}', '$fax', '$latitude', '$longitude', '$accessibility')"
          val addrStatement = connection.createStatement()
          addrStatement.executeUpdate(addrInsert)

    System.out.println("fin insert add")
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
