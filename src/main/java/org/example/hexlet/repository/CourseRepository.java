package org.example.hexlet.repository;

import lombok.Getter;
import org.example.hexlet.model.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class CourseRepository {
    // Тип <T> зависит от того, с какой сущностью (entity) идёт работа в упражнении.
    private static final List<Course> entities = new ArrayList<Course>(); // entities – сущности

    public static void save(Course course) {
        // Формируется идентификатор
        course.setId((long) entities.size() + 1);
        entities.add(course);
    }

    public static List<Course> search(String term) {
        return entities.stream().filter(entity -> entity.getName().startsWith(term)).toList();
    }

    public static Optional<Course> find(Long id) {
        return entities.stream().filter(entity -> entity.getId().equals(id)).findAny();
    }
}
