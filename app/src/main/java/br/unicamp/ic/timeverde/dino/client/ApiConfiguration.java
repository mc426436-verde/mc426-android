package br.unicamp.ic.timeverde.dino.client;


public class ApiConfiguration {

    public static class Credential {
        public static final String CLIENT_SECRET = "my-secret-token-to-change-in-production";
        public static final String CLIENT_ID = "dinoapp";
        public static final String GRANT_TYPE = "password";
        public static final String SCOPE = "read write";
    }

    public static final String BASE_URL = "http://192.168.1.39:8080";
}
