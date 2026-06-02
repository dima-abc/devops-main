package ru.job4j.devops.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ответ контроллера.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private double value;
}
