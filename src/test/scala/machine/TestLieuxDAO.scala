package machine
import org.junit.Test
import org.junit.Assert._
import DB.LieuxXMLDAO
import DB.ListLieuxDAO

class TestLieuxDAO {

  @Test
  def Test_getAdresseId1: Unit = {
    assertEquals(LieuxXMLDAO.getAdresseId("2024"), Some("2, Rue Emile Desprès"))
  }

  @Test
  def Test_getAdresseId2: Unit = {
    assertEquals(LieuxXMLDAO.getAdresseId("0"), None)
  }

  @Test
  def Test_getAdresseId3: Unit = {
    assertEquals(
      LieuxXMLDAO.getAdresseId("1"),
      Some("4, Rue Abbe Huet,rue Abbe Huet")
    )
  }

}
