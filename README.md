# nlp-language-detection
Wrapper for multiple language detection algorithms. Configurable and api friendly interface.

val underTest = FallbackDetector.getDetector(List(new ChampeauDetector(), new OptimaizeDetector()))
val lang = underTest.detect("your text consisting of more than two words (for successfully detecting the language)")