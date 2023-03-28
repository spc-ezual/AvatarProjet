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
      None,
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
  def testCorrcteMot1 {
    assertEquals(
      "mairie",
      correctMot("MAIRIE", List("mairie", "leo ma petite soeur", "a"))
    )
  }

  @Test
  def testCorrcteMot2 {
    assertEquals(
      "MAIRIE",
      correctMot("MAIRIE", List("mairieee", "leo ma petite soeur", "a"))
    )
  }

  @Test
  def testCorrcteMot3 {
    assertEquals(
      "MAIRIE",
      correctMot("MAIRIE", List())
    )
  }

  @Test
  def testCorrct1{
    assertEquals(
      List("bonjour","ou","est","la","Mairie"),
      correct(List("bonjour","ou","est","la","mairie"), List("Mairie"))
    )
  }
}
