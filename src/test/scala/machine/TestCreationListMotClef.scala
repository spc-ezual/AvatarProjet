package machine

import org.junit.Test
import org.junit.Assert._
import Outils.CreationListMotClef._
import DB.ListLieuxDAO
import DB.LangDAO

class TestCreationListMotClef {
    //Objet qui donne acces a la base de donné des lieux
    val dsbLieux = ListLieuxDAO

    //Objet qui donne acces a la base de donné des politesses
    val dsbPolitesse = LangDAO

    //Requetes d'un etablissement précis

    @Test
    def testMatchMotClef_base_fr_1 {
    assertEquals(MatchMotClef(List("Cinéma","Gaumont"),0),
    (0,List(("Cinéma Gaumont Rennes",", Esplanade General De Gaulle"))))
    }

    @Test
    def testMatchMotClef_complexes_fr_2 {
    assertEquals(MatchMotClef(List("Piscine","Gayeulles"),0),
    (0,List(("Piscine Gayeulles","16, Avenue Des Gayeulles,avenue Des Gayeulles"))))
    }


    //tests sur des requetes vagues en français
    //plusieurs résultat possibles

    @Test
    def testMatchMotClef_solo_fr_1 {
    assertEquals(MatchMotClef(List("Cinéma"),0),
    (0,List(("Cinéma Arvor","29, Rue D'antrain,rue D'antrain"), ("Cinéma Gaumont Rennes",", Esplanade General De Gaulle"))))
    }


    @Test
    def testMatchMotClef_solo_fr_2 {
    assertEquals(MatchMotClef(List("Piscine"),0),
    (0,List(("Piscine Villejean","1, Square D'alsace"), ("Piscine Gayeulles","16, Avenue Des Gayeulles,avenue Des Gayeulles"),
    ("Piscine Saint-Georges","4, Rue Gambetta"), ("Piscine Bréquigny","10, Boulevard Albert 1er"))))
    }
    
    //Tests requetes de plusieurs établissements à la fois en français

    @Test
    def testMatchMotClef_multiples_fr_1 {
    assertEquals(MatchMotClef(List("Mairie","Gare"),0),
    (0,List(("Mairie de Rennes","Place de la Mairie"), ("Gare SNCF","19, Place de la Gare"))))
    }

    @Test
    def testMatchMotClef_multiples_fr_2 {
    assertEquals(MatchMotClef(List("Mairie","Gare","TNB"),0),
    (0,List(("Mairie de Rennes","Place de la Mairie"),("Théâtre National de Bretagne","1, Rue Saint-Hélier"),("Gare SNCF","19, Place de la Gare"))))
    }

    //Test des politesses

        @Test
    def testMatchMotClef_fr_poli_1 {
    assertEquals(MatchMotClef(List("Bonjour","Bonjour"),0),
    (2,List()))
    }


    @Test
    def testMatchMotClef_fr_poli_2 {
    assertEquals(MatchMotClef(List("Bonjour","Bonjour","Bonjour"),0),
    (3,List()))
    }
}
