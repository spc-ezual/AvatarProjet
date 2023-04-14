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
    }
    