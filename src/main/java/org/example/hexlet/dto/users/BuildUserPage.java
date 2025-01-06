package org.example.hexlet.dto.users;
/*
Дата-класс, который передаёт в шаблон данные формы и ошибки.
Ошибки передаются в виде объекта, который формирует Javalin в случае ошибки валидации.
 */
import io.javalin.validation.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BuildUserPage {
    private String name;
    private String email;
    // объект, который формирует Javalin в случае ошибки валидации
    private Map<String, List<ValidationError<Object>>> errors;
}
