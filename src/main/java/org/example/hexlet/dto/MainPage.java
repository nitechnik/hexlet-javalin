package org.example.hexlet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MainPage {
    private Boolean visited;
    private String currentUser; // для авторизованных пользователей

    public Boolean isVisited() {
        return visited;
    }
}
