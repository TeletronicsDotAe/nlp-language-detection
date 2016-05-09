package ae.teletronics.nlp.language.detection.detectors

import java.util

import ae.teletronics.nlp.language.detection.model.Language
import com.carrotsearch.labs.langid.{DetectedLanguage, LangIdV3}
import com.neovisionaries.i18n.LanguageCode

/**
  * Created by trym on 18-02-2016.
  */
object CarrotDetector {
  private val DEFAULT_CONFIDENCE_LEVEL = 0.999
  private val instance = new LangIdV3()

  def newInstance() = new CarrotDetector(DEFAULT_CONFIDENCE_LEVEL)
}

class CarrotDetector(confidenceLevel: Double = CarrotDetector.DEFAULT_CONFIDENCE_LEVEL) extends SLanguageDetector {

  override def minimalConfidence() = confidenceLevel

  override def detect(text: String): List[Language] = {
    val detector = CarrotDetector.instance
    detector.reset()
    detector.append(text)

    val ranked: util.List[DetectedLanguage] = detector.rank(true)

    import scala.collection.JavaConversions._
    ranked
      .filter(r => r.getConfidence > 0.000001)
      .sortWith(_.getConfidence > _.getConfidence)
      .map(r => new Language(LanguageCode.getByCodeIgnoreCase(r.getLangCode()), r.getConfidence))
      .toList
  }

}
