package machine
import org.junit.Test
import org.junit.Assert._
import Outils.Correction

class TestCorrection {

  @Test
  def testHammingDistance1 {
    assertEquals(
      0,
      Correction.hammingDistance("a","a")
    )
  }
  @Test
  def testHammingDistance2 {
    try{
        Correction.hammingDistance("aaaa","a");
        fail();
    }catch{
      
    }
  }
}
