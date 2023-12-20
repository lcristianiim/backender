module com.backender.webservermodule {
    requires com.github.mustachejava;
    requires io.javalin;
    requires javalin.rendering;
    requires org.interactormodule;

    opens templates;
    opens images;
}