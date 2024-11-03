module com.backender.webservermodule {
    requires org.interactormodule;
    requires io.javalin.rendering;
    requires io.javalin;
    requires com.github.mustachejava;
    requires annotations;

    opens templates;
    opens images;
    opens com.webserver;
}