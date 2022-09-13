package org.asyncapi.tools.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import com.eclipsesource.v8.V8Object;

import org.asyncapi.tools.parser.engine.ParserEngine;
import org.asyncapi.tools.parser.engine.V8Engine;

public class AsyncAPIParser {

    // private static ParserEngine graal = null;
    // private static ParserEngine nashorn = null;
    private static ParserEngine v8 = null;

    static {
        try {
            // graal = new GraalParser("bundle.js", "window.AsyncAPIParser"); // Not working
            // graal = new GraalParser("bundle-es5.js", "window.AsyncAPIParser"); // Bit slow
            // nashorn = new NashornParser("bundle-es5.js", "window.AsyncAPIParser"); // Too slow
            v8 = new V8Engine("bundle-es5.js", "window.AsyncAPIParser");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object parse(String input) throws Exception {
        // Map<?, ?> docMap = (Map<?, ?>) graal.parse(input);
        // Map<?, ?> docMap = (Map<?, ?>) nashorn.parse(input);
        V8Object result = (V8Object) v8.parse(input);
        String output = v8.stringify(result);
        result.release();
        return output;
    }

    public static Object parseFromUrl(URL input) throws Exception {
        return parse(read(input.openStream()));
    }

    public static Object parseFromStream(InputStream input) throws Exception {
        return parse(read(input));
    }

    public static Object parseFromReader(Reader input) throws Exception {
        return parse(read(input));
    }

    private static String read(InputStream input) throws IOException {
        return read(new InputStreamReader(input));
    }

    private static String read(Reader input) throws IOException {
        try (BufferedReader reader = new BufferedReader(input)) {
            StringBuilder content = new StringBuilder();
            reader.lines().forEach(line -> content.append(line).append(System.lineSeparator()));
            return content.toString();
        }
    }

}