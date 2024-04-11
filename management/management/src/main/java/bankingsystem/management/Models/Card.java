package bankingsystem.management.Models;

import jakarta.persistence.*;
import lombok.Data;


@Data
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String cardType;
    @Column
    private Double monthlySalary;
    @Column
    private Boolean approved;
    @Column
    private String disapprovalReason;

}
