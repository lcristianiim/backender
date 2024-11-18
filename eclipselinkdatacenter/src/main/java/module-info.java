import org.interactor.modules.datacenter.PersonsPersistence;

module eclipselinkdatacenter {
    requires interactor;
    requires jakarta.persistence;
    requires jakarta.activation;
    requires com.sun.xml.bind.core;
    requires org.mapstruct;
    requires com.zaxxer.hikari;

    opens org.eclipselinkdatacenter.persons;

    exports org.eclipselinkdatacenter.persons;
    exports org.eclipselinkdatacenter.internal;
    opens org.eclipselinkdatacenter.internal;

    provides PersonsPersistence with org.eclipselinkdatacenter.persons.PersonsPersistenceImpl;
}