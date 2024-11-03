module webserver {
    requires interactor;
    requires io.javalin.rendering;
    requires io.javalin;
    requires com.github.mustachejava;
    requires annotations;
    requires com.fasterxml.jackson.databind;

    opens templates;
    opens images;
    opens com.webserver;
}