import org.backender.router.RouterImplementation;
import org.interactor.modules.router.Router;

module router {
    requires interactor;

    opens org.backender.router;

    provides Router with RouterImplementation;
}