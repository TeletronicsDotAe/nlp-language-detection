package ae.teletronics.nlp;

import com.neovisionaries.i18n.LanguageCode;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Created by hhravn on 10/02/16.
 */
public interface LanguageDetector {
    LanguageCode detect(String text);

    default Optional<LanguageProbability> detectMostProbable(String text, double threshold) {
        try {
            return detectAll(text).stream().filter(lp -> lp.getProbability() > threshold).sorted(Comparator.comparingDouble(lp -> - lp.getProbability())).findFirst();
        } catch (Exception e) {
            System.err.println(e);
            return Optional.empty();
        }
    }

    List<LanguageProbability> detectAll(String text);
}
