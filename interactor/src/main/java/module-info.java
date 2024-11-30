module interactor {
    requires com.fasterxml.jackson.databind;

    exports org.interactor.modules.datacenter;
    exports org.interactor.modules.metrics;
    exports org.interactor.modules.logging;
    exports org.interactor.router;
    exports org.interactor;
    exports org.interactor.modules.datacenter.interfaces;
    exports org.interactor.modules.datacenter.dtos;

    uses org.interactor.modules.datacenter.interfaces.PersonsPersistence;
    uses org.interactor.modules.metrics.ApplicationMetrics;
    uses org.interactor.modules.logging.ApplicationLogging;
}