package ae.teletronics.nlp;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Boris on 15-02-2016.
 */
public class LanguageProbabilityTestResult {
    List<LanguageProbability> languageProbabilities;
    LanguageTestCase testCase;

    public LanguageProbabilityTestResult(List<LanguageProbability> languageProbabilities, LanguageTestCase testCase) {
        this.languageProbabilities = languageProbabilities;
        this.testCase = testCase;
    }

    private Stream<LanguageProbability> filteredAt(double threshold) {
        return languageProbabilities.stream().filter(lp -> lp.getProbability() >= threshold);
    }

    public boolean isIncludedAtThreshold(double threshold) {
        return filteredAt(threshold).findFirst().isPresent();
    }

    // prerequisite: isIncludedAtThreshold
    public boolean isSuccessAtThreshold(double threshold) {
        return filteredAt(threshold)
                .sorted(Comparator.comparing(lp -> -lp.getProbability()))
                .findFirst()
                .get()
                .getLanguage()
                .equals(testCase.getLanguage());
    }
}
