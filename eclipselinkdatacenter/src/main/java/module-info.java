import org.interactor.modules.datacenter.PersonsRepository;

module org.hibernatedatacentermodule {
    requires org.interactormodule;
    requires jakarta.persistence;

    opens org.hibernatedatacenter.persons;

    exports org.hibernatedatacenter.persons;

    provides PersonsRepository with org.hibernatedatacenter.persons.HardCodedPersonRepositoryImpl;
}