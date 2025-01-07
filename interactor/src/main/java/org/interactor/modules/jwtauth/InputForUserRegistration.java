package org.interactor.modules.jwtauth;


public record InputForUserRegistration(
    String name,
    String identifier,
    String pin,
    String pinConfirm,
    boolean termsOfConditions,
    boolean privacyPolicy
) {}
