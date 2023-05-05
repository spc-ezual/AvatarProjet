package machine
import org.junit.Test
import org.junit.Assert._
import Outils.AnalysePhrase
import scala.collection.View

class TestAnalysePhrase {

  @Test
  def testSepMots_base: Unit = {
    assertEquals(
      List("bonjour", "je", "suis", "le", "v", "aaa"),
      AnalysePhrase.SepMots("bonjour je suis le v aaa")
    )
  }
  @Test
  def testSepMots_virg: Unit = {
    assertEquals(
      List("bonjour", "je", "suis", "le", "v", "aaa"),
      AnalysePhrase.SepMots("bonjour, je suis le v, aaa")
    )
  }
  @Test
  def testSepMots_2space: Unit = {
    assertEquals(
      List("bonjour", "je", "suis", "le", "v", "aaa"),
      AnalysePhrase.SepMots("bonjour je suis le v   aaa")
    )
  }
  @Test
  def testSepMots_apo: Unit = {
    assertEquals(
      List("salut", "je", "suis", "l", "horloge"),
      AnalysePhrase.SepMots("salut je suis l'horloge")
    )
  }
  @Test
  def testSepMots_et: Unit = {
    assertEquals(
      List("salut", "je", "suis", "l", "horloge"),
      AnalysePhrase.SepMots("salut je suis l&horloge")
    )
  }
  @Test
  def testSepMots_div: Unit = {
    assertEquals(
      List("salut", "je", "suis", "l", "horloge"),
      AnalysePhrase.SepMots("salut je suis l/horloge")
    )
  }
  @Test
  def testSepMots_poV: Unit = {
    assertEquals(
      List("salut", "je", "suis", "l", "horloge"),
      AnalysePhrase.SepMots("salut je suis l;horloge")
    )
  }
  @Test
  def testSepMots_excl: Unit = {
    assertEquals(
      List("salut", "je", "suis", "l", "horloge"),
      AnalysePhrase.SepMots("salut je suis l!horloge")
    )
  }
  @Test
  def testSepMots_hac: Unit = {
    assertEquals(
      List("salut", "je", "suis", "l", "horloge"),
      AnalysePhrase.SepMots("salut je suis l#horloge")
    )
  }
  @Test
  def testSepMots_add: Unit = {
    assertEquals(
      List("salut", "je", "suis", "l", "horloge"),
      AnalysePhrase.SepMots("salut je suis l+horloge")
    )
  }

  @Test
  def testCompartList: Unit = {
    assertEquals(
      AnalysePhrase.compartList(
        List("mairie", "gare"),
        List("hello", "where", "is", "gare")
      ),
      false
    )
  }

  @Test
  def testCompartList2: Unit = {
    assertEquals(
      AnalysePhrase.compartList(
        List("gare", "mairie", "SNCF"),
        List("hello", "where", "is", "gare")
      ),
      false
    )
  }

  @Test
  def testCompartList3: Unit = {
    assertEquals(
      AnalysePhrase.compartList(
        List("gare", "SNCF"),
        List("hello", "where", "is", "gare", "SNCF")
      ),
      true
    )
  }

  @Test
  def testAreEqualIgnoreCaseAndAccents: Unit = {
    assertEquals(
      AnalysePhrase.areEqualIgnoreCaseAndAccents(
        'a',
        'b'
      ),
      false
    )
  }

  @Test
  def testAreEqualIgnoreCaseAndAccents2: Unit = {
    assertEquals(
      AnalysePhrase.areEqualIgnoreCaseAndAccents(
        'a',
        'a'
      ),
      true
    )
  }

  @Test
  def testRemoveWords: Unit = {
    assertEquals(
      AnalysePhrase.removeWords(
        List("hello", "where", "is", "gare"),
        List("gare")
      ),
      List("hello", "where", "is")
    )
  }

  @Test
  def testRemoveWords2: Unit = {
    assertEquals(
      AnalysePhrase.removeWords(
        List("hello", "where", "is", "gare"),
        List()
      ),
      List("hello", "where", "is", "gare")
    )
  }

  @Test
  def testRemoveWords3: Unit = {
    assertEquals(
      AnalysePhrase.removeWords(
        List("hello", "where", "is", "gare"),
        List("hello", "where", "is", "gare")
      ),
      List()
    )
  }

  @Test
  def testVerifLang_En: Unit = {
    assertEquals(1, AnalysePhrase.VerifLang(3, List("hello", "where")))
  }
  @Test
  def testVerifLang_Fr: Unit = {
    assertEquals(0, AnalysePhrase.VerifLang(3, List("bonjour", "wherrzere")))
  }
  @Test
  def testVerifLang_Es: Unit = {
    assertEquals(2, AnalysePhrase.VerifLang(4, List("esta", "rez")))
  }
  @Test
  def testVerifLang_It: Unit = {
    assertEquals(4, AnalysePhrase.VerifLang(1, List("italiano", "bua")))
  }
  @Test
  def testVerifLang_Al: Unit = {
    assertEquals(3, AnalysePhrase.VerifLang(1, List("suchen", "ahah")))
  }

  @Test
  def testVerifLangCorrect_En: Unit = {
    assertEquals(1, AnalysePhrase.VerifLang(3, List("hello", "where")))
  }
  @Test
  def testVerifLangCorrect_Fr: Unit = {
    assertEquals(0, AnalysePhrase.VerifLang(3, List("bonjour", "wherrzere")))
  }
  @Test
  def testVerifLangCorrect_Es: Unit = {
    assertEquals(2, AnalysePhrase.VerifLang(4, List("esta", "rez")))
  }
  @Test
  def testVerifLangCorrect_It: Unit = {
    assertEquals(4, AnalysePhrase.VerifLang(1, List("italiano", "bua")))
  }
  @Test
  def testVerifLangCorrect_Al: Unit = {
    assertEquals(3, AnalysePhrase.VerifLang(1, List("suchen", "ahah")))
  }

  @Test
  def testPremierNombreDansString: Unit = {
    assertEquals(Some(1), AnalysePhrase.premierNombreDansString("bla1bla"))
  }
  @Test
  def testPremierNombreDansString2: Unit = {
    assertEquals(Some(2), AnalysePhrase.premierNombreDansString("bla2bla"))
  }
  @Test
  def testPremierNombreDansString3: Unit = {
    assertEquals(Some(3), AnalysePhrase.premierNombreDansString("blabla3"))
  }
  @Test
  def testPremierNombreDansString4: Unit = {
    assertEquals(None, AnalysePhrase.premierNombreDansString("blabla"))
  }

  @Test
  def testRechercheResto: Unit = {
    assertEquals(
      None,
      AnalysePhrase.rechercheResto(
        List("hello", "where"),
        List("Tomate", "BK")
      )
    )
  }
  @Test
  def testRechercheResto2: Unit = {
    assertEquals(
      Some(List()),
      AnalysePhrase.rechercheResto(
        List("hello", "where", "is", "Tomate"),
        List("Tomate", "BK")
      )
    )
  }
  @Test
  def testRechercheResto3: Unit = {
    assertEquals(
      None,
      AnalysePhrase.rechercheResto(
        List("hello", "where"),
        List("")
      )
    )
  }
  @Test
  def testRechercheResto4: Unit = {
    assertEquals(
      None,
      AnalysePhrase.rechercheResto(
        List(),
        List("Tomate")
      )
    )
  }
  @Test
  def testRechercheResto5: Unit = {
    assertEquals(
      Some(List()),
      AnalysePhrase.rechercheResto(
        List("hello", "where", "is", "Tomatte"),
        List("Tomate")
      )
    )
  }

  @Test
  def testReplaceHtmlEntities: Unit = {
    assertEquals(
      "Hello where is tomate",
      AnalysePhrase.replaceHtmlEntities(
        "Hello where is tomate"
      )
    )
  }
  @Test
  def testReplaceHtmlEntities2: Unit = {
    assertEquals(
      "",
      AnalysePhrase.replaceHtmlEntities(
        ""
      )
    )
  }

  @Test
  def testReplaceHtmlEntities3: Unit = {
    assertNotEquals(
      "Hello",
      AnalysePhrase.replaceHtmlEntities(
        "Hello where is tomate"
      )
    )
  }
}
