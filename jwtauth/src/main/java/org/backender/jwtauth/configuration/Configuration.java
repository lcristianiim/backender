package org.backender.jwtauth.configuration;

public enum Configuration {
    SERVER_ADDRESS(fetchByKey("jwtauth.server")),
    USER_LOGIN(fetchByKey("jwtauth.user.login")),
    USER_REGISTER(fetchByKey("jwtauth.user.register")),
    USER_CONFIRM(fetchByKey("jwtauth.user.confirm")),
    TOKEN_DECODE(fetchByKey("jwtauth.token.decode")),
    TOKEN_REFRESH(fetchByKey("jwtauth.token.refresh"));

    private static String fetchByKey(String key) {
        return JwtAuthModuleConfigurationLoader.INSTANCE.loadConfiguration(key);
    }

    private final String value;

    Configuration(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}