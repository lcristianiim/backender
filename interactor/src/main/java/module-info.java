module org.interactormodule {
    exports org.interactor.personsa;
    exports org.interactor.metrics;
    uses org.interactor.personsa.PersonsRepository;
    uses org.interactor.metrics.ApplicationMetrics;
}