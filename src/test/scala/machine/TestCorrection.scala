package machine
import org.junit.Test
import org.junit.Assert._
import Outils.Correction._
import Outils.LongDiffException

class TestCorrection {
  @Test
  def testHammingDistance1 {
    assertEquals(
      0,
      hammingDistance("a","a")
    )
  }
  @Test
  def testHammingDistance2 {
    assertEquals(
      0,
      hammingDistance("Màirie","mairie")
    )
  }
  @Test
  def testHammingDistance3 {
    assertEquals(
      1,
      hammingDistance("Mairie","mairee")
    )
  }
  @Test
  def testHammingDistance4 {
    assertEquals(
      1,
      hammingDistance("Mairie","marrie")
    )
  }
  @Test
  def testHammingDistance5 {
    try {
      hammingDistance("Mairie","marriee")
      fail()
    } catch {
      case e: LongDiffException => assert(e.getMessage == "Les chaînes doivent être de même longueur.")
      case _: Throwable => fail()
    }
  }

  @Test
  def testCompartMot1 {
    assertEquals(
      Some("mairie"),
      compartMot("Màirie","mairie")
    )
  }
  
  @Test
  def testCompartMot2 {
    assertEquals(
      Some("mairiee"),
      compartMot("Màirie","mairiee")
    )
  }

  @Test
  def testCompartMot3 {
    assertEquals(
      None,
      compartMot("","mairie")
    )
  }

  @Test
  def testCompartMot4 {
    assertEquals(
      Some("Mairie"),
      compartMot("mAIRIE","Mairie")
    )
  }

  @Test
  def testCorrectMot1 {
    assertEquals(
      "mairie",
      correctMot("MAIRIE", List("mairie", "gare", "arbre"))
    )
  }

  @Test
  def testCorrectMot2 {
    assertEquals(
      "",
      correctMot("MAIRIE", List("mairieee", "gare", "arbre"))
    )
  }

  @Test
  def testCorrectMot3 {
    assertEquals(
      "",
      correctMot("MAIRIE", List())
    )
  }

  @Test
  def testCorrectMot4 {
    assertEquals(
      "gare",
      correctMot("gaare", List("mairieee", "gare", "arbre"))
    )
  }

  @Test
  def testCorrect1{
    assertEquals(
      List("Mairie"),
      correct(List("bonjour","ou","est","la","mairie"), List("Mairie"),1)._1
    )
  }

  @Test
  def testCorrect2{
    assertEquals(
      List("Maarie"),
      correct(List("bonjour","ou","est","la","mairie"), List("Maarie"),1)._1
    )
  }

  @Test
  def testCorrect3{
    assertEquals(
      List("Mairie", "Gare"),
      correct(List("bonjour","ou","est","la","maarie", "la", "gaare"), List("Mairie", "Gare"),1)._1
    )
  }

  @Test
  def testCorrect4{
    assertEquals(
      List(),
      correct(List("bonjour","ou","est","la","maarie", "la", "gaare"), List(),1)._1
    )
  }

  @Test
  def testCorrect5{
    assertEquals(
      List(),
      correct(List(), List("Mairie", "Gare"),1)._1
    )
  }
}
