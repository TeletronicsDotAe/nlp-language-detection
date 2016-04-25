package ae.teletronics.nlp.language.detection.detectors

import java.util.Optional

import ae.teletronics.nlp.language.detection.model.Language
import com.neovisionaries.i18n.LanguageCode
import me.champeau.ld.UberLanguageDetector

object ChampeauDetector {
  private val DEFAULT_CONFIDENCE_LEVEL = 100

  def newInstance() = new ChampeauDetector(DEFAULT_CONFIDENCE_LEVEL)
}
/**
  * Created by trym on 18-02-2016.
  */
class ChampeauDetector(confidenceLevel: Double = ChampeauDetector.DEFAULT_CONFIDENCE_LEVEL) extends SLanguageDetector {
  override def minimalConfidence() = confidenceLevel
  override def detect(text: String): java.util.List[Language] = {
    import scala.collection.JavaConversions._
    UberLanguageDetector.getInstance().
      scoreLanguages(text).toList.
      map(r => new Language(LanguageCode.getByCodeIgnoreCase(r.getLanguage), r.getScore))
  }

  def detectOld(text: String): Optional[LanguageCode] = {
    val res = UberLanguageDetector.getInstance().detectLang(text)
    if (res != null) {
      Optional.of(LanguageCode.getByCodeIgnoreCase(res))
    } else {
      Optional.empty()
    }
  }

}
