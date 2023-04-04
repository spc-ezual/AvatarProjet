package machine
import org.junit.Test
import org.junit.Assert._
import Outils.CreationListMotClef._
import DB.ListLieuxDAO
import Outils.CreationDeRep

class TestCreationRep {
  //Jeu de test FR
    @Test
    //Test avec uniquement bonjour sans autre requête
  def test_politesse_nil {
    assertEquals(
      List("Bonjour"),
      CreationDeRep.Reponse("Bonjour")
    )
  }
    @Test
    //Test avec uniquement salut sans autre requête
  def test_politesse_nil_2 {
    assertEquals(
      List("Bonjour"),
      CreationDeRep.Reponse("Salut")
    )
  }

  @Test
  def test_nil {
    assertEquals(
      List("Je ne comprends pas votre demande"),
      CreationDeRep.Reponse("")
    )
  }

  @Test
    //Test de requête sans politesse
  def test_requete {
    assertEquals(
      List("L'adresse de Mairie de Rennes est : Place de la Mairie"),
      CreationDeRep.Reponse("Mairie de Rennes")
    )
  }

  @Test
    //Test avec requête et politesse
  def test_politesse_et_requete {
    assertEquals(
      List("Bonjour","L'adresse de Mairie de Rennes est : Place de la Mairie"),
      CreationDeRep.Reponse("Bonjour Mairie de Rennes")
    )
  }


@Test
    //Test avec une autre requête et politesse
  def test_politesse_et_requete_2 {
    assertEquals(
      List("Bonjour","L'adresse de Théâtre National de Bretagne est : 1, Rue Saint-Hélier"),
      CreationDeRep.Reponse("Bonjour, je voudrais l'adresse du Théâtre National de Bretagne")
    )
  }
}