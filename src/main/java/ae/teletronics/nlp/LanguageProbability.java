package ae.teletronics.nlp;

/**
 * Created by Boris on 11-02-2016.
 */
public class LanguageProbability {
    private String language;
    private double probability;

    public LanguageProbability(String language, double probability) {
        this.language = language;
        this.probability = probability;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}