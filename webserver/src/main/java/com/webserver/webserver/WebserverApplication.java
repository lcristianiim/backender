package com.webserver.webserver;


import com.github.mustachejava.DefaultMustacheFactory;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinMustache;
import org.interactor.personsa.HibernatePersonService;
import org.interactor.personsa.PersonDTO;

import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class WebserverApplication {
	public static void main(String[] args) {
		// Mustache configuration
		DefaultMustacheFactory factory = new DefaultMustacheFactory("templates");
		JavalinMustache.init(factory);

		Javalin.create(javalinConfig -> {
			javalinConfig.staticFiles.add(staticFileConfig -> {
				staticFileConfig.hostedPath = "/images";
				staticFileConfig.directory = "images";
			});
				})
				.get("/", ctx -> {
							HibernatePersonService hibernatePersonService = new HibernatePersonService();
							List<PersonDTO> persons = hibernatePersonService.getAllPersons();

							ctx.render("hello.mustache", model(
									"person1", persons.get(0),
									"person2", persons.get(1),
									"image", "images/a.jpg"));
						}
				)
				.start(7070);
	}
}
