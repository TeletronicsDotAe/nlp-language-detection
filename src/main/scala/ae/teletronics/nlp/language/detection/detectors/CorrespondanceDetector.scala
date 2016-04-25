package ae.teletronics.nlp.language.detection.detectors

import java.util

import ae.teletronics.nlp.language.detection.model.Language
import com.neovisionaries.i18n.LanguageCode

import scala.collection.JavaConversions._

/**
  * Created by trym on 18-04-2016.
  */
class CorrespondanceDetector(detector: SLanguageDetector) {

  def detect(messages: java.util.List[String]): java.util.List[LanguageCode] = {
    val languages = messages.map(detector.detect)
    val primaryLanguages = languages.
      filter(_.nonEmpty).
      map(_.head)
    val allLang2MsgCount = primaryLanguages.
      groupBy(_.code).
      mapValues(_.size)

    val confidentLang2MsgCount = primaryLanguages.
      filter(_.confidence >= detector.minimalConfidence()).
      groupBy(_.code).
      mapValues(_.size)

//    println(confidentLang2MsgCount.keySet)

    val total = messages.size()
    // choose which language each messages is detected as
    languages.map(ll => {
      if (ll.isEmpty) { // no language available by the detector
        println("SessionDetector#detect - ll.isEmpty")
        LanguageCode.undefined
      } else if (ll.head.confidence >= detector.minimalConfidence()) {// the detector is confident, so are we
        ll.head.code
      } else if (confidentLang2MsgCount.keySet.contains(ll.head.code)) {// the detector have a guess, make it confident by looking at confident languages
        ll.head.code
      } else if (lessThanOnePercent(ll.head.code, allLang2MsgCount, total)
        && hasLanguage(confidentLang2MsgCount.keySet, ll)) { // very few messages detected as this language, choose another possible language if it is confident
        val res = getConfidentLanguage(confidentLang2MsgCount.keySet, ll).getOrElse(LanguageCode.undefined)
//        println("SessionDetector#detect is choosing " + res + " in the list of " + ll)
        res
      } else { // no more help available
        println("SessionDetector#detect - no reasonable guesses for " + ll)
        LanguageCode.undefined
      }
    })
  }

  private def lessThanOnePercent(code: LanguageCode, lang2MsgCount: Map[LanguageCode, Int], total: Int): Boolean = {
    lang2MsgCount.getOrElse(code, -1) <= (total / 100) // less than one percent
  }

  private def getConfidentLanguage(confident: Set[LanguageCode], possible: util.List[Language]) = {
    possible.
      find(language => confident.contains(language.code)). // && language.confidence >= 0.99
      map(_.code)
  }

  private def hasLanguage(confident: Set[LanguageCode], possible: java.util.List[Language]) =
    getConfidentLanguage(confident, possible).isDefined

}
