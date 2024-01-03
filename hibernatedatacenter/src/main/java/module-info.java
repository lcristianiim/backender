import org.hibernatedatacenter.persons.PersonRepositoryImplementation;
import org.interactor.personsa.PersonsRepository;

module org.hibernatedatacentermodule {
    requires org.interactormodule;
    requires jakarta.persistence;

    opens org.hibernatedatacenter.persons;

    exports org.hibernatedatacenter.persons;

    provides PersonsRepository with PersonRepositoryImplementation;
}