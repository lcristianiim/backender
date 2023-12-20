import org.hibernatedatacenter.persons.PersonService;

module org.hibernatedatacentermodule {
    requires org.interactormodule;
    requires jakarta.persistence;

    opens org.hibernatedatacenter.persons;

    exports org.hibernatedatacenter.persons;

    provides org.interactor.personsa.PersonsService with PersonService;
}