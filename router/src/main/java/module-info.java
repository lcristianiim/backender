import org.backender.router.RouterImplementation;
import org.interactor.modules.router.Router;

module router {
    requires interactor;

    provides Router with RouterImplementation;
}