package bankingsystem.management.Controllers;

import bankingsystem.management.Models.AuthenticationRequest;
import bankingsystem.management.Models.AuthenticationResponse;
import bankingsystem.management.Models.RegisterRequest;
import bankingsystem.management.Models.UserDTO;
import bankingsystem.management.Services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService service) {
        this.userService = service;
    }

//    @PostMapping("/register")
//    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
//        userService.createUser(userDTO);
//        return new ResponseEntity<>(userDTO,HttpStatus.CREATED);
//    }
//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthenticationResponse> authenticate(
//            @RequestBody AuthenticationRequest request
//    ) {
//        return ResponseEntity.ok(userService.authenticate(request));
//    }
}
