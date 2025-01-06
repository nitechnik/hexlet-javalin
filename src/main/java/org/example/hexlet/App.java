package org.example.hexlet;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import io.javalin.validation.ValidationException;
import org.example.hexlet.controller.SessionsController;
import org.example.hexlet.controller.UsersController;
import org.example.hexlet.dto.MainPage;
import org.example.hexlet.dto.courses.CoursePage;
import org.example.hexlet.dto.courses.CoursesPage;
import org.example.hexlet.dto.users.BuildUserPage;
import org.example.hexlet.model.Course;
import org.example.hexlet.model.User;
import org.example.hexlet.repository.UserRepository;
import org.example.hexlet.util.NamedRoutes;

import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

public class App {
    public static void main(String[] args) {
        // Создаём приложение
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            // будем использовать jte в качестве шаблонизатора (template engine) в нашем приложении.
            config.fileRenderer(new JavalinJte());
        });

        // обработчик главной страницы
        app.get(NamedRoutes.rootPath(), ctx -> {
            var visited = Boolean.valueOf(ctx.cookie("visited")); // работа с куками
            var page = new MainPage(visited, ctx.sessionAttribute("currentUser")); // + работа с сессией
            ctx.render("index.jte", model("page", page));
            ctx.cookie("visited", String.valueOf(true));
        });

        app.get("/users/{id}/post/{postId}", ctx -> {
            var userId = ctx.pathParam("id");
            var postId = ctx.pathParam("postId");
            ctx.result("User ID: " + userId + " Post ID: " + postId);
        });

        app.get(NamedRoutes.coursePath("{id}"), ctx -> {
            var id = ctx.pathParamAsClass("id", Long.class).getOrDefault(8L);
            /*
            Курс извлекается из базы данных.
            Как работать с базами данных мы разберём в следующих уроках.
            */
            var course = new Course("Java: Веб-технологии", "Курс по веб-технологиям на Java");
            var page = new CoursePage(course);
            ctx.render("courses/show.jte", model("page", page));
        });

        app.get(NamedRoutes.coursesPath(), ctx -> {
            var term = ctx.queryParam("term");
            /* Список курсов извлекается из базы данных */
            var courses = List.of(
                    new Course("Java: Веб-технологии", "Курс по веб-технологиям на Java"),
                    new Course("Java: Базовый курс", "Курс по основам Java"),
                    new Course("Java: Продвинутый курс", "Курс по продвинутым темам Java"));
            var header = "Курсы по программированию";
            var page = new CoursesPage(courses, header, term);
            ctx.render("courses/index.jte", model("page", page));
        });

        app.get(NamedRoutes.buildUsersPath(), UsersController::build);

        app.post(NamedRoutes.usersPath(), ctx -> {
            var name = ctx.formParam("name").trim();
            var email = ctx.formParam("email").trim().toLowerCase();

            try {
                var passwordConfirmation = ctx.formParam("passwordConfirmation");
                var password = ctx.formParamAsClass("password", String.class)
                        .check(value -> value.equals(passwordConfirmation), "Пароли не совпадают")
                        .get();
                var user = new User(name, email, password); // пароль корректен
                UserRepository.save(user);
                ctx.redirect("/users");
            } catch (ValidationException e) { // пароль НЕ корректен
                var page = new BuildUserPage(name, email, e.getErrors());
                ctx.render("users/build.jte", model("page", page));
            }
        });

        /*
        Как использовать сессии
         */
        // Отображение формы авторизации (login)
        app.get(NamedRoutes.buildSessionPath(), SessionsController::build);
        // Процесс авторизации (log in)
        app.post(NamedRoutes.sessionsPath(), SessionsController::create);
        // Процесс выхода из аккаунта (log out)
        app.delete(NamedRoutes.sessionsPath(), SessionsController::destroy);


        app.start(7070); // Стартуем веб-сервер
    }
}
