package ru.job4j.devops.controllers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import ru.job4j.devops.dto.TwoArgs;
import ru.job4j.devops.models.enums.CalcType;
import ru.job4j.devops.service.fake.ResultFakeService;

class CalcResultControllerTest {
  private final CalcResultController calcResultController =
      new CalcResultController(new ResultFakeService());

  @Test
  void summarise() {
    // GIVEN
    var input = new TwoArgs(10, 20);
    // WHEN
    var actual = calcResultController.summarise(input);
    // THEN
    assertThat(actual)
        .satisfies(
            result -> {
              assertThat(result.getId()).isNotNull();
              assertThat(result.getFirstArg().doubleValue()).isEqualTo(input.getFirst());
              assertThat(result.getSecondArg().doubleValue()).isEqualTo(input.getSecond());
              assertThat(result.getResult().doubleValue())
                  .isEqualTo(input.getFirst() + input.getSecond());
              assertThat(result.getOperation()).isEqualTo(CalcType.SUMMATION);
              assertThat(result.getCreateDate().truncatedTo(ChronoUnit.MINUTES))
                  .isEqualTo(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
            });
  }
}
