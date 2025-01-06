package org.example.hexlet.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public final class Course {
    private Long id; // идентификатор

    @ToString.Include
    private String name; // название
    private String description; // описание

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
