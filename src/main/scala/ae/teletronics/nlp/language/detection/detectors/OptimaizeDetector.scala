package ae.teletronics.nlp.language.detection.detectors

import java.util.Optional

import com.neovisionaries.i18n.LanguageCode
import com.optimaize.langdetect.LanguageDetectorBuilder
import com.optimaize.langdetect.ngram.NgramExtractors
import com.optimaize.langdetect.profiles.LanguageProfileReader

/**
  * Created by trym on 18-02-2016.
  */
object OptimaizeDetector {
  // minimalConfidence can be tweaked, but want a real hit or no hit on language, so keep high confidence
  val instance = LanguageDetectorBuilder.
    create(NgramExtractors.standard).
    withProfiles(new LanguageProfileReader().readAllBuiltIn()).
    build()
}

class OptimaizeDetector extends SLanguageDetector {

  override def detect(text: String): Optional[LanguageCode] = {
    val res = OptimaizeDetector.instance.detect(text)
    if (res.isPresent) {
      Optional.of(LanguageCode.getByCodeIgnoreCase(res.get().getLanguage))
    } else {
      Optional.empty()
    }
  }

}
