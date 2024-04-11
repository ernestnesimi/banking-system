package bankingsystem.management.Controllers;

import bankingsystem.management.Models.*;
import bankingsystem.management.Services.UserService;
import bankingsystem.management.securityConfiguration.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticateController {

    private final JwtService jwtService;

    private final UserService userService;

    public AuthenticateController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

//    @PostMapping("/signup")
//    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
//        UserDTO registeredUser = userService.createUser(userDTO);
//
//        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
//    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        User authenticatedUser = userService.authenticate(authenticationRequest);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse(jwtToken);

        return new ResponseEntity<>(authenticationResponse,HttpStatus.OK);
    }

}
