import org.interactor.modules.router.Router;

module router {
    requires interactor;

    opens org.backender.router;
    exports org.backender.router;

    provides Router with org.backender.router.RouterImplementation;
}