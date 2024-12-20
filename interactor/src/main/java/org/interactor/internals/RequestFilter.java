package org.interactor.internals;

import org.interactor.modules.router.dtos.InteractorResponse;
import org.interactor.modules.router.dtos.InteractorRequest;

public interface RequestFilter {
    void setNextHandler(RequestFilter nextHandler);
    InteractorResponse execute(InteractorRequest ctx);
}

