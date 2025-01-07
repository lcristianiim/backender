package org.backender.jwtauth;


import com.fasterxml.jackson.annotation.JsonProperty;

public record InputForUserRegistrationDTO(
    String name,
    String identifier,
    String pin,
    @JsonProperty("pin_confirm")
    String pinConfirm,
    @JsonProperty("terms_and_conditions")
    boolean termsOfConditions,
    @JsonProperty("privacy_policy")
    boolean privacyPolicy
) {}
