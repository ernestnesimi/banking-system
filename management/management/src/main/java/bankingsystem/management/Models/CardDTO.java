package bankingsystem.management.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CardDTO {

    @Schema(example="DEBIT")
    private String cardType;
    private Long accountId;
    private Double monthlySalary;
}
