package ae.teletronics.nlp;

import com.google.common.base.Optional;
import com.neovisionaries.i18n.LanguageCode;
import com.optimaize.langdetect.LanguageDetectorBuilder;
import com.optimaize.langdetect.i18n.LdLocale;
import com.optimaize.langdetect.ngram.NgramExtractors;
import com.optimaize.langdetect.profiles.LanguageProfile;
import com.optimaize.langdetect.profiles.LanguageProfileReader;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hhravn on 09/02/16.
 */
public class Optimaize implements LanguageDetector {

    protected static com.optimaize.langdetect.LanguageDetector instance;
    private static LanguageCode defaultLang = LanguageCode.en;

    static {
            try{
                List<LanguageProfile> languageProfiles = new LanguageProfileReader().readAllBuiltIn();
                instance = LanguageDetectorBuilder.create(NgramExtractors.standard())
                        .withProfiles(languageProfiles)
                        .build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
       }

    @Override
    public LanguageCode detect(String text) {
        Optional<LdLocale> lang = instance.detect(text);
        if (lang.isPresent())
            return LanguageMapper.fromString(lang.get().getLanguage());
        else
            return defaultLang;
    }

    @Override
    public List<LanguageProbability> detectAll(String text) {
        return instance.getProbabilities(text).stream()
                .map(score -> new LanguageProbability(LanguageMapper.fromString(score.getLocale().getLanguage()), score.getProbability()))
                .collect(Collectors.toList());
    }
}
