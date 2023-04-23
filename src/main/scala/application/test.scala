package application

import Speech.Discours
import Outils.CreationDeRep._
import java.text.Normalizer

object  test {
    def main(a : Array[String]): Unit={
        //val a = Reponse("College Cleunay")
        //Discours.generateDiscours("na",0)
        /*
        for(b <- a){
            var x= b//.map( z => Normalizer.normalize(z.toString, Normalizer.Form.NFD).substring(0, 1)).mkString("")
            println(x)
            x.codePoints.forEach(codePoint => println(codePoint.toHexString.toUpperCase + "=" +Character.toString(codePoint) ))
            x.map(z => {println(z);if(z!= ' ')Discours.generateDiscours((z.toString()),0)})
            Discours.generateDiscours(x,0)
            
        }
        */

        val lettre = "azertyuiopqsdfghjklmwxcvbn"
        var cm =(1 to 8).flatMap(i => lettre.combinations(i)).toList
        for ( a <- cm){
            try {
                Discours.generateDiscours(a,0)
                cm = cm.filterNot(b => a==b )
            }
            catch{
                case  _  => println(a)
            }
        }
        println(cm)
    }
}