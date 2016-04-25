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
    assertTrue(underTest.detect("anadfyadsf").isEmpty())
  }

  @Test
  def testIsEnglish() = {
    import scala.collection.JavaConversions._
    val languages = underTest.detect("sunshine").toList
    assertTrue(languages.size > 0)
//    languages.foreach(l => println(l.code + ": " + l.confidence))
  }
}
