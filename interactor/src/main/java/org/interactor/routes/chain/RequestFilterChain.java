package org.interactor.routes.chain;

import org.interactor.modules.router.dtos.InteractorRequest;
import org.interactor.modules.router.dtos.InteractorResponse;

import java.util.ArrayList;
import java.util.List;

public class RequestFilterChain {
    private final List<RequestFilter> filters = new ArrayList<>();

    public void addFilter(RequestFilter filter) {
        filters.add(filter);
    }

    public InteractorResponse execute(InteractorRequest ctx) {
        List<InteractorResponse> result = new ArrayList<>();
        for (RequestFilter filter : filters) {
            result.add(filter.execute(ctx));
        }
        return result.getLast();
    }
}
