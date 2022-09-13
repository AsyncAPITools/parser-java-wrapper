package org.asyncapi.tools.parser.engine;

public interface ParserEngine {

    Object getParser() throws Exception;

    Object parse(String sql) throws Exception;

    String stringify(Object obj);

}
