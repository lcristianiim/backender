module interactor {
    requires com.fasterxml.jackson.databind;
    exports org.interactor.modules.datacenter;
    exports org.interactor.modules.metrics;
    exports org.interactor.modules.logging;
    exports org.interactor.router;
    exports org.interactor;
    uses org.interactor.modules.datacenter.PersonsRepository;
    uses org.interactor.modules.metrics.ApplicationMetrics;
    uses org.interactor.modules.logging.ApplicationLogging;
}