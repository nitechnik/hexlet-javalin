package org.example.hexlet.util;

public class NamedRoutes {
    // Маршрут главной страницы
    public static String rootPath() {
        return "/";
    }

    // Маршрут пользователей
    public static String usersPath() {
        return "/users";
    }

    // Маршрут формы создания пользователя
    public static String buildUsersPath() {
        return "/users/build";
    }

    // Маршрут списка курсов
    public static String coursesPath() {
        return "/courses";
    }

    // Это нужно, чтобы не преобразовывать типы снаружи
    public static String coursePath(Long id) {
        return coursePath(String.valueOf(id));
    }

    public static String coursePath(String id) {
        return "/courses/" + id;
    }

    public static String sessionsPath() { return "/sessions"; }

    public static String buildSessionPath() {return "/sessions/build";}
}
