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

  @Test
  def Test_getAdresseId4: Unit = {
    assertEquals(
      LieuxXMLDAO.getAdresseId("2114"),
      None
    )
  }

  @Test
  def Test_getAdresseId5: Unit = {
    assertEquals(
      LieuxXMLDAO.getAdresseId("2115"),
      Some("1, Avenue Saint Vincent")
    )
  }

  @Test
  def Test_getId1: Unit = {
    assertEquals(
      LieuxXMLDAO.getId(List("Université Rennes 3")),
      None
    )
  }

  @Test
  def Test_getId2: Unit = {
    assertEquals(LieuxXMLDAO.getId(List("TVR")), Some(List("1173")))
  }

  @Test
  def Test_getId3: Unit = {
    assertEquals(
      LieuxXMLDAO.getId(List("Tennis", "Gayeulles")),
      Some(List("515"))
    )
  }

  @Test
  def Test_getId4: Unit = {
    assertEquals(
      LieuxXMLDAO.getId(List("Tennis")),
      Some(List("1114", "1451", "32", "433", "515"))
    )
  }

  @Test
  def Test_getNameId1: Unit = {
    assertEquals(
      LieuxXMLDAO.getNameId("2114"),
      Some("Dor Breizh Info-Trafic agglo")
    )
  }

  @Test
  def Test_getNameId2: Unit = {
    assertEquals(
      LieuxXMLDAO.getNameId("0"),
      None
    )
  }

  @Test
  def Test_getNameId3: Unit = {
    assertEquals(
      LieuxXMLDAO.getNameId("1355"),
      Some("Université de Rennes 1")
    )
  }

}
