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
        List("salut","je","suis","l","horloge"),AnalysePhrase.SepMots("salut je suis l'horloge")
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
    @Test 
    def testVerifLang_En{
        assertEquals(1,AnalysePhrase.VerifLang(3,List("hello","where")))
    }
    @Test 
    def testVerifLang_Fr{
        assertEquals(0,AnalysePhrase.VerifLang(3,List("bonjour","wherrzere")))
    }
    @Test 
    def testVerifLang_Es{
        assertEquals(2,AnalysePhrase.VerifLang(4,List("esta","rez")))
    }
    @Test 
    def testVerifLang_It{
        assertEquals(4,AnalysePhrase.VerifLang(1,List("italiano","bua")))
    }
    @Test 
    def testVerifLang_Al{
        assertEquals(3,AnalysePhrase.VerifLang(1,List("suchen","ahah")))
    }
}
