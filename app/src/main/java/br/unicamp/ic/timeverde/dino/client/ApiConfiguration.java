package br.unicamp.ic.timeverde.dino.client;


public class ApiConfiguration {

    public static class Credential {
        public static final String CLIENT_SECRET = "mySecretOAuthSecret";
        public static final String CLIENT_ID = "dinoapp";
        public static final String GRANT_TYPE = "password";
        public static final String SCOPE = "read write";
    }

    public static final String BASE_URL = "http://177.220.85.187:8080";
}
