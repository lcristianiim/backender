package org.backender.jwtauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.backender.jwtauth.configuration.Configuration;
import org.backender.jwtauth.configuration.JwtAuthModuleConfigurationLoader;
import org.interactor.internals.ObjectMapperSingleton;
import org.interactor.modules.jwtauth.*;

import java.util.Optional;

import static org.backender.jwtauth.configuration.Configuration.*;

public class JWTAuthRequester {

    private static final String LOGIN = SERVER_ADDRESS.getValue() + USER_LOGIN.getValue();
    private static final String REGISTER = SERVER_ADDRESS.getValue() + USER_REGISTER.getValue();
    private static final String CONFIRM = SERVER_ADDRESS.getValue() + USER_CONFIRM.getValue();
    private static final String DECODE = SERVER_ADDRESS.getValue() + TOKEN_DECODE.getValue();
    private static final String REFRESH_TOKEN = SERVER_ADDRESS.getValue() + TOKEN_REFRESH.getValue();

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

    public RefreshTokenResponse refreshToken(RefreshTokenInput refreshTokenInput) {
        RefreshTokenInputDTO refreshTokenInputDTO = new RefreshTokenInputDTO(
                refreshTokenInput.grandType(),
                refreshTokenInput.refreshToken()
        );
        String payload = refreshTokenToPayload(refreshTokenInputDTO, mapper);

        HttpResponse<String> response = Unirest.post(REFRESH_TOKEN)
                .header("content-type", "application/json")
                .body(payload)
                .asString();

        try {
            JWTAuthServerErrorResponse result = mapper.readValue(response.getBody(), JWTAuthServerErrorResponse.class);
            return new RefreshTokenResponse(false, result, null);
        } catch (JsonProcessingException e) {
            try {
                JWTTokens result = mapper.readValue(response.getBody(), JWTTokens.class);
                return new RefreshTokenResponse(true, null, result);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public RegisterUserResponse register(InputForUserRegistration inputForUserRegistration) {
        InputForUserRegistrationDTO input = new InputForUserRegistrationDTO(
                inputForUserRegistration.name(),
                inputForUserRegistration.identifier(),
                inputForUserRegistration.pin(),
                inputForUserRegistration.pinConfirm(),
                inputForUserRegistration.termsOfConditions(),
                inputForUserRegistration.privacyPolicy()
        );

        String payload = inputForUserRegistrationToPayload(input, mapper);

        HttpResponse<String> response = Unirest.post(REGISTER)
                .header("content-type", "application/json")
                .body(payload)
                .asString();

        try {
            JWTAuthServerErrorResponse result = mapper.readValue(response.getBody(), JWTAuthServerErrorResponse.class);
            return new RegisterUserResponse(false, result);
        } catch (JsonProcessingException e) {
            return new RegisterUserResponse(true, null);
        }
    }

    public ConfirmUserResponse confirm(String confirmCode) {
        HttpResponse<String> response = Unirest.get(CONFIRM + "/" + confirmCode)
                .header("content-type", "application/json")
                .asString();

        try {
            JWTAuthServerErrorResponse result = mapper.readValue(response.getBody(), JWTAuthServerErrorResponse.class);
            return new ConfirmUserResponse(false, result);
        } catch (JsonProcessingException e) {
            return new ConfirmUserResponse(true, null);
        }
    }

    private String inputForUserRegistrationToPayload(InputForUserRegistrationDTO inputForUserRegistration, ObjectMapper mapper) {
        try {
            return mapper.writeValueAsString(inputForUserRegistration);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String refreshTokenToPayload(RefreshTokenInputDTO refreshTokenInput, ObjectMapper mapper) {
        try {
            return mapper.writeValueAsString(refreshTokenInput);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
            JWTAuthServerErrorResponse errorResponse = mapper.readValue(response.getBody(), JWTAuthServerErrorResponse.class);
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
