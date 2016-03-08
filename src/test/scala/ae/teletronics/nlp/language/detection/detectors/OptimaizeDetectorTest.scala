package ae.teletronics.nlp.language.detection.detectors

import com.neovisionaries.i18n.LanguageCode
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
  * Created by trym on 18-02-2016.
  */
object OptimaizeDetectorTest {
  val danishExamples = List("Stop! - Pas på du ikke stikker din spidse næse for langt frem.",
    "Ha, bare rolig: Jeg stikker kun med handsken.",
    "Ja, men du er svær at få til at stikke.",
    "Og hvis Capulet’erne faktisk kommer, så stikker du sikkert af.",
    "De hunde!? Mod dem tager jeg alle stikkene hjem!",
    "Tag du bare hjem - ligesom pigerne - når det går løs. Vi tapre mænd skal nok blive stående!",
    "Jeg skal vise Capulet’s mænd at jeg kan blive stående, og hans piger, at noget andet kan stå.",
    "Ja ja, rolig nu. Kampen er mellem vores herrer, ikke mellem os, deres tjenere.",
    "Det er lige meget! - Jég har kræfter til at klare både Capulet’s tjenere, og hans jomfruer. Tjenerne vil jeg dømme til døden, og jomfruerne vil jeg befri for deres domme.",
    "Æh, hvilke domme?",
    "Deres jomfrudomme, sgu da. Er den trængt ind?",
    "Jo tak, du er ret indtrængende.",
    "eller bare trængende.",
    "Hvis I bliver ved med at pirre mig, så bliver jeg nok nødt til at hive den frem.",
    "Undskyld! Undskyld, men peger De af os",
    "Undskyld undskyld undskyld - hvis det kunne virke som om jeg pegede på dig",
    "Du er dus, siger “dig” til mig, òg du gir mig fingeren. Dét er at stramme den! Handsker?",
    "Handsker? Vil du bokse?",
    "Gerne!",
    "Fint - jeg skal vise jeg er mere mand end dig")

}
class OptimaizeDetectorTest {

//  val underTest = new FallbackDetector(List(new OptimaizeDetector(), new ChampeauDetector()))
  val underTest = new FallbackDetector(List(new ChampeauDetector(), new OptimaizeDetector()))
//  val underTest = new OptimaizeDetector()

  @Test
  def testDetect() = {
    val champeau = new ChampeauDetector()
    val carrot = new CarrotDetector()
    val tika = new TikaDetector()
    // all samples should be detected
    assertFalse(OptimaizeDetectorTest.danishExamples.
      map(t => (t, underTest.detect(t))).
      map(p => {
        val champeauLang = champeau.detect(p._1)
        val carrotLang = carrot.detect(p._1)
        val tikaLang = tika.detect(p._1)
        println("Optimaize: " + p._2 + ". Champeau: " + champeauLang + ". Carrot: " + carrotLang + ". tika: " + tikaLang)
        p._2
      })
      exists(!_.isPresent()))
    // all samples should be danish
  }

  @Test
  def testIsDanish() = {
    val lang = OptimaizeDetectorTest.danishExamples.map(underTest.detect(_))
    lang.foreach(println)
  }
}
