package ru.job4j.devops.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import ru.job4j.devops.models.enums.CalcType;

@Data
@Entity
@Table(name = "calc_events")
public class CalcEvent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Column(name = "first")
  private BigDecimal first;

  @Column(name = "second")
  private BigDecimal seconds;

  @Column(name = "result")
  private BigDecimal result;

  @Column(name = "create_date")
  private LocalDateTime createDate;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private CalcType type;
}
