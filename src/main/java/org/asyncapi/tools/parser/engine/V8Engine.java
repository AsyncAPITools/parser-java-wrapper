package org.asyncapi.tools.parser.engine;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

public class V8Engine implements ParserEngine {

    private V8 v8;

    private String parserScript;
    private String parserRef;

    public V8Engine(String parserScript, String parserRef) throws Exception {
        this.parserScript = parserScript;
        this.parserRef = parserRef;
        runEngine();
    }

    private void runEngine() throws Exception {
        v8 = V8.createV8Runtime();
        try(BufferedReader parserReader = new BufferedReader(new InputStreamReader(V8Engine.class.getClassLoader().getResourceAsStream(parserScript)))) {
            StringBuilder parserjs = new StringBuilder();
            parserReader.lines().forEach(line -> parserjs.append(line).append("\n"));
            v8.executeScript("var window = {}; ");
            v8.executeScript(parserjs.toString());
        }
    }

    public V8Object parse(String input) throws Exception {
        V8Object parser = ((V8Object) getParser());
        V8Object output = (V8Object) parser.executeJSFunction("parse", input);
        parser.release();
        return output;

    }

    public Object getParser() throws Exception {
        return v8.executeScript(parserRef);
    }

    public String stringify(Object obj) {
        V8Object JSON = ((V8Object) v8).getObject("JSON");
        V8Array args = new V8Array(v8).push(obj);
        String result = JSON.executeFunction("stringify", args).toString();
        args.release();
        JSON.release();
        return result;
    }

}
