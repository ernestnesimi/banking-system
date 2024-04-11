package bankingsystem.management.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Builder
@Data
public class BankAccountDTO {

    @NotEmpty(message = "Iban most not be empty")
    private String iban;
    @Schema(example="EUR")
    private String currency;
    private Double balance;
    private Boolean active;
}
