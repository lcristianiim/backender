package com.webserver;


import com.github.mustachejava.DefaultMustacheFactory;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinMustache;
import org.interactor.metrics.MetricsService;
import org.interactor.personsa.PersonDTO;
import org.interactor.personsa.PersonsPersistenceService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class Application {
	public static void main(String[] args) {

		System.out.println("JAVA VERSION:" + System.getProperty("java.version"));
		writePidFile();


		var app = Javalin.create(javalinConfig -> {
					javalinConfig.staticFiles.add(staticFileConfig -> {
						staticFileConfig.hostedPath = "/images";
						staticFileConfig.directory = "images";
						staticFileConfig.precompress = true;
					});
					javalinConfig.useVirtualThreads = true;

					DefaultMustacheFactory factory = new DefaultMustacheFactory("templates");
					javalinConfig.fileRenderer(new JavalinMustache(factory));
				});


		app.get("/", ctx -> {
							PersonsPersistenceService personsPersistenceService = new PersonsPersistenceService();
							List<PersonDTO> persons = personsPersistenceService.getAllPersons();

							ctx.render("hello.mustache", model(
									"person1", persons.get(0),
									"person2", persons.get(1),
									"image", "images/a.jpg"));
						}
				).
				get("/metrics", ctx -> {
					MetricsService metricsService = new MetricsService();
					ctx.result(metricsService.getMetrics());
				})
				.get("/increment", ctx -> {
					MetricsService metricsService = new MetricsService();
					metricsService.incrementCounter();
				})
				.start(7070);
	}

	public static void writePidFile() {
		String fileName = "pid";
		String content = String.valueOf(ProcessHandle.current().pid());

        try {
            Files.writeString(Path.of(fileName), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
