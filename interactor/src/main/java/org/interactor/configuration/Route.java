package org.interactor.configuration;

import org.interactor.modules.router.dtos.Controller;
import org.interactor.modules.router.dtos.RequestType;
import org.interactor.security.Role;

import java.util.List;

public record Route(
    RequestType requestType,
    String path,
    Controller controller,
    List<Role> roles
) {}
