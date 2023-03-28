package machine

import Outils.CreationDeRep
import application.UI

object MachineImpl extends MachineDialogue {
  def ask(s: String): List[String] = CreationDeRep.Reponse(s)

  // Pour la partie test par le client
  def reinit(): Unit = {}
  def test(l: List[String]): List[String] = {
    var rep = Nil
    for( i <- l){
       rep :: ask(i)
    }
    rep
  }
}