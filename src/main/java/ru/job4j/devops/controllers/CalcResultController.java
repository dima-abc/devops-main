package ru.job4j.devops.controllers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.job4j.devops.dto.TwoArgs;
import ru.job4j.devops.models.Result;
import ru.job4j.devops.models.enums.CalcType;
import ru.job4j.devops.service.ResultService;

@RestController
@RequestMapping("calc/result")
@RequiredArgsConstructor
public class CalcResultController {
  private final ResultService resultService;

  @PostMapping("/summarise")
  @ResponseStatus(HttpStatus.OK)
  public Result summarise(@RequestBody TwoArgs twoArgs) {
    var result = new Result();
    result.setFirstArg(BigDecimal.valueOf(twoArgs.getFirst()));
    result.setSecondArg(BigDecimal.valueOf(twoArgs.getSecond()));
    result.setResult(BigDecimal.valueOf(twoArgs.getFirst() + twoArgs.getSecond()));
    result.setOperation(CalcType.SUMMATION);
    result.setCreateDate(LocalDateTime.now());
    resultService.save(result);
    return result;
  }

  @GetMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public List<Result> logs() {
    return resultService.findAll();
  }
}
