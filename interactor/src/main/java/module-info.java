module interactor {
    requires com.fasterxml.jackson.databind;
    exports org.interactor.modules.datacenter;
    exports org.interactor.modules.metrics;
    exports org.interactor.router;
    uses org.interactor.modules.datacenter.PersonsRepository;
    uses org.interactor.modules.metrics.ApplicationMetrics;
}