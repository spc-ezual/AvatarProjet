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

  
}
