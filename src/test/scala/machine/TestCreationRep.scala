package machine
import org.junit.Test
import org.junit.Assert._
import Outils.CreationListMotClef._
import DB.ListLieuxDAO
import Outils.CreationDeRep

class TestCreationRep {
  @Test
    //Test getLangue
  def test_get_lang_1 {
    CreationDeRep.langues = 1
    assertEquals(
      1,
      CreationDeRep.getLangue()
    )
  }
  @Test
    //Test getLangue
  def test_get_lang_2 {
    CreationDeRep.langues = 2
    assertEquals(
      2,
      CreationDeRep.getLangue()
    )
  }
  @Test
    //Test changement de langue
  def test_lang_1 {
    CreationDeRep.action = 0
    CreationDeRep.langues = 0
    assertEquals(
      List("Hablas español?"),
      CreationDeRep.Reponse("donde esta")
    )
  }
  @Test
    //Test changement de langue quand il attends un lieu
  def test_lang_2 {
    CreationDeRep.action = 1
    CreationDeRep.langues = 0
    assertEquals(
      List("Hablas español?"),
      CreationDeRep.Reponse("donde esta")
    )
  }

  //Série de tests pour le Français

  @Test
    //Test avec uniquement bonjour sans autre requête
  def test_politesse_nil {
    CreationDeRep.action = 1
    CreationDeRep.langues = 0
    assertEquals(
      List("Bonjour"),
      CreationDeRep.Reponse("Bonjour")
    )
  }
  @Test
    //Test avec uniquement salut sans autre requête
  def test_politesse_nil_2 {
    CreationDeRep.action = 1
    CreationDeRep.langues = 0
    assertEquals(
      List("Bonjour"),
      CreationDeRep.Reponse("Salut")
    )
  }

  @Test
  def test_nil {
    CreationDeRep.action = 1
    CreationDeRep.langues = 0
    assertEquals(
      List("Je ne comprends pas votre demande"),
      CreationDeRep.Reponse("")
    )
  }

  @Test
    //Test de requête sans politesse
  def test_requete {
    CreationDeRep.action = 1
    CreationDeRep.langues = 0
    assertEquals(
      List("L'adresse de Mairie de Rennes est : Place de la Mairie"),
      CreationDeRep.Reponse("Mairie de Rennes")
    )
  }

  @Test
    //Test avec requête et politesse
  def test_politesse_et_requete {
    CreationDeRep.action = 1
    CreationDeRep.langues = 0
    assertEquals(
      List("Bonjour","L'adresse de Mairie de Rennes est : Place de la Mairie"),
      CreationDeRep.Reponse("Bonjour Mairie de Rennes")
    )
  }


@Test
    //Test avec une autre requête et politesse
  def test_politesse_et_requete_2 {
    CreationDeRep.action = 1
    CreationDeRep.langues = 0
    assertEquals(
      List("Bonjour","L'adresse de Théâtre National de Bretagne est : 1, Rue Saint-Hélier"),
      CreationDeRep.Reponse("Bonjour, je voudrais l'adresse du Théâtre National de Bretagne")
    )
  }
  @Test
    // Test avec une autre requête et politesse
  def test_politesse_et_requete_introuvab {
    CreationDeRep.action = 1
    CreationDeRep.langues = 0
    assertEquals(
      List("Bonjour", "Je ne comprends pas votre demande"),
      CreationDeRep.Reponse("Bonjour, 647hdc_8")
)
}
}