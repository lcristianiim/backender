import org.hibernatedatacenter.persons.PersonService;

module org.hibernatedatacentermodule {
    requires org.interactormodule;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens org.hibernatedatacenter.persons;

    exports org.hibernatedatacenter.persons;

    provides org.interactor.personsa.PersonsService with PersonService;
}