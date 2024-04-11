package bankingsystem.management.Services;

import bankingsystem.management.Controllers.LogController;
import bankingsystem.management.Models.BankAccount;
import bankingsystem.management.Models.BankAccountDTO;
import bankingsystem.management.Repository.BankAccountRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    Logger logger = LoggerFactory.getLogger(LogController.class);

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<BankAccount> getAllBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        return bankAccounts;
    }
    public void createNewBankAccount(BankAccountDTO accountDTO) {
        BankAccount bankAccount = toEntity(accountDTO);
        bankAccountRepository.save(bankAccount);
    }

    public void updateBankAccount(BankAccountDTO accountDTO) {
        BankAccount bankAccount = new BankAccount();
        if (bankAccountRepository.findByIban(accountDTO.getIban()).isPresent()){
            bankAccount=bankAccountRepository.findByIban(accountDTO.getIban()).get();
        }

        bankAccount.setBalance(accountDTO.getBalance());
        bankAccount.setActive(accountDTO.getActive());
        bankAccountRepository.save(bankAccount);
    }

    public void deleteBankAccount(Long id) {
        bankAccountRepository.deleteById(id);

    }

    private BankAccount toEntity(BankAccountDTO accountDTO) {

        return BankAccount.builder()
                .iban(accountDTO.getIban())
                .currency(accountDTO.getCurrency())
                .balance(accountDTO.getBalance())
                .active(accountDTO.getActive())
                .build();
    }


}
