package ru.job4j.devops.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.devops.dto.Result;
import ru.job4j.devops.dto.TwoArgs;

/**
 * Контроллер.
 */
@RestController
@RequestMapping("calc")
public class CalcController {
    /**
     * Сложение.
     *
     * @param twoArgs {@link TwoArgs}
     * @return {@link ResponseEntity}
     */
    @PostMapping("summarise")
    public ResponseEntity<Result> summarise(@RequestBody TwoArgs twoArgs) {
        var result = twoArgs.getFirst() + twoArgs.getSecond();
        return ResponseEntity.ok(new Result(result));
    }

    /**
     * Умножение.
     *
     * @param twoArgs {@link TwoArgs}
     * @return {@link ResponseEntity}
     */
    @PostMapping("times")
    public ResponseEntity<Result> times(@RequestBody TwoArgs twoArgs) {
        var result = twoArgs.getFirst() * twoArgs.getSecond();
        return ResponseEntity.ok(new Result(result));
    }
}
