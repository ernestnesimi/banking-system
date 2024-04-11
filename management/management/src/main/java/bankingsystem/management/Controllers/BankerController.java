package bankingsystem.management.Controllers;

import bankingsystem.management.Models.UserDTO;
import bankingsystem.management.Services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banker")
public class BankerController {

    private final UserService userService;

    public BankerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<UserDTO> getAll(){
        userService.getAllUsers();
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Void> createBanker(@RequestBody UserDTO userDTO){
        userService.createUser(userDTO);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public ResponseEntity<Void> updateBanker(@RequestBody UserDTO userDTO){
        userService.updateUser(userDTO);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBanker(@RequestParam Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);

    }
}
