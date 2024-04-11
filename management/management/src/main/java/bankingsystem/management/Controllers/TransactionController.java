package bankingsystem.management.Controllers;

import bankingsystem.management.Models.TransactionDepositDTO;
import bankingsystem.management.Models.TransactionTransferDTO;
import bankingsystem.management.Models.TransactionWithdrawDTO;
import bankingsystem.management.Services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping(value="/withdraw")
    public ResponseEntity<Void> withdraw(@RequestBody TransactionWithdrawDTO transactionDTO){
        transactionService.withdraw(transactionDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping(value="/deposit")
    public ResponseEntity<Void> deposit(@RequestBody TransactionDepositDTO transactionDepositDTO){
        transactionService.deposit(transactionDepositDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping(value= "/transfer")
    public ResponseEntity<Void> transfer(@RequestBody TransactionTransferDTO transactionTransferDTO){
        transactionService.transfer(transactionTransferDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
