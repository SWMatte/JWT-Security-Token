package JWTSecurity.JWTSecurity.controller;

import JWTSecurity.JWTSecurity.models.Customer;
import JWTSecurity.JWTSecurity.models.DTO.LoginResponseDTO;
import JWTSecurity.JWTSecurity.models.DTO.RegistrationDTO;
import JWTSecurity.JWTSecurity.service.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utente")
public class SignUpController {

    @Autowired
    AuthenticationService signUpService;


    @PostMapping("/register")
    public Customer registrazione(@RequestBody RegistrationDTO body){

        return signUpService.registerCustomer(body.getUsername(),body.getPassword());
    }



    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body){
        return signUpService.loginUser(body.getUsername(), body.getPassword());
    }
}