package ae.teletronics.nlp.language.detection.detectors

import ae.teletronics.nlp.language.detection.model.Language
import com.neovisionaries.i18n.LanguageCode
import org.apache.tika.language.LanguageIdentifier

/**
  * Created by trym on 18-02-2016.
  */
class TikaDetector extends SLanguageDetector {
  override def minimalConfidence() = 0.5

  override def detect(text: String): java.util.List[Language] = {
    import scala.collection.JavaConversions._
    val languageIdentifier = new LanguageIdentifier(text)
    if (languageIdentifier.isReasonablyCertain()) {
      List(new Language(LanguageCode.getByCodeIgnoreCase(languageIdentifier.getLanguage()), 0.5))
    } else {
      List.empty[Language]
    }
  }
}
