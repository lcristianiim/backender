module interactor {
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires jdk.httpserver;
    requires org.mockito;

    exports org.interactor.modules.datacenter;
    exports org.interactor.modules.metrics;
    exports org.interactor.modules.logging;
    exports org.interactor;
    exports org.interactor.modules.datacenter.interfaces;
    exports org.interactor.modules.datacenter.dtos;

    exports org.interactor.modules.router;
    exports org.interactor.modules.router.dtos;
    exports org.interactor.configuration;

    opens org.interactor.modules.datacenter.dtos;
    exports org.interactor.routes;
    exports org.interactor.routes.chain;

    uses org.interactor.modules.datacenter.interfaces.PersonsPersistence;
    uses org.interactor.modules.router.Router;
    uses org.interactor.modules.metrics.ApplicationMetrics;
    uses org.interactor.modules.logging.ApplicationLogging;

    exports org.interactor.modules.jwtauth to com.fasterxml.jackson.databind;
}