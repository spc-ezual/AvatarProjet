package machine
import org.junit.Test
import org.junit.Assert._
import DB.LangDAO
import DB.LieuxXMLDAO
import Outils.AnalysePhrase
import application.AnalysePageObjet

class TestLangDAO{

    @Test
    def Test1:Unit={
        assertEquals (LangDAO.getMotsLang(2),List("español","hola","buenos","dias","donde","esta","busco","buscando"))
        }
    @Test
    def Test2:Unit={
        assertEquals(LieuxXMLDAO.getAdresseId("2024"),Some("2, Rue Emile Desprès"))
    }
    @Test
    def Test3:Unit={
        assertEquals(
            List(),LangDAO.getMotsRest(0)
        )
    }
    @Test 
    def Test4:Unit={
        assertEquals(AnalysePageObjet.getNomAdres(List("Atelier","des")),Some(("La Tomate","18, rue Saint Georges")))
    }
    }
    