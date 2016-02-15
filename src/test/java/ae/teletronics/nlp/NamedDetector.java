package ae.teletronics.nlp;

/**
 * Created by Boris on 15-02-2016.
 */
public class NamedDetector {
    private LanguageDetector detector;
    private String name;

    public NamedDetector(LanguageDetector detector, String name) {
        this.detector = detector;
        this.name = name;
    }

    public LanguageDetector getDetector() {
        return detector;
    }

    public String getName() {
        return name;
    }
}
