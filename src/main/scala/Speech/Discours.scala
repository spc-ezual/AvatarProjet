package Speech

import marytts.LocalMaryInterface
import java.util.Locale
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.AudioInputStream

object Discours {
  val langues = List(Locale.FRENCH, Locale.US, null, Locale.GERMAN, Locale.ITALIAN)
  val mary = new LocalMaryInterface()

  def generateDiscours(text: String, langIndex: Int): Unit = {
    if(langIndex!=2){
    mary.setLocale(langues(langIndex))
    //mary.setVoice("dfki-spike-hsmm")

    val audio = mary.generateAudio(text)
    val clip = AudioSystem.getClip()
    clip.open(audio)
    clip.start()}
  }
}
