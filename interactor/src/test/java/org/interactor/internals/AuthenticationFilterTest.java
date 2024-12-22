package org.interactor.internals;

import org.interactor.configuration.RegisteredRoute;
import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class AuthenticationFilterTest {

    @Test
    void a() {
        AuthenticationFilter filter = new AuthenticationFilter();
        InteractorRequest ctx = new InteractorRequest();
        ctx.setRequestPath("invalid");
        InteractorResponse response = filter.execute(ctx);

        List<RegisteredRoute> customValues = new ArrayList<>(Arrays.asList(
                RegisteredRoute.ADD_USER
        ));
    }

}