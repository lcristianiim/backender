module com.backender.webservermodule {
    requires org.interactormodule;
    requires io.javalin.rendering;
    requires io.javalin;
    requires com.github.mustachejava;

    opens templates;
    opens images;
    opens com.webserver;
}