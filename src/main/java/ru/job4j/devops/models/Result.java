package ru.job4j.devops.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.job4j.devops.models.enums.CalcType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "results")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "first_arg")
    private BigDecimal firstArg;

    @Column(name = "second_arg")
    private BigDecimal secondArg;

    @Column(name = "result")
    private BigDecimal result;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation")
    private CalcType operation;

    @Column(name = "create_date")
    private LocalDateTime createDate;
}
