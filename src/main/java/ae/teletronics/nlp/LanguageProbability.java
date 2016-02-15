package ae.teletronics.nlp;

import com.neovisionaries.i18n.LanguageCode;

/**
 * Created by Boris on 11-02-2016.
 */
public class LanguageProbability {
    private LanguageCode language;
    private double probability;

    public LanguageProbability(LanguageCode language, double probability) {
        this.language = language;
        this.probability = probability;
    }

    public LanguageCode getLanguage() {
        return language;
    }

    public void setLanguage(LanguageCode language) {
        this.language = language;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}