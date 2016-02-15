package ae.teletronics.nlp;

import com.google.common.base.Optional;
import com.neovisionaries.i18n.LanguageCode;
import com.optimaize.langdetect.i18n.LdLocale;

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
}
