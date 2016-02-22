package ae.teletronics.nlp.language.detection.detectors

/**
  * Created by trym on 18-02-2016.
  */
trait DetectorType {

  case object OPTIMAIZE extends DetectorType
  case object CHAMPEAU extends DetectorType
  case object TIKA extends DetectorType
  case object CARROT extends DetectorType

}
