package ae.teletronics.nlp.language.detection.detectors

import ae.teletronics.nlp.language.detection.model.Language

class FallbackDetector(detectors: List[SLanguageDetector]) extends SLanguageDetector {
  override def minimalConfidence() = detectors.head.minimalConfidence()

  override def detect(text: String): List[Language] = {
    var index = 0
    var res: List[Language] = detectors(index).detect(text)
    while (!res.isEmpty && ((index + 1) < detectors.size)) {
      index = index + 1
      res = detectors(index).detect(text)
    }
    if (!res.isEmpty) {
      res
    } else {
      List.empty[Language]
    }
  }
}
