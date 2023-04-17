package machine

import Outils.CreationDeRep
import application.Avatar
import application.AvatarV2

object MachineImpl extends MachineDialogue {
  def ask(s: String): List[String] = CreationDeRep.Reponse(s)

  // Pour la partie test par le client
  def reinit(): Unit = {
    Avatar.reinit()
    CreationDeRep.reInit
  }
  def test(l: List[String]): List[String] = {
    var rep = List() : List[String]
    for( i <- l){
      for(j <- ask(i)){
        rep = rep ++ List(j)
      }
    }
    rep
  }
}