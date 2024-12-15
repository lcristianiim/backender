import org.interactor.modules.datacenter.interfaces.PersonsPersistence;

module mongodbmodule {
    requires interactor;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;
    requires com.fasterxml.jackson.databind;
    requires bson4jackson;
    requires org.mapstruct;
    requires java.desktop;

    exports org.backender.interactorimplementations;
    opens org.backender.interactorimplementations;

    provides PersonsPersistence with org.backender.interactorimplementations.PersonsPersistenceImpl;
}