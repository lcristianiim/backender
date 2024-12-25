package org.interactor.usecases.users;

public record InputForUserRegistration(
        String name,
        String identifier,
        String pin,
        String pinConfirm,
        boolean termsAndConditions,
        boolean privacyPolicy
) {
}