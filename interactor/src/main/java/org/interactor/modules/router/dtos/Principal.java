package org.interactor.modules.router.dtos;

import java.util.List;

public record Principal (
    String identifier,
    String name,
    String pin,
    List<String> roles,
    String uuid,
    boolean suspended,
    boolean canceled,
    boolean isActive
) {

    public static class Builder {
        private String identifier;
        private String name;
        private String pin;
        private List<String> roles;
        private String uuid;
        private boolean suspended;
        private boolean canceled;
        private boolean isActive;

        public Builder setIdentifier(String identifier) {
            this.identifier = identifier;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPin(String pin) {
            this.pin = pin;
            return this;
        }

        public Builder setRoles(List<String> roles) {
            this.roles = roles;
            return this;
        }

        public Builder setUuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setSuspended(boolean suspended) {
            this.suspended = suspended;
            return this;
        }

        public Builder setCanceled(boolean canceled) {
            this.canceled = canceled;
            return this;
        }

        public Builder setActive(boolean active) {
            isActive = active;
            return this;
        }

        public Principal build() {
            return new Principal(identifier, name, pin, roles, uuid, suspended, canceled, isActive);
        }
    }
}