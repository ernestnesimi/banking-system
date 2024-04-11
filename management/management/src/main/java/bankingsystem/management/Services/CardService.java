package bankingsystem.management.Services;

import bankingsystem.management.Controllers.LogController;
import bankingsystem.management.Models.*;
import bankingsystem.management.Repository.BankAccountRepository;
import bankingsystem.management.Repository.CardApplicationRepository;
import bankingsystem.management.Repository.DebitCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CardService {
    private final BankAccountRepository bankAccountRepository;
    private final CardApplicationRepository cardApplicationRepository;
    private final DebitCardRepository debitCardRepository;
    Logger logger = LoggerFactory.getLogger(LogController.class);


    public CardService( BankAccountRepository bankAccountRepository, CardApplicationRepository cardApplicationRepository, DebitCardRepository debitCardRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.cardApplicationRepository = cardApplicationRepository;

        this.debitCardRepository = debitCardRepository;
    }

    public List<CardApplication> getAllCards() {
        return cardApplicationRepository.findAll();
    }

//    public void newCardApplication(CardDTO cardDTO) {
//        if(bankAccountRepository.findByUserName("").isPresent()){
//            if(cardDTO.getMonthlySalary()>= 500){
//
//            }
//            else{
//                logger.error("Monthly salary is less than 500: ",cardDTO.getMonthlySalary());
//            }
//
//        }
//    }

//    public List<CardReq> getCardRequests() {
//        List<CardRequest> cardRequest = cardRequestRepository.findAll();
//        return cardRequest;
//    }
//
//    public void approveCardRequest(CardRequestDTO cardRequestDTO) {
//        BankAccount bankAccount = new BankAccount();
//        if (bankAccountRepository.existsById(cardRequestDTO.getBankAccountId())){
//            bankAccount = bankAccountRepository.findById(cardRequestDTO.getBankAccountId()).get();
//        }
//        CardRequest card = new CardRequest();
//        card.setBankAccount(bankAccount);
//        card.setApproved(cardRequestDTO.getApproved());
//        if (!card.getApproved()) {
//            card.setDisapprovalReason(cardRequestDTO.getDisapprovalReason());
//        }
//        cardRequestRepository.save(card);
//    }

    public CardApplication applyForDebitCard(CardDTO cardDTO) {
        CardApplication application = new CardApplication();
        if (cardDTO.getMonthlySalary() < 500) {
            logger.error("Monthly salary must be at least 500€.");
        }
        else{
            application.setAccountId(cardDTO.getAccountId());
            application.setMonthlySalary(cardDTO.getMonthlySalary());
            return cardApplicationRepository.save(application);
        }

        return application;
    }

    public CardApplication approveOrRejectCardApplication(CardApplicationDTO cardApplicationDTO) {
        DebitCard debitCard = new DebitCard();
        BankAccount bankAccount = new BankAccount();
        CardApplication application = cardApplicationRepository.findById(cardApplicationDTO.getId()).orElseThrow();
        application.setApproved(cardApplicationDTO.isApproved());
        if (!cardApplicationDTO.isApproved()) {
            application.setRejectionReason(cardApplicationDTO.getRejectionReason());
        } else {
            bankAccount = bankAccountRepository.findById(cardApplicationDTO.getAccountId()).orElseThrow();
            debitCard.setConnectedAccount(bankAccount);
            debitCardRepository.save(debitCard);
            cardApplicationRepository.save(application);
        }
        return application;
    }
}
