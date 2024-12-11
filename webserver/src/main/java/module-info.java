module webserver {
    requires io.javalin.rendering;
    requires io.javalin;
    requires com.github.mustachejava;
    requires annotations;
//    requires eclipselinkdatacenter;
    requires interactor;

    opens templates;
    opens images;
    opens com.webserver;
}