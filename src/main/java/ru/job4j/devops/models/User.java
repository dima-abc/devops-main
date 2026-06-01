package ru.job4j.devops.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username")
  private String userName;

  @Column(name = "first_arg")
  private BigDecimal firstArg;

  @Column(name = "second_arg")
  private BigDecimal secondArg;

  @Column(name = "result")
  private BigDecimal result;

  @CreatedDate
  @Column(name = "create_date", updatable = false)
  private LocalDateTime createDate;
}
