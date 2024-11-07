package com.webserver;


import com.github.mustachejava.DefaultMustacheFactory;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.rendering.template.JavalinMustache;
import org.interactor.ApplicationConfigurationSingleton;
import org.interactor.modules.metrics.MetricsServiceBeanSingleton;
import org.interactor.router.ResponseBody;
import org.interactor.router.Router;
import org.interactor.modules.datacenter.PersonDTO;
import org.interactor.modules.datacenter.PersonsPersistenceService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static io.javalin.rendering.template.TemplateUtil.model;

public class Application {
	private static final MetricsServiceBeanSingleton METRICS_SERVICE_BEAN_SINGLETON = MetricsServiceBeanSingleton.INSTANCE;
	private static final ApplicationConfigurationSingleton applicationConfiguration =
			ApplicationConfigurationSingleton.INSTANCE;

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
					METRICS_SERVICE_BEAN_SINGLETON.incrementCounter();
					PersonsPersistenceService personsPersistenceService = new PersonsPersistenceService();
					List<PersonDTO> persons = personsPersistenceService.getAllPersons();

					ctx.render("hello.mustache", model(
							"person1", persons.get(0),
							"person2", persons.get(1),
							"image", "images/a.jpg"));
				}
		);

		app.get("/metrics", ctx -> {
			ctx.result(METRICS_SERVICE_BEAN_SINGLETON.getMetrics());
		});

		app.get("/increment", ctx -> {
			METRICS_SERVICE_BEAN_SINGLETON.incrementCounter();
		});

		app.get(applicationConfiguration.getApiPath() + "/**", ctx -> {
			METRICS_SERVICE_BEAN_SINGLETON.incrementCounter();

			Locale locale = getLocale(ctx);

			Router router = new Router();
			ResponseBody response = router.get(ctx.path(), locale);

			ctx.status(response.code());
			ctx.json(response.body());
		});

		app.start(7070);
	}

	private static Locale getLocale(@NotNull Context ctx) {
		return ctx.header("Accept-Language") != null
				? Locale.forLanguageTag(Objects.requireNonNull(ctx.header("Accept-Language"))
				.split(",")[0])
				: Locale.getDefault();
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
