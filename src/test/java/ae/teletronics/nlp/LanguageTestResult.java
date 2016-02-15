package ae.teletronics.nlp;

import com.neovisionaries.i18n.LanguageCode;

/**
 * Created by Boris on 15-02-2016.
 */
public class LanguageTestResult {
    private LanguageTestCase testCase;
    private LanguageCode testResult;

    public LanguageTestCase getTestCase() {
        return testCase;
    }

    public LanguageCode getTestResult() {
        return testResult;
    }

    public LanguageTestResult(LanguageTestCase testCase, LanguageCode testResult) {

        this.testCase = testCase;
        this.testResult = testResult;
    }

    // agnostic about whether the result is two-letter language or three-letter language
    public boolean isSuccess()
    {
        return testCase.getLanguage().equals(testResult);
    }

}
