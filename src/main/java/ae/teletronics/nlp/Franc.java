package ae.teletronics.nlp;

import com.neovisionaries.i18n.LanguageCode;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.apache.commons.io.IOUtils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hhravn on 10/02/16.
 */
public class Franc implements LanguageDetector {
    private static Invocable instance;
    private static ScriptEngine engine;
    static {
            engine = new ScriptEngineManager().getEngineByName("nashorn");
            try {
                final InputStream inputStream = Franc.class.getClassLoader().getResourceAsStream("franc-most.js");
                String src = IOUtils.toString(inputStream, "UTF-8");
                engine.eval(src);
                instance = (Invocable) engine;
            } catch (ScriptException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    @Override
    public LanguageCode detect(String text){
        try {
            final Object franc = instance.invokeFunction("franc", text);
            return LanguageMapper.fromString(franc.toString());
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<LanguageProbability> detectAll(String text){
        try {
            final ScriptObjectMirror franc = (ScriptObjectMirror) instance.invokeMethod(engine.eval("franc"), "all", text);
            return franc.keySet().stream().limit(5).map(key -> {
                final ScriptObjectMirror obj = (ScriptObjectMirror) franc.get(key);
                return new LanguageProbability(obj.get("0").toString(), new Double(obj.get("1").toString()));
            }).collect(Collectors.toList());
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
