package bankingsystem.management.Services;

import bankingsystem.management.Controllers.LogController;
import bankingsystem.management.Models.*;
import bankingsystem.management.Repository.RoleRepository;
import bankingsystem.management.Repository.UserRepository;

import bankingsystem.management.securityConfiguration.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserService {
     private final UserRepository userRepository;

     private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
     Logger logger = LoggerFactory.getLogger(LogController.class);

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getByUserName(String  username){
        return userRepository.findByUsername(username).orElseThrow();
    }

    public UserDTO createUser(UserDTO userDTO){
        if(userRepository.findByUsername(userDTO.getUsername()).isPresent()){
            logger.error("User already exist");
        }
        else {
            userRepository.save(toEntity(userDTO));
            logger.info("User created successfully");
        }
        return userDTO;
    }

    public void updateUser(UserDTO userDTO){
        User user = userRepository.findByUsername(userDTO.getUsername()).orElseThrow();
        Role role = roleRepository.findByName(userDTO.getRole()).orElseThrow();
        user.setId(user.getId());
        user.setPassword(userDTO.getPassword());
        user.setRole(role);
        userRepository.save(user);
        logger.info("User Update successfully");

    }

    public void deleteUser(Long id){
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
        else {
            logger.warn("User doesn't exist");
        }
    }

//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getUsername(),
//                        request.getPassword()
//                )
//        );
//        var user = userRepository.findByUsername(request.getUsername())
//                .orElseThrow();
//        var jwtToken = jwtService.generateToken(user);
//
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//    }

    public User authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        return userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow();
    }

    private User toEntity(UserDTO userDTO) {
        Role role = roleRepository.findByName(userDTO.getRole()).orElseThrow();
        return User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(role)
                .build();
    }

    private UserDTO toDTO(User user){
        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole().getName())
                .build();
    }

}
