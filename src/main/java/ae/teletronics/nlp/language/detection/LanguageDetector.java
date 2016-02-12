package ae.teletronics.nlp.language.detection;

import com.neovisionaries.i18n.LanguageCode;

import java.util.List;

/**
 * Created by hhravn on 10/02/16.
 */
public interface LanguageDetector {
    LanguageCode detect(String text);

    List<LanguageProbability> detectAll(String text);
}
