module jwtauth {
    requires interactor;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires unirest.java;

    exports org.backender.jwtauth;

    provides org.interactor.modules.jwtauth.JWTAuth with org.backender.jwtauth.JWTAuthImplementation;
}