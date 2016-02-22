package ae.teletronics.nlp.language.detection.detectors

import java.util.Optional

import com.neovisionaries.i18n.LanguageCode
import org.apache.tika.language.LanguageIdentifier

/**
  * Created by trym on 18-02-2016.
  */
class TikaDetector extends SLanguageDetector {
  override def detect(text: String): Optional[LanguageCode] = {
    val res: String = new LanguageIdentifier(text).getLanguage()
    if (res != null) {
      Optional.of(LanguageCode.getByCodeIgnoreCase(res))
    } else {
      Optional.empty()
    }
  }
}
