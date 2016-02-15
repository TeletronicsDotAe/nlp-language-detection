package ae.teletronics.nlp;

import com.neovisionaries.i18n.LanguageCode;

/**
 * Created by Boris on 15-02-2016.
 */
public class LanguageTestCase {
    private String line;
    private LanguageCode language;

    public LanguageTestCase(String line, LanguageCode language) {
        this.line = line;
        this.language = language;
    }

    public String getLine() {
        return line;
    }

    public LanguageCode getLanguage() {
        return language;
    }
}
