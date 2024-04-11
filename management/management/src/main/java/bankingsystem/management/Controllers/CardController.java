package bankingsystem.management.Controllers;

import bankingsystem.management.Models.Card;
import bankingsystem.management.Models.CardApplication;
import bankingsystem.management.Models.CardApplicationDTO;
import bankingsystem.management.Models.CardDTO;
import bankingsystem.management.Services.BankAccountService;
import bankingsystem.management.Services.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;


    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    public ResponseEntity<List<CardApplication>> getAllCards() {
        List<CardApplication> cards = cardService.getAllCards();
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    //    @PostMapping("/new-cart")
//    public ResponseEntity<Void> newCartApplication(@RequestBody CardDTO cardDTO){
//       cardService.newCardApplication(cardDTO);
//    }
//
    @PreAuthorize("hasRole('BANKER')")
    @GetMapping("/card-request")
    public ResponseEntity<List<CardApplication>> getAllCardApplication() {
        List<CardApplication> cardRequest = cardService.getAllCards();
        return new ResponseEntity<>(cardRequest, HttpStatus.OK);
    }
//
//    @PostMapping("/approve-request")
//    public ResponseEntity<Void> approveCardResquest(@RequestBody CardRequestDTO cardRequestDTO){
//        cardService.approveCardRequest(cardRequestDTO);
//    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/debit")
    public ResponseEntity<CardApplication> applyForDebitCard(@RequestBody CardDTO cardDTO) {

        CardApplication application = cardService.applyForDebitCard(cardDTO);

        return new ResponseEntity<>(application, HttpStatus.OK);


}

    @PreAuthorize("hasRole('BANKER')")
    @PutMapping("/approve/{applicationId}")
    public ResponseEntity<CardApplication> approveCardApplication(@RequestBody CardApplicationDTO cardApplicationDTO) {

            CardApplication application = cardService.approveOrRejectCardApplication(cardApplicationDTO);

            return new ResponseEntity<>(application,HttpStatus.OK);

    }

}
