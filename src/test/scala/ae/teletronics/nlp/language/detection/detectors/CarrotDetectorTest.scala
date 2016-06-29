package ae.teletronics.nlp.language.detection.detectors

import org.junit.Test
import org.junit.Assert._

/**
  * Created by trym on 18-04-2016.
  */
class CarrotDetectorTest {
  val underTest = new CarrotDetector()

  @Test
  def testNoProbableHits() = {
    val languages = underTest.detect("anadfyadsf")
    val probableLanguages = languages.filter(_.confidence > 0.2)
    assertTrue(probableLanguages.isEmpty)
  }

  @Test
  def testIsEnglish() = {
    val languages = underTest.detect("sunshine")
    assertTrue(languages.size > 0)
  }
}
