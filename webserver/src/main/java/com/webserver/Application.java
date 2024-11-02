package com.webserver;


import com.github.mustachejava.DefaultMustacheFactory;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinMustache;
import org.interactor.modules.metrics.MetricsService;
import org.interactor.modules.datacenter.PersonDTO;
import org.interactor.modules.datacenter.PersonsPersistenceService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static io.javalin.rendering.template.TemplateUtil.model;

public class Application {
	private static MetricsService metricsService = new MetricsService();

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

		app.before(ctx -> {
			// Check if the request is for a static file
			if (!ctx.path().startsWith("/images")) {
				// Handle the request (e.g., log it, modify it, etc.)
				System.out.println("Handling request for: " + ctx.path());
			}
		});


		app.get("/", ctx -> {
					metricsService.incrementCounter();
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
					metricsService.incrementCounter();
				});

		app.get("/**", ctx -> {
			metricsService.incrementCounter();
			try {
				Thread.sleep(5000); // Simulate delay
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			ctx.result("This is some data!");
		});

		app.start(7070);

	}

	private static CompletableFuture<String> getRandomCatFactFuture() {
		return CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(10000); // Simulate delay
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			return "This is some data!";
		});
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
