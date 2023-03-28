package machine
import org.junit.Test
import org.junit.Assert._
import  Outils.AnalysePhrase

class TestSepMots {

    @Test
    def testSepMots_base {
    assertEquals(
        List("bonjour", "je", "suis", "le", "v", "aaa"),AnalysePhrase.SepMots("bonjour je suis le v aaa")
    )
    }
    @Test
    def testSepMots_virg {
    assertEquals(
        List("bonjour", "je", "suis", "le", "v", "aaa"),AnalysePhrase.SepMots("bonjour, je suis le v, aaa")
    )
    }
    @Test
    def testSepMots_2space {
    assertEquals(
        List("bonjour", "je", "suis", "le", "v", "aaa"),AnalysePhrase.SepMots("bonjour je suis le v   aaa")
    )
    }
    @Test
    def testSepMots_apo {
    assertEquals(
        List("salut","je","suis","l'","horloge"),AnalysePhrase.SepMots("salut je suis l'horloge")
    )
    }
    @Test
    def testSepMots_et {
    assertEquals(
        List("salut","je","suis","l","horloge"),AnalysePhrase.SepMots("salut je suis l&horloge")
    )
    }
    @Test
    def testSepMots_div {
    assertEquals(
        List("salut","je","suis","l","horloge"),AnalysePhrase.SepMots("salut je suis l/horloge")
    )
    }
    @Test
    def testSepMots_poV {
    assertEquals(
        List("salut","je","suis","l","horloge"),AnalysePhrase.SepMots("salut je suis l;horloge")
    )
    }
    @Test
    def testSepMots_excl {
    assertEquals(
        List("salut","je","suis","l","horloge"),AnalysePhrase.SepMots("salut je suis l!horloge")
    )
    }
    @Test
    def testSepMots_hac {
    assertEquals(
        List("salut","je","suis","l","horloge"),AnalysePhrase.SepMots("salut je suis l#horloge")
    )
    }
    @Test
    def testSepMots_add {
    assertEquals(
        List("salut","je","suis","l","horloge"),AnalysePhrase.SepMots("salut je suis l+horloge")
    )
    }
}
