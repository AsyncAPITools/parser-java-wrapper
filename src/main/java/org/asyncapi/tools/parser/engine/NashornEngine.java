package org.asyncapi.tools.parser.engine;

import java.io.InputStreamReader;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class NashornEngine implements ParserEngine {

    private static ScriptEngineManager manager = new ScriptEngineManager();
    private ScriptEngine engine;

    private String parserScript;
    private String parserRef;

    public NashornEngine(String parserScript, String parserRef) throws Exception {
        this.parserScript = parserScript;
        this.parserRef = parserRef;
        runEngine();
    }

    private void runEngine() throws Exception {
        engine = manager.getEngineByName("nashorn");
        engine.eval("var window = {}; ");
        try(InputStreamReader isr = new InputStreamReader(GraalEngine.class.getClassLoader().getResourceAsStream(parserScript))) {
            engine.eval(isr);
        }
    }

    public Map<?, ?> parse(String input) throws Exception {
        Map<?, ?> output = (Map<?, ?>) ((Invocable) engine).invokeMethod(getParser(), "parse", input);
        return output;
    }

    public Object getParser() throws Exception {
        return engine.eval(parserRef);
    }

    public String stringify(Object obj) {
        return obj.toString();
    }

}
