package org.interactor.modules.jwtauth;


import com.fasterxml.jackson.annotation.JsonProperty;

public record InputForUserRegistration(
    String name,
    String identifier,
    String pin,
    @JsonProperty("pin_confirm")
    String pinConfirm,
    @JsonProperty("terms_and_conditions")
    boolean termsAndConditions,
    @JsonProperty("privacy_policy")
    boolean privacyPolicy
) {}
