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
    exports org.eclipselinkdatacenter.persistence.entities;
    opens org.eclipselinkdatacenter.persistence.entities;
    exports org.eclipselinkdatacenter.persistence.mappers;
    opens org.eclipselinkdatacenter.persistence.mappers;
    exports org.eclipselinkdatacenter.repositories;
    opens org.eclipselinkdatacenter.repositories;
    exports org.eclipselinkdatacenter.interactorimplementations;
    opens org.eclipselinkdatacenter.interactorimplementations;

    provides PersonsPersistence with org.eclipselinkdatacenter.interactorimplementations.PersonsPersistenceImpl;
}