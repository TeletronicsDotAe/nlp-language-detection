package ae.teletronics.nlp.language.detection.detectors

import com.neovisionaries.i18n.LanguageCode
import org.junit.{Ignore, Test}
import org.junit.Assert.assertEquals

import scala.io.Source

/**
  * Created by trym on 08-03-2016.
  */
class FallbackDetectorTest {

  @Test
  def testArabic() = {
    for (sample <- Source.fromFile("src/test/resources/arabic.test").getLines()) {
      assertEquals(sample, "ar", detectLang(sample))
    }
  }

  @Test
  def testUrdu() = {
    for (sample <- Source.fromFile("src/test/resources/urdu.test").getLines()) {
      assertEquals(sample, "ur", detectLang(sample))
    }
  }

  @Test
  def testHindi() = {
    for (sample <- Source.fromFile("src/test/resources/hindi.test").getLines()) {
      assertEquals(sample, "hi", detectLang(sample))
    }
  }

  @Ignore
  @Test
  def testEuroparl() = {
    var count = 0
    var undefinedCount = 0
    for (sample <- Source.fromFile("src/test/resources/europarl.test").getLines()) {
      val sampleArray = sample.split("\t")
      val detectedLang = detectLang(sampleArray(1))
      if ("undefined".equals(detectedLang)) {
        undefinedCount += 1
      } else if (!sampleArray(0).equals(detectedLang) && !"undefined".equals(detectedLang)) {
        //        println("Expected: " + sampleArray(0) + " but found: " + detectedLang + " at " + sampleArray(1))
        count += 1
      }
      //      assertEquals(sample, sampleArray(0), detectLang(sampleArray(1)))
    }
    println("failed: " + count + ", undefined: " + undefinedCount)
    // Carrot: failed: 14, undefined: 599
    // carrot, optimaze: failed: 21, undefined: 468
    // carrot, optimaze, champeau: failed: 23, undefined: 289
  }

  @Ignore
  @Test
  def testConfidenceLevel() = {
    // For each text, detect it and compare with the provided language
    var undefinedDetectedCount = 0
    var differentDetectedCount = 0
    var successDetectedCount = 0
    val startMs = System.currentTimeMillis()
    for (sample <- Source.fromFile("src/test/resources/langId-text.tsv").getLines()) {
      val twitterLang = sample.split("\t")(0)
      val twitterText = sample.split("\t")(1)
      val detectedLang: String = detectLang(twitterText)
      if (isSame(twitterLang, detectedLang)) {
        successDetectedCount += 1
      } else if ("undefined".equals(detectedLang) && !"und".equals(twitterLang)) {
        undefinedDetectedCount += 1
      } else if (!twitterLang.startsWith(detectedLang)) {
        differentDetectedCount += 1
        //        println(detectedLang + " <> " + twitterLang + " on " + twitterText)
      } else {
        successDetectedCount += 1
      }
    }
    println("Detector speed: " + (System.currentTimeMillis() - startMs))
    println("This detector could detect: " + successDetectedCount)
    println("This detector could not detect: " + undefinedDetectedCount)
    println("This detector differed from the labels: " + differentDetectedCount)
    //    List(new CarrotDetector(), new OptimaizeDetector(), new ChampeauDetector(100))
    //    Detector speed: 21347
    //    This detector could detect: 60355
    //    This detector could not detect: 13601
    //    This detector differed from the labels: 5648
  }

  private def isSame(twitterLang: String, detectedLang: String): Boolean = {
    ("undefined".equals(detectedLang) && "und".equals(twitterLang)) ||
      ("ms".equals(detectedLang) && "id".equals(twitterLang))
  }


  private val detector = new FallbackDetector(List(new CarrotDetector(), new OptimaizeDetector(), new ChampeauDetector(100)))
  //  private val detector = new FallbackDetector(List(new CarrotDetector(), new OptimaizeDetector()))
  //    private val detector = new FallbackDetector(List(new ChampeauDetector(200)))
  //  private val detector = new FallbackDetector(List(new TikaDetector()))
  //    private val detector = new FallbackDetector(List(new CarrotDetector()))
  //      private val detector = new FallbackDetector(List(new OptimaizeDetector()))

  private def detectLang(twitterText: String): String = {
    detector.
      detect(twitterText).
      orElse(LanguageCode.undefined).
      name()
  }

  /*
  * ChampeauDetector (20 confidence level)
      Detector speed: 41987
      This detector could detect: 43854
      This detector could not detect: 11523
      This detector differed from the labels: 24227
  * ChampeauDetector (30 confidence level)
      Detector speed: 43792
      This detector could detect: 40791
      This detector could not detect: 18360
      This detector differed from the labels: 20453
  * ChampeauDetector (40 confidence level)
      Detector speed: 45384
      This detector could detect: 37821
      This detector could not detect: 23926
      This detector differed from the labels: 17857
  * ChampeauDetector (60 confidence level)
      Detector speed: 44601
      This detector could detect: 32026
      This detector could not detect: 32885
      This detector differed from the labels: 14693
  * ChampeauDetector (80 confidence level)
      Detector speed: 44846
      This detector could detect: 26826
      This detector could not detect: 40030
      This detector differed from the labels: 12748
  * ChampeauDetector (100 confidence level)
      Detector speed: 43660
      This detector could detect: 28616
      This detector could not detect: 46037
      This detector differed from the labels: 4951
  * ChampeauDetector (200 confidence level)
      Detector speed: 42883
      This detector could detect: 13563
      This detector could not detect: 65102
      This detector differed from the labels: 939
  * OptimaizeDetector (0.9999)
      Detector speed: 14306
      This detector could detect: 29569
      This detector could not detect: 41972
      This detector differed from the labels: 8063
  * OptimaizeDetector (0.99999)
      Detector speed: 20950
      This detector could detect: 38379
      This detector could not detect: 37397
      This detector differed from the labels: 3828
  * OptimaizeDetector (0.999999)
      Detector speed: 21198
      This detector could detect: 13212
      This detector could not detect: 65433
      This detector differed from the labels: 959
  * CarrotDetector (confidence level 0.99)
      Detector speed: 3105
      This detector could detect: 57847
      This detector could not detect: 19220
      This detector differed from the labels: 2537
  * CarrotDetector (confidence level 0.999)
      Detector speed: 3031
      This detector could detect: 55264
      This detector could not detect: 22809
      This detector differed from the labels: 1531
  * CarrotDetector (confidence level 0.9999)
      Detector speed: 3021
      This detector could detect: 52857
      This detector could not detect: 25659
      This detector differed from the labels: 1088
  * new CarrotDetector(), new OptimaizeDetector()
      Detector speed: 9091
      This detector could detect: 56521
      This detector could not detect: 20145
      This detector differed from the labels: 2938
  * new CarrotDetector(3x9), new OptimaizeDetector(6x9)
      Detector speed: 7811
      This detector could detect: 55981
      This detector could not detect: 21508
      This detector differed from the labels: 2115
  * new CarrotDetector(), new OptimaizeDetector(), new ChampeauDetector()
      This detector could detect: 60536
      This detector could not detect: 12898
      This detector differed from the labels: 6170
  * new CarrotDetector()
      Detector speed: 2345
      This detector could detect: 55264
      This detector could not detect: 22809
      This detector differed from the labels: 1531
  * new CarrotDetector(), new OptimaizeDetector()
      Detector speed: 12448
      This detector could detect: 55991
      This detector could not detect: 21492
      This detector differed from the labels: 2121
  * new CarrotDetector(), new OptimaizeDetector(), new ChampeauDetector(200)
      Detector speed: 26146
      This detector could detect: 56196
      This detector could not detect: 20599
      This detector differed from the labels: 2809
  * TikaDetector
      Detector speed: 50131
      This detector could detect: 18726
      This detector could not detect: 63
      This detector differed from the labels: 60815
  * TikaDetector with isReasonablyCertain
      Detector speed: 50214
      This detector could detect: 0
      This detector could not detect: 72410
      This detector differed from the labels: 7194
  *
   */

}
