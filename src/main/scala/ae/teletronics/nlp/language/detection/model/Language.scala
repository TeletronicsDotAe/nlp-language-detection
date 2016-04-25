package ae.teletronics.nlp.language.detection.model

import com.neovisionaries.i18n.LanguageCode

/**
  * Created by trym on 18-04-2016.
  */
class Language(val code: LanguageCode, val confidence: Double) {
  override def toString() = "Language(" + code + ", " + confidence + ")"
}
