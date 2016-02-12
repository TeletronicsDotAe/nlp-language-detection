package ae.teletronics.nlp.language.detection;

import com.neovisionaries.i18n.LanguageCode;
import org.apache.tika.language.LanguageIdentifier;

import java.util.List;

/**
 * Created by trym on 10-02-2016.
 */
public class TikaDetector implements LanguageDetector {

    @Override
    public LanguageCode detect(String text) {
        return LanguageMapper.fromString(new LanguageIdentifier(text).getLanguage());
    }

    @Override
    public List<LanguageProbability> detectAll(String text) {
        throw new UnsupportedOperationException();
    }
}
