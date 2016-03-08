package ae.teletronics.nlp.language.detection.detectors

import java.util
import java.util.Optional

import com.neovisionaries.i18n.LanguageCode
import me.champeau.ld.LangDetector.Score
import me.champeau.ld.UberLanguageDetector

/**
  * Created by trym on 18-02-2016.
  */
class ChampeauDetector(confidenceLevel: Double = 100) extends SLanguageDetector {

  override def detect(text: String): Optional[LanguageCode] = {
    val res: util.Collection[Score] = UberLanguageDetector.getInstance().scoreLanguages(text)
    val iterator = res.iterator()
    if (iterator.hasNext){
      val score = res.iterator().next()
      if (score.getScore() > confidenceLevel) {
        Optional.of(LanguageCode.getByCodeIgnoreCase(score.getLanguage))
      } else {
        Optional.empty()
      }
    } else {
      Optional.empty()
    }
  }

  def detectOld(text: String): Optional[LanguageCode] = {
    val res = UberLanguageDetector.getInstance().detectLang(text)
    if (res != null) {
      Optional.of(LanguageCode.getByCodeIgnoreCase(res))
    } else {
      Optional.empty()
    }
  }

}
