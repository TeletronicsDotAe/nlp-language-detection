package ae.teletronics.nlp.language.detection.detectors

import java.util

import ae.teletronics.nlp.language.detection.model.Language


object FallbackDetector {
  def getDetector(detectors: java.util.List[SLanguageDetector]) = {
    import scala.collection.JavaConversions._
    new FallbackDetector(detectors.toList)
  }
}

class FallbackDetector(detectors: List[SLanguageDetector]) extends SLanguageDetector {
  override def minimalConfidence() = detectors.head.minimalConfidence()

  override def detect(text: String): java.util.List[Language] = {
    var index = 0
    var res: util.List[Language] = detectors(index).detect(text)
    while (!res.isEmpty() && ((index + 1) < detectors.size)) {
      index = index + 1
      res = detectors(index).detect(text)
    }
    if (!res.isEmpty()) {
      res
    } else {
      import scala.collection.JavaConversions._
      List.empty[Language]
    }
  }
}
