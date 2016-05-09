package ae.teletronics.nlp.language.detection.detectors

import org.junit.Test
import org.junit.Assert._

/**
  * Created by trym on 18-04-2016.
  */
class CarrotDetectorTest {
  val underTest = new CarrotDetector()

  @Test
  def testNoHits() = {
    assertTrue(underTest.detect("anadfyadsf").isEmpty)
  }

  @Test
  def testIsEnglish() = {
    val languages = underTest.detect("sunshine")
    assertTrue(languages.size > 0)
  }
}
