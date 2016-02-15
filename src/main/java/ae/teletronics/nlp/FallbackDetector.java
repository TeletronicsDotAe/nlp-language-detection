package ae.teletronics.nlp;

import com.neovisionaries.i18n.LanguageCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Boris on 15-02-2016.
 */
public class FallbackDetector implements LanguageDetector {

    private List<LanguageDetector> detectors;

    public FallbackDetector(DetectorType... detectors) throws IllegalArgumentException {
        if (detectors.length == 0)
            throw new IllegalArgumentException("Must have at least one detector type");

        this.detectors = Arrays.asList(detectors).stream().map(DetectorFactory::makeSingleDetector).collect(Collectors.toList());
    }

    @Override
    public LanguageCode detect(String text) {
        return detectors
                .stream()
                .map(d -> d.detect(text))
                .filter(lc -> !lc.equals(LanguageCode.undefined))
                .findFirst()
                .orElse(LanguageCode.undefined);
    }

    @Override
    public List<LanguageProbability> detectAll(String text) {
        return detectors
                .stream()
                .map(d -> d.detectAll(text))
                .filter(l -> l.size() > 0)
                .findFirst()
                .orElse(new ArrayList<>());
    }
}
