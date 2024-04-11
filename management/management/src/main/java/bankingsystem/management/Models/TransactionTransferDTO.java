package bankingsystem.management.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionTransferDTO {

    private Long fromAccount;

    private String iban;
    private Double amount;
    @Schema(example="EUR")
    private String currency;
    @Schema(example="TRANSFER")
    private String type;
}
