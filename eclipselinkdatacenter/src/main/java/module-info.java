import org.interactor.modules.datacenter.interfaces.PersonsPersistence;

module eclipselinkdatacenter {
    requires interactor;
    requires jakarta.persistence;
    requires jakarta.activation;
    requires com.sun.xml.bind.core;
    requires org.mapstruct;
    requires com.zaxxer.hikari;

    exports org.eclipselinkdatacenter.internal;
    opens org.eclipselinkdatacenter.internal;
    exports org.eclipselinkdatacenter.entities;
    opens org.eclipselinkdatacenter.entities;
    exports org.eclipselinkdatacenter.mappers;
    opens org.eclipselinkdatacenter.mappers;
    exports org.eclipselinkdatacenter.repositories;
    opens org.eclipselinkdatacenter.repositories;
    exports org.eclipselinkdatacenter.interactorimplementations;
    opens org.eclipselinkdatacenter.interactorimplementations;

    provides PersonsPersistence with org.eclipselinkdatacenter.interactorimplementations.PersonsPersistenceImpl;
}