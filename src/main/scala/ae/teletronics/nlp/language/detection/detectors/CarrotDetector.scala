package ae.teletronics.nlp.language.detection.detectors

import java.util.Optional

import com.carrotsearch.labs.langid.LangIdV3
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

  override def detect(text: String): Optional[LanguageCode] = {
    val res = CarrotDetector.instance.classify(text, true)
    //println("CarrotDetector: confidence: " + res.getConfidence())
    if (res.getConfidence() > confidenceLevel) {
      Optional.of(LanguageCode.getByCodeIgnoreCase(res.getLangCode()))
    } else {
      Optional.empty()
    }
  }

}
