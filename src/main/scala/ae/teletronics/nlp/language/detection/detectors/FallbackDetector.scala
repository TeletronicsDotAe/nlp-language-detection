package ae.teletronics.nlp.language.detection.detectors

import java.util.Optional

import com.neovisionaries.i18n.LanguageCode


object FallbackDetector {
  def getDetector(detectors: java.util.List[SLanguageDetector]) = {
    import scala.collection.JavaConversions._
    new FallbackDetector(detectors.toList)
  }
}

class FallbackDetector(detectors: List[SLanguageDetector]) extends SLanguageDetector {

  override def detect(text: String): Optional[LanguageCode] = {
    var index = 0
    var res = detectors(index).detect(text)
    while (!res.isPresent() && ((index + 1) < detectors.size)) {
      index = index + 1
      res = detectors(index).detect(text)
    }
    if (res.isPresent()) {
      Optional.of(res.get())
    } else {
      Optional.empty()
    }
  }
}
