package ae.teletronics.nlp;

import com.neovisionaries.i18n.LanguageCode;
import me.champeau.ld.LangDetector;
import me.champeau.ld.UberLanguageDetector;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hhravn on 10/02/16.
 */
public class Champeu implements LanguageDetector {

    @Override
    public LanguageCode detect(String text) {
        UberLanguageDetector detector = UberLanguageDetector.getInstance();
        return LanguageMapper.fromString(detector.detectLang(text));
    }

    @Override
    public List<LanguageProbability> detectAll(String text) {
        UberLanguageDetector detector = UberLanguageDetector.getInstance();
        final Collection<LangDetector.Score> scores = detector.scoreLanguages(text);
        return scores.stream()
                .map(score -> new LanguageProbability(score.getLanguage(), score.getScore()))
                .collect(Collectors.toList());
    }
}
