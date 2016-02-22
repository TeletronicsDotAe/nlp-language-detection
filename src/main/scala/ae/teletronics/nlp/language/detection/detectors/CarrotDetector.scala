package ae.teletronics.nlp.language.detection.detectors

import java.util.Optional

import com.carrotsearch.labs.langid.LangIdV3
import com.neovisionaries.i18n.LanguageCode

/**
  * Created by trym on 18-02-2016.
  */
object CarrotDetector {
  val instance = new LangIdV3()
}

class CarrotDetector extends SLanguageDetector {

  override def detect(text: String): Optional[LanguageCode] = {
    val res = CarrotDetector.instance.classify(text, true)
    //println("CarrotDetector: confidence: " + res.getConfidence())
    if (res.getConfidence() > 0.99) {
      Optional.of(LanguageCode.getByCodeIgnoreCase(res.getLangCode()))
    } else {
      Optional.empty()
    }
  }

}
