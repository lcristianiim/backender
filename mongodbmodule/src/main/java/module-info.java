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

    exports org.backender.mongodbmodule.interactorimplementations;
    opens org.backender.mongodbmodule.interactorimplementations;
    exports org.backender.mongodbmodule.internals;
    opens org.backender.mongodbmodule.internals;
    exports org.backender.mongodbmodule.repositories;
    opens org.backender.mongodbmodule.repositories;
    exports org.backender.mongodbmodule.configuration;
    opens org.backender.mongodbmodule.configuration;

    provides PersonsPersistence with org.backender.mongodbmodule.interactorimplementations.PersonsPersistenceImpl;
//    this is needed in all modules for access to properties configuration files
    opens mongodbmodule.environments;
}