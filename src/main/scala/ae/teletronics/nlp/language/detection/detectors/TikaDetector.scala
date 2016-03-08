package ae.teletronics.nlp.language.detection.detectors

import java.util.Optional

import com.neovisionaries.i18n.LanguageCode
import org.apache.tika.language.LanguageIdentifier

/**
  * Created by trym on 18-02-2016.
  */
class TikaDetector extends SLanguageDetector {
  override def detect(text: String): Optional[LanguageCode] = {
    val languageIdentifier = new LanguageIdentifier(text)
    val lang = if (languageIdentifier.isReasonablyCertain()) {
      LanguageCode.getByCodeIgnoreCase(languageIdentifier.getLanguage())
    } else {
      null
    }
    Optional.ofNullable(lang)
  }
}
