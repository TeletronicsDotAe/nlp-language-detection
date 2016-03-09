package ae.teletronics.nlp.language.detection.detectors

import java.util.Optional

import com.neovisionaries.i18n.LanguageCode
import com.optimaize.langdetect.LanguageDetectorBuilder
import com.optimaize.langdetect.ngram.NgramExtractors
import com.optimaize.langdetect.profiles.LanguageProfileReader

import scala.util.Try

/**
  * Created by trym on 18-02-2016.
  */
object OptimaizeDetector {
  // minimalConfidence can be tweaked, but want a real hit or no hit on language, so keep high confidence
  private val instance = LanguageDetectorBuilder.
    create(NgramExtractors.standard).
    withProfiles(new LanguageProfileReader().readAllBuiltIn()).
    minimalConfidence(0.99999).
    build()
}

class OptimaizeDetector extends SLanguageDetector {

  override def detect(text: String): Optional[LanguageCode] = {
    val res = Try(OptimaizeDetector.instance.detect(text))
    if (res.isSuccess && res.get.isPresent) {
      Optional.ofNullable(LanguageCode.getByCodeIgnoreCase(res.get.get().getLanguage))
    } else {
      Optional.empty()
    }
  }

}
