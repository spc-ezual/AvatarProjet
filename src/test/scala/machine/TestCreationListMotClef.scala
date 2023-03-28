package machine

import org.junit.Test
import org.junit.Assert._
import Outils.CreationListMotClef._
import DB.ListLieuxDAO
import DB.PolitesseDAO

class TestCreationListMotClef {
    //Objet qui donne acces a la base de donné des lieux
    val dsbLieux = ListLieuxDAO
    //Objet qui donne acces a la base de donné des politesses
    val dsbPolitesse = PolitesseDAO
    @Test
    def testRecupPoli_1ele {
    assertEquals(
        dsbPolitesse.getReponse("Bonjour").get,RecupPoli(List("Bonjour"))
        )
    }
    @Test
    def testRecupPoli_1ele2 {
    assertEquals(
        "",RecupPoli(List("haha"))
        )
    }
    @Test
    def testRecupPoli_1ele3 {
    assertEquals(
        "",RecupPoli(List("bonjours"))
        )
    }
    @Test
    def testRecupPoli_0ele {
    assertEquals(
        "",RecupPoli(List(""))
        )
    }
    @Test
    def testRecupPoli_nele1 {
    assertEquals(
        dsbPolitesse.getReponse("Bonjour").get,RecupPoli(List("Bonjour","salsa"))
        )
    }
    @Test
    def testRecupPoli_nele2 {
    assertEquals(
        dsbPolitesse.getReponse("Bonjour").get,RecupPoli(List("bingo","Bonjour","nonnnn"))
        )
    }
    @Test
    def testRecupPoli_nele3 {
    assertEquals(
        dsbPolitesse.getReponse("Bonjour").get+" "+dsbPolitesse.getReponse("Bonjour").get,RecupPoli(List("Bonjour","bonsssssoir","Bonjour"))
        )
    }
    

    @Test
    def testRecupLieux_1ele {
    assertEquals(
        List(("Mairie",dsbLieux.getAdresse("Mairie").get)),RecupLieux(List("Mairie"))
        )
    }
    @Test
    def testRecupLieux_1ele2 {
    assertEquals(
        Nil,RecupLieux(List("haha"))
        )
    }
    @Test
    def testRecupLieux_1ele3 {
    assertEquals(
        Nil,RecupLieux(List("bonjours"))
        )
    }
    @Test
    def testRecupLieux_0ele {
    assertEquals(
        Nil,RecupLieux(List(""))
        )
    }
    @Test
    def testRecupLieux_nele1 {
    assertEquals(
        Nil,RecupLieux(List("Bonjour","salsa"))
        )
    }
    @Test
    def testRecupLieux_nele2 {
    assertEquals(
        List(("Mairie",dsbLieux.getAdresse("Mairie").get),("TNB",dsbLieux.getAdresse("TNB").get)),RecupLieux(List("Mairie","TNB","nonnnn"))
        )
    }
    @Test
    def testRecupLieux_nele3 {
    assertEquals(
        List(("Mairie",dsbLieux.getAdresse("Mairie").get),("TNB",dsbLieux.getAdresse("TNB").get),("Mairie",dsbLieux.getAdresse("Mairie").get)),RecupLieux(List("Mairie","hah","TNB","Mairie","bonjour"))
        )
    }
    
    
}
