package org.interactor;

import org.interactor.modules.router.dtos.InteractorResponse;
import org.interactor.modules.router.dtos.ReqContextDTO;
import org.junit.jupiter.api.Test;

class GatewayTest {

    @Test
    void a() {
        Gateway gateway = new Gateway();
        ReqContextDTO ctx = new ReqContextDTO();
        ctx.setRequestPath(ApplicationConfiguration.INSTANCE.getApiPath() + "/test-users");

        InteractorResponse result = gateway.processGETRequest(ctx);

    }
}