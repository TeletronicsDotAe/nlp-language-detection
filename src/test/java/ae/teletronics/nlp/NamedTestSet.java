package ae.teletronics.nlp;

import java.util.List;

/**
 * Created by Boris on 15-02-2016.
 */
public class NamedTestSet {
    String name;
    List<LanguageTestCase> testcases;

    public NamedTestSet(String name, List<LanguageTestCase> testcases) {
        this.name = name;
        this.testcases = testcases;
    }

    public String getName() {
        return name;
    }

    public List<LanguageTestCase> getTestcases() {
        return testcases;
    }
}
