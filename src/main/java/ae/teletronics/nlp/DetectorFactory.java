package ae.teletronics.nlp;

import org.apache.tika.detect.Detector;

/**
 * Created by Boris on 15-02-2016.
 */
public class DetectorFactory {

    public static LanguageDetector makeDetector(DetectorType... detectors) throws IllegalArgumentException {
        if (detectors.length == 0)
            throw new IllegalArgumentException("Must have at least one detector type");

        if (detectors.length > 1)
            return new FallbackDetector(detectors);
        else
            return makeSingleDetector(detectors[0]);
    }

    public static LanguageDetector makeSingleDetector(DetectorType detector) throws IllegalArgumentException {
        switch (detector) {
            case OPTIMAIZE: return new Optimaize();
            case CHAMPEU:   return new Champeu();
            case FRANC:     return new Franc();
            case TIKA:      return new TikaDetector();
            default: throw new IllegalArgumentException("Argument is unknown enum value");
        }
    }
}
