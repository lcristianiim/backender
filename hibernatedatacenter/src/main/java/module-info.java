module org.hibernatedatacentermodule {
    requires org.interactormodule;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;


    opens org.backender.persons;

    exports org.backender.persons;

    provides org.interactor.personsa.PersonsService with org.backender.persons.PersonService;
}