package Speech

import marytts.LocalMaryInterface
import java.util.Locale
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.AudioInputStream

object Discours {
  val langues = List(Locale.FRENCH, Locale.US, null, Locale.GERMAN, Locale.ITALIAN)
  val mary = new LocalMaryInterface()

  def generateDiscours(text: String, langIndex: Int): Unit = {
    try{
    if(langIndex!=2){
    mary.setLocale(langues(langIndex))
    //mary.setVoice("dfki-spike-hsmm")
      val texte_modif = text//.replaceAll("[-‐‑‒–—―]", "")
    val audio = mary.generateAudio(texte_modif)
    val clip = AudioSystem.getClip()
    clip.open(audio)
    clip.start()}
  }catch{
    case _ : Throwable=> println("Une erreur es survenue lors de la synthese vocal")
  }

}
}
