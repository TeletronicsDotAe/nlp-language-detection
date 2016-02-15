package ae.teletronics.nlp;

import com.google.common.base.Optional;
import com.neovisionaries.i18n.LanguageCode;
import com.optimaize.langdetect.i18n.LdLocale;

import java.util.List;

/**
 * Created by Boris on 12-02-2016.
 */
public class OptimaizeFallback extends Optimaize implements LanguageDetector {

    private static Champeu fallback = new Champeu();

    @Override
    public LanguageCode detect(String text) {
        Optional<LdLocale> lang = instance.detect(text);
        return lang.isPresent() ? LanguageMapper.fromString(lang.get().getLanguage()) : fallback.detect(text);
    }

    @Override
    public List<LanguageProbability> detectAll(String text) {
        List<LanguageProbability> ps = super.detectAll(text);
        if (ps.size() > 0)
            return ps;
        else
            return fallback.detectAll(text);
    }
}
