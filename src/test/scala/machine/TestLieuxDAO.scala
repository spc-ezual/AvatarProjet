package machine
import org.junit.Test
import org.junit.Assert._
import DB.LieuxXMLDAO
import DB.ListLieuxDAO

class TestLieuxDAO{

    
    @Test
    def Test:Unit={
        assertEquals(LieuxXMLDAO.getAdresseId("2024"),Some("2, Rue Emile Desprès"))
    }
    
} 