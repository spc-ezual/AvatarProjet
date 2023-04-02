package machine
import org.junit.Test
import org.junit.Assert._
import DB.LangDAO

class TestLangDAO{

    @Test
    def Test1{
        assertEquals (LangDAO.getMotsLang(2),List("español","hola","buenos","dias","donde","esta","busco","buscando"))
        }
    }
