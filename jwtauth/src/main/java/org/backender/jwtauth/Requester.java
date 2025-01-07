package org.backender.jwtauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.interactor.internals.ObjectMapperSingleton;
import org.interactor.modules.jwtauth.*;

import java.util.Optional;

public class Requester {

    private static final String LOGIN = "http://localhost:4000/auth/login";
    private static final String DECODE = "http://localhost:4000/auth/decode";
    private final ObjectMapper mapper = ObjectMapperSingleton.INSTANCE.getObjectMapper();

    public JWTTokens login(LoginInput loginInput) {
        String payload = loginInputToBody(loginInput, mapper);

        HttpResponse<String> response = Unirest.post(LOGIN)
                .header("content-type", "application/json")
                .body(payload)
                .asString();

        return responseToJWTTokens(mapper, response);
    }

    public DecodeResponse decode(String token) {
        Token mappedToken = new Token(token);
        String payload = tokenToPayload(mappedToken);

        HttpResponse<String> response = Unirest.post(DECODE)
                .header("content-type", "application/json")
                .body(payload)
                .asString();

        return responseToPrincipal(mapper, response);
    }

    private String tokenToPayload(Token mappedToken) {
        try {
            return mapper.writeValueAsString(mappedToken);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    private DecodeResponse responseToPrincipal(ObjectMapper mapper, HttpResponse<String> response) {
        try {
            ErrorDecodeResponse errorResponse = mapper.readValue(response.getBody(), ErrorDecodeResponse.class);
            return new DecodeResponse(errorResponse.statusCode(), errorResponse.message(), Optional.empty());
        } catch (JsonProcessingException e) {
            try {
                try {
                    Optional<JWTPrincipal> result = Optional.of(mapper.readValue(response.getBody(), JWTPrincipal.class));
                    return new DecodeResponse("200", "success", result);
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (RuntimeException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private JWTTokens responseToJWTTokens(ObjectMapper mapper, HttpResponse<String> response) {
        try {
            return mapper.readValue(response.getBody(), JWTTokens.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String loginInputToBody(LoginInput loginInput, ObjectMapper mapper) {
        try {
            return mapper.writeValueAsString(loginInput);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
