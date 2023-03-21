package machine
import org.junit.Test
import org.junit.Assert._
import Outils.Correction

class TestCorrection {

  @Test
  def testHammingDistance {
    assertEquals(
      0,
      Correction.hammingDistance("a","a")
    )
  }
}
