module org.interactormodule {
    exports org.interactor.modules.datacenter;
    exports org.interactor.modules.metrics;
    uses org.interactor.modules.datacenter.PersonsRepository;
    uses org.interactor.modules.metrics.ApplicationMetrics;
}