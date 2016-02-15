package ae.teletronics.nlp;

import com.neovisionaries.i18n.LanguageCode;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Created by Boris on 12-02-2016.
 */
public class LanguageDetectorTest extends TestCase {

    public void testDetectorPerformanceAndQuality() throws Exception {
        List<LanguageTestCase> euroParlTestCases = getEuroParlTestCases();
        List<LanguageTestCase> arabicTestCases = getGenericTestCases("arabic.test", "ar");
        List<LanguageTestCase> urduTestCases = getGenericTestCases("urdu.test", "ur");
        List<LanguageTestCase> hindiTestCases = getGenericTestCases("hindi.test", "hi");

        runLanguageDetectionTest(euroParlTestCases, "europarl");
        runLanguageDetectionTest(arabicTestCases, "arabic");
        runLanguageDetectionTest(urduTestCases, "urdu");
        runLanguageDetectionTest(hindiTestCases, "hindi");
    }

    private void runLanguageDetectionTest(List<LanguageTestCase> testCases, String testCaseSet)
    {
        Franc franc = new Franc();
        TikaDetector tika = new TikaDetector();
        Champeu champeu = new Champeu();
        Optimaize optimaize = new Optimaize();
        OptimaizeFallback optimaizeFallback = new OptimaizeFallback();

        System.out.println("Running test on " + testCases.size() + " lines from " + testCaseSet + "...");

        runDetectorTest(franc, "Franc", testCases);
        runDetectorTest(tika, "Tika", testCases);
        runDetectorTest(champeu, "Champeu", testCases);
        runDetectorTest(optimaize, "Optimaize", testCases);
        runDetectorTest(optimaizeFallback, "OptimaizeFallback", testCases);
    }

    private void runDetectorTest(LanguageDetector detector, String name, List<LanguageTestCase> testCases) {
        System.out.println("--------------- Testing " + name + " ----------------");
        long start = System.currentTimeMillis();
        List<LanguageTestResult> results = testCases.stream().map(testCase -> new LanguageTestResult(testCase, detector.detect(testCase.getLine()))).collect(Collectors.toList());
        long end = System.currentTimeMillis();
        long diffMillis = end - start;

        long successCount = results.stream().filter(r -> r.isSuccess()).count();
        long allCount = testCases.size();

        Map<String, List<String>> mistakes = new TreeMap<>(results.stream()
                .filter(r -> !r.isSuccess())
                .map(r -> r.getTestResult() + " / " + r.getTestCase().getLanguage())
                .collect(Collectors.groupingBy(s -> s)));

        System.out.println("Results for " + name + ": " + successCount + " / " + allCount + ", time(millis): " + diffMillis);
        for (Map.Entry<String, List<String>> entry : mistakes.entrySet()) {
            System.out.println("  " + entry.getKey() + " - " + entry.getValue().size());
        }
    }

    private List<LanguageTestCase> getEuroParlTestCases() {
        List<String> lines = getResourceFileLines("europarl.test");

        return lines
                .stream()
                .map(s -> parseEuroParlTestCase(s))
                .collect(Collectors.toList());
    }

    private List<LanguageTestCase> getGenericTestCases(String filename, String iso639_1_LanguageCode) {
        List<String> lines = getResourceFileLines(filename);

        return lines
                .stream()
                .map(text -> new LanguageTestCase(text, LanguageMapper.fromString(iso639_1_LanguageCode)))
                .collect(Collectors.toList());
    }

    private List<String> getResourceFileLines(String resourceFileName) {
        ClassLoader classLoader = new LanguageDetectorTest().getClass().getClassLoader();
        File langSentences = new File(classLoader.getResource(resourceFileName).getFile());
        List<String> lines = null;
        try
        {
            lines = (List<String>) FileUtils.readLines(langSentences);
        }
        catch (Exception e)
        {
            System.out.println("Got exception: " + e.toString());
            System.exit(1);
        }
        return lines;
    }

    private LanguageTestCase parseEuroParlTestCase(String line)
    {
        int tabIdx = line.indexOf('\t');
        String lang = line.substring(0, tabIdx);
        String text = line.substring(tabIdx + 1);
        return new LanguageTestCase(text, LanguageMapper.fromString(lang));
    }

    private class LanguageTestCase {
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

    private class LanguageTestResult {
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
}