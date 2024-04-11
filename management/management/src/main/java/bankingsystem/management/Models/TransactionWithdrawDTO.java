package bankingsystem.management.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
@Builder
public class TransactionWithdrawDTO {


    private Long bankAccountId;
    private Double amount;
    @Schema(example="EUR")
    private String currency;
    @Schema(example="WITHDRAW")
    private String type;
}
