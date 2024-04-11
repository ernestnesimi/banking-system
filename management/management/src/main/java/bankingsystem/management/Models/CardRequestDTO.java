package bankingsystem.management.Models;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CardRequestDTO {

    private Long BankAccountId;

    private Boolean approved;

    private String disapprovalReason;
}
