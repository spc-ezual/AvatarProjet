package machine

import org.junit.Test
import org.junit.Assert._
import Outils.CreationListMotClef._
import DB.ListLieuxDAO

class TestCreationListMotClef {
    //Objet qui donne acces a la base de donné des lieux
    val dsbLieux = ListLieuxDAO
    //Objet qui donne acces a la base de donné des politesses
    @Test
    def testRecupPoli_1ele {
    assertEquals(
        "Bonjour ",RecupPoli(List("Bonjour"))
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
        "Bonjour ",RecupPoli(List("Bonjour","salsa"))
        )
    }
    
/*
    @Test
    def testRecupLieux_1ele {
    assertEquals(
        List(("Mairie",dsbLieux.getAdresse("Mairie").get)),RecupLieux("Mairie")
        )
    }
    @Test
    def testRecupLieux_1ele2 {
    assertEquals(
        Nil,RecupLieux("haha")
        )
    }
    @Test
    def testRecupLieux_1ele3 {
    assertEquals(
        "",RecupLieux("bonjours")
        )
    }
    @Test
    def testRecupLieux_0ele {
    assertEquals(
        "",RecupLieux("")
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
    
*/    
}
