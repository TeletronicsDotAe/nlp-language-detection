package ae.teletronics.nlp.language.detection.detectors

import ae.teletronics.nlp.language.detection.model.Language

class FallbackDetector(detectors: List[SLanguageDetector]) extends SLanguageDetector {
  override def minimalConfidence() = detectors.head.minimalConfidence()

  override def detect(text: String): List[Language] = {
    detectors
      .iterator // iterators are lazy, so the following map and find only evaluates as much as needed
      .map(_.detect(text))
      .find(!_.isEmpty)
      .getOrElse(List.empty[Language])
  }
}
