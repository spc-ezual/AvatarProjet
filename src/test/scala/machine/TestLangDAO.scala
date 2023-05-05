package machine
import org.junit.Test
import org.junit.Assert._
import DB.LangDAO
import DB.LieuxXMLDAO
import Outils.AnalysePhrase
import application.AnalysePageObjet
import Outils.CreationDeRep

class TestLangDAO{

    @Test
    def Test1:Unit={
        assertEquals (LangDAO.getMotsLang(2),List("español","hola","buenos","dias","donde","esta","busco","buscando"))
        }
    @Test
     def Test2:Unit={
        assertEquals(LangDAO.getMotsLang(4),List("italiano", "buongiorno", "ciao", "salve", "buon", "pomeriggio", "buonasera", "incantato",  "dove", "trova", "cerco", "cercando"))    
    }

    @Test
    def Test3:Unit= {
        assertEquals(LangDAO.getMotsLang(3),List("deutsch", "hallo", "guten", "morgen", "tag", "abend", "wo", "ist", "suche", "suchen"))
    }

    @Test
    def TestPolitesse1:Unit={
        assertEquals(LangDAO.politesse(1),List("hi","hello","morning","evening","afternoon","hey"))
    }

    @Test
    def TestPolitesse2:Unit={
        assertEquals(LangDAO.politesse(4),List("buongiorno","ciao","salve","buon","pomeriggio","buonasera","incantato"))
    }

    @Test
    def TestPolitesse3:Unit={
        assertEquals(LangDAO.politesse(2),List("hola","buenos","dias"))  
    }

    @Test
    def TestRecherche1:Unit={
        assertEquals(LangDAO.recherche(0), List("recherche","cherche","ou","est","donc","trouve","trouver"))
    }

    @Test
    def TestRecherche2:Unit={
        assertEquals(LangDAO.recherche(3), List("wo","ist","suche","suchen"))
    }

    @Test
    def TestRecherche3:Unit={
        assertEquals(LangDAO.recherche(4), List("dove","trova","cerco","cercando"))
    }

    @Test
    def TestNom1:Unit={
        assertEquals(LangDAO.nom(2), "español")
    }

    @Test 
    def TestNom2:Unit={
        assertEquals(LangDAO.nom(3), "deutsch")
    }

     @Test 
    def TestNom3:Unit={
        assertEquals(LangDAO.nom(4), "italiano")
    } 

    @Test
    def TestVrai1:Unit={
        assertEquals(LangDAO.vrai(1), "yes")
    }

    @Test
    def TestVrai2:Unit={
        assertEquals(LangDAO.vrai(4), "si")
    }

    @Test
    def testFaux1:Unit={
        assertEquals(LangDAO.faux(4), "no")
    }

    @Test
    def TestFaux2:Unit={
        assertEquals(LangDAO.faux(0), "non")
    }

    @Test 
    def TestFaux3:Unit={
        assertEquals(LangDAO.faux(3), "nein")
    }

    @Test
    def TestreponseUni1:Unit={
        assertEquals(LangDAO.reponseUni(2), "La dirección de XXX es")
    }

    @Test
    def TestreponseUni2:Unit={
        assertEquals(LangDAO.reponseUni(1), "The address of XXX is")
    }

    @Test
    def TestreponseUni3:Unit={
        assertEquals(LangDAO.reponseUni(4), "Indirizzo di XXX è")
    }

    @Test
    def Testinconnu1:Unit={
        assertEquals(LangDAO.inconnu(3), "Ich verstehe nicht")
    }
     @Test
    def Testinconnu2:Unit={
        assertEquals(LangDAO.inconnu(0), "Je ne comprends pas votre demande")
    }

     @Test
    def Testinconnu3:Unit={
        assertEquals(LangDAO.inconnu(2), "No comprendo")
    }

    @Test
    def TestDemandeLang1:Unit={
        assertEquals(LangDAO.demandeLang(4), "Parli italiano?")
    }
    @Test
    def TestDemandeLang2:Unit={
        assertEquals(LangDAO.demandeLang(3), "Sprechen Sie Deutsch?")
    }

    @Test
    def TestDemandeLang3:Unit={
        assertEquals(LangDAO.demandeLang(1), "Do you speak english?")
    }

    @Test
    def TestdemandeChoix1:Unit={
        assertEquals(LangDAO.demandeChoix(2),"Cuál es su elección?" )
    }

     @Test
    def TestdemandeChoix2:Unit={
        assertEquals(LangDAO.demandeChoix(3),"Was ist Ihre Wahl?" )
    }

    @Test
    def TestdemandeChoix3:Unit={
        assertEquals(LangDAO.demandeChoix(0),"Quel est votre choix?" )
    }

    @Test 
    def TestMultReponse1:Unit={
        assertEquals(LangDAO.multReponse(4), "Ho XXX risposte" )
    }

    @Test 
    def TestMultReponse2:Unit={
        assertEquals(LangDAO.multReponse(1), "I found XXX answers" )
    }

    @Test 
    def TestMultReponse3:Unit={
        assertEquals(LangDAO.multReponse(0), "J'ai XXX réponses possibles" )
    }

    @Test
    def TestGetMotsRest1:Unit={
        assertEquals(LangDAO.getMotsRest(4), List("ristorante", "creperie", "pizzeria"))
    }

    @Test
    def TestGetMotsRest2:Unit={
        assertEquals(LangDAO.getMotsRest(0), List("restaurant", "creperie", "pizzeria"))
    }

     @Test
    def TestGetMotsRest3:Unit={
        assertEquals(LangDAO.getMotsRest(2), List("restaurante", "creperie", "pizzeria"))
    }
}

    
    
