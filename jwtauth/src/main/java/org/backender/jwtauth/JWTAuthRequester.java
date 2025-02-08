package org.backender.jwtauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.interactor.configuration.Configuration;
import org.interactor.internals.ObjectMapperSingleton;
import org.interactor.modules.jwtauth.*;
import org.interactor.modules.logging.LoggerService;

import java.util.Optional;

import static org.backender.jwtauth.configuration.Configuration.*;

public class JWTAuthRequester {

    private static final String LOGIN = SERVER_ADDRESS.getValue() + USER_LOGIN.getValue();
    private static final String REGISTER = SERVER_ADDRESS.getValue() + USER_REGISTER.getValue();
    private static final String CONFIRM = SERVER_ADDRESS.getValue() + USER_CONFIRM.getValue();
    private static final String DECODE = SERVER_ADDRESS.getValue() + TOKEN_DECODE.getValue();
    private static final String REFRESH_TOKEN = SERVER_ADDRESS.getValue() + TOKEN_REFRESH.getValue();
    private static final String ACTIVATION_LINK = SERVER_ADDRESS.getValue() + GET_USER_ACTIVATION_LINK.getValue();
    private static final String USER_PURGE_LINK = SERVER_ADDRESS.getValue() + USER_PURGE.getValue();


    private final ObjectMapper mapper = ObjectMapperSingleton.INSTANCE.getObjectMapper();

    public LoginUserResponse login(LoginInput loginInput) {
        String payload = loginInputToBody(loginInput, mapper);

        HttpResponse<String> response = Unirest.post(LOGIN)
                .header("content-type", "application/json")
                .header(COM_KEY.getValue(), COM_VALUE.getValue())
                .body(payload)
                .asString();
        
        if (response.getStatus() == 200) {
            String logMessage = USER_LOGIN_SUCCESS.getValue().formatted(loginInput.identifier());
            logMessage(logMessage);

            return new LoginUserResponse(true, null, responseToJWTTokens(mapper, response));
        }

        JWTAuthServerErrorResponse error = new JWTAuthServerErrorResponse(response.getStatus(),
                response.getStatusText(), response.getBody());

        String logMessage = USER_LOGIN_FAILED.getValue().formatted(loginInput.identifier(), error.body());

        logMessage(logMessage);

        return new LoginUserResponse(false, error, null);
    }

    public DecodeResponse decode(String token) {
        Token mappedToken = new Token(token);
        String payload = tokenToPayload(mappedToken);

        HttpResponse<String> response = Unirest.post(DECODE)
                .header("content-type", "application/json")
                .header(COM_KEY.getValue(), COM_VALUE.getValue())
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
                .header(COM_KEY.getValue(), COM_VALUE.getValue())
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
                inputForUserRegistration.termsAndConditions(),
                inputForUserRegistration.privacyPolicy()
        );

        String payload = inputForUserRegistrationToPayload(input, mapper);

        HttpResponse<String> response = Unirest.post(REGISTER)
                .header("content-type", "application/json")
                .header(COM_KEY.getValue(), COM_VALUE.getValue())
                .body(payload)
                .asString();

        if (response.getStatus() == 200) {
            String logMessage = USER_REGISTER_SUCCESS.getValue().formatted(inputForUserRegistration.identifier());
            logMessage(logMessage);

            return new RegisterUserResponse(true, null);
        }

        JWTAuthServerErrorResponse result =
                new JWTAuthServerErrorResponse(response.getStatus(), response.getStatusText(), response.getBody());

        String logMessage = USER_REGISTER_FAILED.getValue()
                .formatted(inputForUserRegistration.identifier(), result.body());
        logMessage(logMessage);

        return new RegisterUserResponse(false, result);
    }

    private static void logMessage(String message) {
        LoggerService.INSTANCE.getLogging().info(message, JWTAuthRequester.class);
    }

    public ConfirmUserResponse confirm(String confirmCode) {
        HttpResponse<String> response = Unirest.get(CONFIRM + "/" + confirmCode)
                .header("content-type", "application/json")
                .header(COM_KEY.getValue(), COM_VALUE.getValue())
                .asString();

        try {
            JWTAuthServerErrorResponse result = mapper.readValue(response.getBody(), JWTAuthServerErrorResponse.class);
            return new ConfirmUserResponse(false, result);
        } catch (JsonProcessingException e) {
            return new ConfirmUserResponse(true, null);
        }
    }

    public GetUserActivationResponse getUserActivationLink(String identifier) {
        String url = ACTIVATION_LINK + "/" + identifier;

        HttpResponse<String> response = Unirest.get(url)
                .header("content-type", "application/json")
                .header(COM_KEY.getValue(), COM_VALUE.getValue())
                .asString();

        try {
            SuccessActivationLinkResponse returnedResponse = mapper.readValue(response.getBody(), SuccessActivationLinkResponse.class);
            String confirmationLink = Configuration.BACKEND_DOMAIN.getValue()
                    + Configuration.API_PATH.getValue()
                    + USER_CONFIRM.getValue()
                    + "/" + returnedResponse.uuid();

            String successLogMessage = GET_ACTIVATION_LINK_SUCCESS.getValue().formatted(returnedResponse.userIdentifier());
            logMessage(successLogMessage);

            return new GetUserActivationResponse(true, confirmationLink, returnedResponse.uuid(),
                    returnedResponse.userIdentifier(), null);

        } catch (JsonProcessingException e) {
            try {
                JWTAuthServerErrorResponse result = mapper.readValue(response.getBody(), JWTAuthServerErrorResponse.class);

                String failLogMessage = GET_ACTIVATION_LINK_FAIL.getValue().formatted(identifier);
                logMessage(failLogMessage);

                return new GetUserActivationResponse(false, null, null, null, result);
            } catch (JsonProcessingException f) {
                throw new RuntimeException(f.getMessage());
            }
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
            return new DecodeResponse(errorResponse.statusCode(), errorResponse.body(), Optional.empty());
        } catch (JsonProcessingException e) {
            try {
                try {
                    Optional<JWTPrincipal> result = Optional.of(mapper.readValue(response.getBody(), JWTPrincipal.class));
                    return new DecodeResponse(200, "success", result);
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

    public PurgeUserResponse purgeUser(String identifierToPurge) {
        HttpResponse<String> response = Unirest.delete(USER_PURGE_LINK + "/" + identifierToPurge)
                .header(COM_KEY.getValue(), COM_VALUE.getValue())
                .asString();

        if (response.getStatus() == 200) {
            return new PurgeUserResponse(true, null);
        } else {
            JWTAuthServerErrorResponse result = new JWTAuthServerErrorResponse(
                    response.getStatus(),
                    response.getStatusText(),
                    response.getBody()
            );

            return new PurgeUserResponse(false, result);
        }
    }
}
