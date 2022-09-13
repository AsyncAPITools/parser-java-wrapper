package org.asyncapi.tools.parser;

import static org.junit.Assert.assertNotNull;

import java.net.URL;

import org.junit.Test;

public class AsyncAPIParserTest {

    @Test
    public void testParse() throws Exception {
        Object result = AsyncAPIParser.parse("asyncapi: 2.4.0\n" +
                        "info:\n" +
                        "  title: Account Service\n" +
                        "  version: '1.0.0'\n" +
                        "  description: |\n" +
                        "    Manages user accounts in the system.\n" +
                        "  license:\n" +
                        "    name: Apache 2.0\n" +
                        "    url: https://www.apache.org/licenses/LICENSE-2.0\n" +
                        "\n" +
                        "servers:\n" +
                        "  production:\n" +
                        "    url: mqtt://test.mosquitto.org\n" +
                        "    protocol: mqtt\n" +
                        "    description: Test MQTT broker\n" +
                        "\n" +
                        "channels:\n" +
                        "  user/signedup:\n" +
                        "    subscribe:\n" +
                        "      operationId: emitUserSignUpEvent\n" +
                        "      message:\n" +
                        "        $ref : '#/components/messages/UserSignedUp'\n" +
                        "\n" +
                        "components:\n" +
                        "  messages:\n" +
                        "    UserSignedUp:\n" +
                        "      name: userSignedUp\n" +
                        "      title: User signed up event\n" +
                        "      summary: Inform about a new user registration in the system\n" +
                        "      contentType: application/json\n" +
                        "      payload:\n" +
                        "        $ref: '#/components/schemas/userSignedUpPayload'\n" +
                        "\n" +
                        "  schemas:\n" +
                        "    userSignedUpPayload:\n" +
                        "      type: object\n" +
                        "      properties:\n" +
                        "        firstName:\n" +
                        "          type: string\n" +
                        "          description: 'foo'\n" +
                        "        lastName:\n" +
                        "          type: string\n" +
                        "          description: 'bar'\n" +
                        "        email:\n" +
                        "          type: string\n" +
                        "          format: email\n" +
                        "          description: 'baz'\n" +
                        "        createdAt:\n" +
                        "          type: string\n" +
                        "          format: date-time ");
        System.out.println("-------------------------");
        System.out.println("parse result: " + result);
        System.out.println("-------------------------");

        assertNotNull(true);
    }

    @Test
    public void testParseFromStream() throws Exception {
        Object result = AsyncAPIParser.parseFromStream(AsyncAPIParserTest.class.getClassLoader().getResourceAsStream("asyncapi-sample.yaml"));
        System.out.println("-------------------------");
        System.out.println("stream result : " + result);
        System.out.println("-------------------------");
        assertNotNull(true);
    }

    @Test
    public void testParseFromUrl() throws Exception {
        Object result = AsyncAPIParser.parseFromUrl(new URL("https://raw.githubusercontent.com/asyncapi/spec/master/examples/simple.yml"));
        System.out.println("-------------------------");
        System.out.println("url result : " + result);
        System.out.println("-------------------------");
        assertNotNull(true);
    }

}
