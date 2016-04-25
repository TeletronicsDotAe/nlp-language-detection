package ae.teletronics.nlp.language.detection.detectors

import ae.teletronics.nlp.language.detection.model.Language

/**
  * Created by trym on 18-02-2016.
  */
trait SLanguageDetector {
  def minimalConfidence(): Double

  /**
    * Returns an empty iterator if no languages are detected,
    * otherwise an iterator with the probable languages
    */
  def detect(text: String): java.util.List[Language]
}
