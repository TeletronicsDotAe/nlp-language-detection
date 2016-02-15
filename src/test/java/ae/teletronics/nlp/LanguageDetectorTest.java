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

    NamedDetector franc;
    NamedDetector tika;
    NamedDetector champeu;
    NamedDetector optimaize;
    NamedDetector optimaizeFallback;

    NamedTestSet euroParlTestCases;
    NamedTestSet arabicTestCases;
    NamedTestSet urduTestCases;
    NamedTestSet hindiTestCases;

    protected void setUp() throws Exception {
        franc = new NamedDetector(DetectorFactory.makeSingleDetector(DetectorType.FRANC), "Franc");
        tika = new NamedDetector(DetectorFactory.makeSingleDetector(DetectorType.TIKA), "Tika");
        champeu = new NamedDetector(DetectorFactory.makeSingleDetector(DetectorType.CHAMPEU), "Champeu");
        optimaize = new NamedDetector(DetectorFactory.makeDetector(DetectorType.OPTIMAIZE), "Optimaize");
        optimaizeFallback = new NamedDetector(DetectorFactory.makeDetector(DetectorType.OPTIMAIZE, DetectorType.CHAMPEU), "Optimaize - Champeu Fallback");

        euroParlTestCases = new NamedTestSet("europarl", getEuroParlTestCases());
        arabicTestCases = new NamedTestSet("arabic", getGenericTestCases("arabic.test", "ar"));
        urduTestCases = new NamedTestSet("urdu", getGenericTestCases("urdu.test", "ur"));
        hindiTestCases = new NamedTestSet("hindi", getGenericTestCases("hindi.test", "hi"));
    }

    public void testDetectorPerformanceAndQuality() throws Exception {
        runLanguageDetectionTest(euroParlTestCases);
        runLanguageDetectionTest(arabicTestCases);
        runLanguageDetectionTest(urduTestCases);
        runLanguageDetectionTest(hindiTestCases);
    }

    public void testDetectorMostProbablePerformanceAndQuality() throws Exception {
        runProbableLanguageDetectionTest(euroParlTestCases, 0.7);
        runProbableLanguageDetectionTest(arabicTestCases, 0.7);
        runProbableLanguageDetectionTest(urduTestCases, 0.7);
        runProbableLanguageDetectionTest(hindiTestCases, 0.7);
    }

    private void runProbableLanguageDetectionTest(NamedTestSet testSet, double threshold) {
        System.out.println("Running probable detection test on " + testSet.getTestcases().size() + " lines from " + testSet.getName() + "...");

//        runProbableDetectorTest(franc, testSet.getTestcases(), threshold);
  //      runProbableDetectorTest(tika, testSet.getTestcases(), threshold);
    //    runProbableDetectorTest(champeu, testSet.getTestcases(), threshold);
      //  runProbableDetectorTest(optimaize, testSet.getTestcases(), threshold);
        runProbableDetectorTest(optimaizeFallback, testSet.getTestcases(), threshold);
    }

    private void runProbableDetectorTest(NamedDetector detector, List<LanguageTestCase> testCases, double threshold)
    {

    }

    private void runLanguageDetectionTest(NamedTestSet testSet)
    {
        System.out.println("Running test on " + testSet.getTestcases().size() + " lines from " + testSet.getName() + "...");

//        runDetectorTest(franc, testSet.getTestcases());
  //      runDetectorTest(tika, testSet.getTestcases());
    //    runDetectorTest(champeu, testSet.getTestcases());
      //  runDetectorTest(optimaize, testSet.getTestcases());
        runDetectorTest(optimaizeFallback, testSet.getTestcases());
    }

    private void runDetectorTest(NamedDetector detector, List<LanguageTestCase> testCases) {
        System.out.println("--------------- Testing " + detector.getName() + " ----------------");
        long start = System.currentTimeMillis();
        List<LanguageTestResult> results = testCases.stream().map(testCase -> new LanguageTestResult(testCase, detector.getDetector().detect(testCase.getLine()))).collect(Collectors.toList());
        long end = System.currentTimeMillis();
        long diffMillis = end - start;

        long successCount = results.stream().filter(r -> r.isSuccess()).count();
        long allCount = testCases.size();

        Map<String, List<String>> mistakes = new TreeMap<>(results.stream()
                .filter(r -> !r.isSuccess())
                .map(r -> r.getTestResult() + " / " + r.getTestCase().getLanguage())
                .collect(Collectors.groupingBy(s -> s)));

        System.out.println("Results for " + detector.getName() + ": " + successCount + " / " + allCount + ", time(millis): " + diffMillis);
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
}