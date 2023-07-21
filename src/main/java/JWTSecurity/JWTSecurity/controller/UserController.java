package JWTSecurity.JWTSecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class UserController {

@GetMapping("/user")
    public String helloThere(){
        return "Hello by User";
    }
}
