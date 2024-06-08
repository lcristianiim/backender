module com.backender.webservermodule {
    requires io.javalin;
    requires org.interactormodule;
    requires io.javalin.rendering;
    requires com.github.mustachejava;

    opens templates;
    opens images;
    opens com.webserver;
}