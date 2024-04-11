package bankingsystem.management.Controllers;

import bankingsystem.management.Models.BankAccount;
import bankingsystem.management.Models.BankAccountDTO;
import bankingsystem.management.Services.BankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bankAccount")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    Logger logger = LoggerFactory.getLogger(LogController.class);

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping(value="/all", produces = { "application/json" })
    public ResponseEntity<List<BankAccount>> getAllBankAccounts(){
        List<BankAccount> bankAccounts = bankAccountService.getAllBankAccounts();
        return new ResponseEntity<>(bankAccounts,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping(value="/new", produces = { "application/json" })
    public ResponseEntity<BankAccountDTO> createNewBankAccount(@RequestBody BankAccountDTO accountDTO){

        if(accountDTO != null){
            bankAccountService.createNewBankAccount(accountDTO);
        }
        return new ResponseEntity<>(accountDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping(value="/update", produces = { "application/json" })
    public ResponseEntity<BankAccountDTO> updateBankAccount(@RequestBody BankAccountDTO accountDTO){

        if(accountDTO != null){
            bankAccountService.updateBankAccount(accountDTO);
        }
        else{
            logger.warn("Can't find account with this parameters.");
        }
        return new ResponseEntity<>(accountDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('BANKER')")
    @DeleteMapping(value="/delete/{id}")
    public ResponseEntity<Void> deleteBankAccount(@RequestParam Long id){
        if(id != null){
            bankAccountService.deleteBankAccount(id);
        }
        else {
            logger.error("Account with the given iban doesn't exist.");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
