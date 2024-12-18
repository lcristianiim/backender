package com.webserver;

import com.github.mustachejava.DefaultMustacheFactory;
import com.webserver.response.ProcessJSONResponseHandler;
import com.webserver.response.ResponseHandler;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.rendering.template.JavalinMustache;
import org.interactor.ApplicationConfiguration;
import org.interactor.modules.logging.LoggerService;
import org.interactor.modules.metrics.MetricsService;
import org.interactor.modules.router.RouterService;

import org.interactor.modules.router.dtos.ReqContextDTO;
import org.interactor.modules.router.dtos.InteractorResponse;
import org.interactor.modules.datacenter.dtos.PersonDTO;
import org.interactor.modules.datacenter.PersonsPersistenceService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static io.javalin.rendering.template.TemplateUtil.model;

public class Application {
	private static final MetricsService METRICS_SERVICE = MetricsService.INSTANCE;
	private static final ApplicationConfiguration APPLICATION_CONFIGURATION = ApplicationConfiguration.INSTANCE;
	private static final LoggerService LOGGER = LoggerService.INSTANCE;
	private static final PersonsPersistenceService PERSONS_PERSISTENCE_SERVICE = PersonsPersistenceService.INSTANCE;
	private static final Class<Application> clazz = Application.class;

	public static void main(String[] args) {
		LOGGER.getLogging().info("Starting server on JAVA VERSION:" + System.getProperty("java.version"),
				clazz);

		eagerInitialization();
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
					LOGGER.getLogging().info("Start", clazz);
					METRICS_SERVICE.incrementCounter();
					LOGGER.getLogging().info("Metrics done", clazz);
					List<PersonDTO> persons = PERSONS_PERSISTENCE_SERVICE.getAllPersons();
					LOGGER.getLogging().info("Fetch all persons done", clazz);

					ctx.render("hello.mustache", model(
							"person1", persons.get(0),
							"person2", persons.get(1)
					));
					LOGGER.getLogging().info("render done", clazz);
				}
		);

		app.get("/carte", ctx -> {
					ctx.status(200);
					ctx.json("carte");
				}
		);

		app.get("/metrics", ctx -> {
			ctx.result(METRICS_SERVICE.getMetrics());
		});

		app.get("/increment", ctx -> {
			METRICS_SERVICE.incrementCounter();
		});

		app.get(APPLICATION_CONFIGURATION.getApiPath() + "/**", ctx -> {
			METRICS_SERVICE.incrementCounter();

			ReqContextDTO reqContext = transformJavalinContextToInteractorContextDTO(ctx);

			InteractorResponse response = RouterService.INSTANCE.getRouter().get(reqContext);

			ResponseHandler handler = new ProcessJSONResponseHandler();
			handler.handleRequest(response, ctx);
		});

		app.post(APPLICATION_CONFIGURATION.getApiPath() + "/**", ctx -> {
			METRICS_SERVICE.incrementCounter();

			ReqContextDTO reqContext = transformJavalinContextToInteractorContextDTO(ctx);

			InteractorResponse response = RouterService.INSTANCE.getRouter().post(reqContext);

			ResponseHandler handler = new ProcessJSONResponseHandler();
			handler.handleRequest(response, ctx);
		});

		app.start(7070);

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("Shutting down...");
			// todo
//			Hikari.INSTANCE.getDataSource().close(); // Close HikariCP DataSource
			app.stop(); // Stop Javalin app
			System.out.println("Shutdown complete.");
		}));
	}

	private static ReqContextDTO transformJavalinContextToInteractorContextDTO(Context ctx) {
		ReqContextDTO reqContext = new ReqContextDTO();
		if (ctx.queryString() != null) {
			reqContext.setRequestPath(ctx.path() + "?" + ctx.queryString());
		} else {
			reqContext.setRequestPath(ctx.path());
			reqContext.setLocale(getLocale(ctx));
		}
        reqContext.setBody(ctx.body());
        return reqContext;
	}

	private static void eagerInitialization() {
//		todo eager initialization for singletons from other jpms modules
	}

	private static Locale getLocale(Context ctx) {
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
