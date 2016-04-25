package ae.teletronics.nlp.language.detection.detectors

import com.neovisionaries.i18n.LanguageCode
import org.junit.Assert._
import org.junit.Test

import scala.collection.JavaConversions._

/**
  * Created by trym on 19-04-2016.
  */
class CorrespondanceDetectorTest {

  val carrot = new CarrotDetector()
  val underTest = new CorrespondanceDetector(carrot)

  @Test
  def testUndefindedMessages() = {
    val undefindedMessages = List("Green", "blue", "red")
    val detectedLanguages = underTest.detect(undefindedMessages)
    assertTrue(detectedLanguages.forall(LanguageCode.undefined == _))
  }

  @Test
  def testEnglishMessages() = {
    val englishMessages = List("A green apple is hanging on the tree", "the water is blue", "my shirt is red")
    val detectedLanguages = underTest.detect(englishMessages)
    assertTrue(detectedLanguages.forall(LanguageCode.en == _))
  }

  @Test
  def test100Messages() = {
    val englishMessages = (1 to 100).map {
      _ + " green apple is hanging on the tree"
    }
    val dutchMessage = "Garage door opener." // mt: "Zzzzzzzz positive thoughts. Zzzzzzzzz"
    val mixedMessages = englishMessages ++ List(dutchMessage)
    val carrotLanguage = carrot.detect(dutchMessage)
    assertEquals(LanguageCode.nl, carrotLanguage.head.code)
//    carrotLanguage.foreach(println)
    val detectedLanguages = underTest.detect(mixedMessages)
    assertEquals(101, detectedLanguages.size())
    assertEquals(LanguageCode.nl, detectedLanguages(100))// the detector is confident..., we do not want to change that
  }

  @Test
  def testUnconfidentMessageNotInConfidentList() = {
    val englishMessages = (1 to 100).map(_ + " green apple(s) is/are hanging on the tree")
    val unConfidentMessageNotPrimary = "Godmorgen, hvordan har du det?"
    val mixedMessages = englishMessages ++ List(unConfidentMessageNotPrimary)
    val carrotLanguage = carrot.detect(unConfidentMessageNotPrimary)
    assertEquals(LanguageCode.no, carrotLanguage.head.code)
    val detectedLanguages = underTest.detect(mixedMessages)
    assertEquals(101, detectedLanguages.size())
    assertEquals(LanguageCode.undefined, detectedLanguages(100))
  }

  @Test
  def testUnconfidentMessageInConfidentList() = {
    val englishMessages = (1 to 100).map(_ + " green apple(s) is/are hanging on the tree")
    val unConfidentMessage = "my shirt is red"
    val mixedMessages = englishMessages ++ List(unConfidentMessage)
    val carrotLanguage = carrot.detect(unConfidentMessage)
    assertTrue(carrotLanguage.head.confidence <= carrot.minimalConfidence())
    assertEquals(LanguageCode.en, carrotLanguage.head.code)
    val detectedLanguages = underTest.detect(mixedMessages)
    assertEquals(101, detectedLanguages.size())
    assertEquals(LanguageCode.en, detectedLanguages(100))
  }

  @Test
  def testUnconfidentMessageWithSecondLevelLanguageInConfidentList() = {
    val englishMessages = (1 to 100).map(_ + " grøn flaske hænger på væggen og når den tilfældigvis falder, så er der en mindre")
    val unConfidentMessageNotPrimary = "Godmorgen, hvordan har du det?"
    val mixedMessages = englishMessages ++ List(unConfidentMessageNotPrimary)
    val carrotLanguage = carrot.detect(unConfidentMessageNotPrimary)
    assertEquals(LanguageCode.no, carrotLanguage.head.code)
    val detectedLanguages = underTest.detect(mixedMessages)
    assertEquals(101, detectedLanguages.size())
    assertEquals(LanguageCode.da, detectedLanguages(100))
  }

}
