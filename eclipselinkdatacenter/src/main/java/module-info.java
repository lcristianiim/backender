import org.interactor.modules.datacenter.PersonsRepository;

module eclipselinkdatacenter {
    requires interactor;
    requires jakarta.persistence;

    opens org.eclipselinkdatacenter.persons;

    exports org.eclipselinkdatacenter.persons;

    provides PersonsRepository with org.eclipselinkdatacenter.persons.PersonRepositoryImplementation;
}