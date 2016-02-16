package ae.teletronics.nlp;

import com.neovisionaries.i18n.LanguageCode;

import java.util.*;

/**
 * Created by Boris on 11-02-2016.
 */
public class LanguageMapper {

    private static Map<String, LanguageCode> theMap;

    static
    {
        List<LanguageCode> codes = Arrays.asList(LanguageCode.values());
        theMap = new HashMap<>();
        for (LanguageCode code : codes)
            if (!code.name().equals("undefined")) {
                Locale locale = Locale.forLanguageTag(code.name());
                theMap.put(locale.getISO3Language(), code);
            }
    }

    public static LanguageCode fromLocale(Locale locale) {
        return theMap.getOrDefault(locale.getISO3Language(), LanguageCode.undefined);
    }

    public static LanguageCode fromString(String langString) {
        return fromLocale(Locale.forLanguageTag(langString));
    }
}
