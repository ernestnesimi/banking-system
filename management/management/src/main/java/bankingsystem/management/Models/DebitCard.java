package bankingsystem.management.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class DebitCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private BankAccount connectedAccount;
}
