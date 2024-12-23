package org.interactor.modules.jwtauth;

public record RegisterInput(
    String name,
    String identifier,
    String pin,
    String pinConfirm,
    boolean termsOfConditions,
    boolean privacyPolicy
) {}
