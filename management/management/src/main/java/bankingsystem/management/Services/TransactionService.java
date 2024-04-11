package bankingsystem.management.Services;

import bankingsystem.management.Controllers.LogController;
import bankingsystem.management.Models.*;
import bankingsystem.management.Repository.BankAccountRepository;
import bankingsystem.management.Repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final BankAccountRepository bankAccountRepository;
    Logger logger = LoggerFactory.getLogger(LogController.class);


    public TransactionService(TransactionRepository transactionRepository, BankAccountRepository bankAccountRepository) {
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    public void withdraw(TransactionWithdrawDTO transactionWithdrawDTO) {
        BankAccount bankAccount = new BankAccount();
        Transaction transaction = new Transaction();
        if(bankAccountRepository.findById(transactionWithdrawDTO.getBankAccountId()).isPresent()){
            bankAccount = bankAccountRepository.findById(transactionWithdrawDTO.getBankAccountId()).get();

            if (bankAccount.getBalance() < transactionWithdrawDTO.getAmount()) {
                        logger.error("Your Balance " + bankAccount.getBalance() + " is not enough to withdraw " + transactionWithdrawDTO.getAmount());
            }
            else {
                bankAccount.setBalance(bankAccount.getBalance() - transactionWithdrawDTO.getAmount());
                bankAccountRepository.save(bankAccount);
                transaction.setId(Transaction.builder().build().getId());
                transaction.setAmount(transactionWithdrawDTO.getAmount());
                transaction.setCurrency(transactionWithdrawDTO.getCurrency());
                transaction.setType(transactionWithdrawDTO.getType());
                transaction.setBankAccount(bankAccount);
                transactionRepository.save(transaction);
            }
        }

    }

    public void deposit(TransactionDepositDTO transactionDepositDTO) {
        BankAccount bankAccount = new BankAccount();
        Transaction transaction = new Transaction();
        if(bankAccountRepository.findById(transactionDepositDTO.getBankAccountId()).isPresent()){
            bankAccount = bankAccountRepository.findById(transactionDepositDTO.getBankAccountId()).get();

            if (transactionDepositDTO.getAmount() <= 0) {
                logger.error("Your deposit is equal or smaller than 0 " + bankAccount.getBalance() + "deposit can't be performed." );
            }else {
                bankAccount.setBalance(bankAccount.getBalance() + transactionDepositDTO.getAmount());
                bankAccountRepository.save(bankAccount);
                transaction.setAmount(transactionDepositDTO.getAmount());
                transaction.setCurrency(transactionDepositDTO.getCurrency());
                transaction.setType(transactionDepositDTO.getType());
                transaction.setBankAccount(bankAccount);
                transactionRepository.save(transaction);
            }
        }

    }
    private Transaction toTransactionEntity(TransactionWithdrawDTO transactionDTO) {
        BankAccount bankAccount = new BankAccount();
        if(bankAccountRepository.findById(transactionDTO.getBankAccountId()).isPresent()) {
            bankAccount = bankAccountRepository.findById(transactionDTO.getBankAccountId()).get();
        }
        Transaction transaction = Transaction.builder()
                .id(bankAccount.getId())
                .bankAccount(bankAccount)
                .amount(transactionDTO.getAmount())
                .currency(transactionDTO.getCurrency())
                .type(transactionDTO.getType())
                .build();
        return transaction;
    }

    private TransactionWithdrawDTO toTransactionDTO(Transaction transaction){
        BankAccount bankAccount = new BankAccount();
        if(bankAccountRepository.findById(transaction.getBankAccount().getId()).isPresent()) {
            bankAccount = bankAccountRepository.findById(transaction.getBankAccount().getId()).get();
        }
            TransactionWithdrawDTO transactionDTO = TransactionWithdrawDTO.builder()
                .bankAccountId(bankAccount.getId())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .type(transaction.getType())
                .build();

        return transactionDTO;
    }

    public void transfer(TransactionTransferDTO transactionTransferDTO) {
        BankAccount bankAccountFrom = bankAccountRepository.findById(transactionTransferDTO.getFromAccount()).get();
        BankAccount bankAccountTo = new BankAccount();
        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();

        if(bankAccountRepository.findByIban(transactionTransferDTO.getIban()).isPresent()){
            bankAccountTo = bankAccountRepository.findByIban(transactionTransferDTO.getIban()).get();
            if(bankAccountFrom.getBalance() >= transactionTransferDTO.getAmount()){
                bankAccountTo.setBalance(bankAccountTo.getBalance() + transactionTransferDTO.getAmount());
                bankAccountFrom.setBalance(bankAccountTo.getBalance() - transactionTransferDTO.getAmount());

                transaction1.setAmount(transactionTransferDTO.getAmount());
                transaction1.setCurrency(transactionTransferDTO.getCurrency());
                transaction1.setType("TRANSFER PLUS");
                transaction1.setBankAccount(bankAccountTo);
                transactionRepository.save(transaction1);

                transaction2.setAmount(transactionTransferDTO.getAmount());
                transaction2.setCurrency(transactionTransferDTO.getCurrency());
                transaction2.setType("TRANSFER MINUS");
                transaction2.setBankAccount(bankAccountFrom);
                transactionRepository.save(transaction2);
            }

        }
    }
}