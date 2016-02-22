package ae.teletronics.nlp.language.detection.detectors

import com.neovisionaries.i18n.LanguageCode

/**
  * Created by trym on 18-02-2016.
  */
trait SLanguageDetector {
  def detect(text: String): java.util.Optional[LanguageCode]
}
