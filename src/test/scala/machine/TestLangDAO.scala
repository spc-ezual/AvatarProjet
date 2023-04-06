package machine
import org.junit.Test
import org.junit.Assert._
import DB.LangDAO
import DB.LieuxXMLDAO

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
        assertEquals(LieuxXMLDAO.listeMotsUniques(),List())
    }
    }
    