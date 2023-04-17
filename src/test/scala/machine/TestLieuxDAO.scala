package machine
import org.junit.Test
import org.junit.Assert._
import DB.LieuxXMLDAO
import DB.ListLieuxDAO
import Outils.CreationDeRep

class TestLieuxDAO{

    
    @Test
    def Test:Unit={
        assertEquals(CreationDeRep.Reponse("santé" +
          ""),List())
    }
    
} 