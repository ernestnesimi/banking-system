package bankingsystem.management.Controllers;

import bankingsystem.management.Models.UserDTO;
import bankingsystem.management.Services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final UserService userService;

    public ClientController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('BANKER')")
    @GetMapping("/all")
    public ResponseEntity<UserDTO> getAll(){
        userService.getAllUsers();
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('BANKER')")
    @PostMapping("/create")
    public ResponseEntity<Void> createClient(@RequestBody UserDTO userDTO){
        userService.createUser(userDTO);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.CREATED);

    }
    @PreAuthorize("hasRole('BANKER')")
    @PostMapping("/update")
    public ResponseEntity<Void> updateClient(@RequestBody UserDTO userDTO){
        userService.updateUser(userDTO);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.CREATED);

    }
    @PreAuthorize("hasRole('BANKER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClient(@RequestParam Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.CREATED);

    }
}
