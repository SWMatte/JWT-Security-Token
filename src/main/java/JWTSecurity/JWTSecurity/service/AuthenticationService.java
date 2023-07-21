package JWTSecurity.JWTSecurity.service;




  import JWTSecurity.JWTSecurity.models.Customer;
  import JWTSecurity.JWTSecurity.models.DTO.LoginResponseDTO;
  import JWTSecurity.JWTSecurity.models.Role;
  import JWTSecurity.JWTSecurity.repositories.ICustomerRepository;
  import JWTSecurity.JWTSecurity.repositories.IRoleRepository;
  import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


@Service
@Transactional
public class AuthenticationService {

    @Autowired
    ICustomerRepository customerRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;


    public Customer registerCustomer(String username, String password){

            String encodedPassword= passwordEncoder.encode(password);
             Role userRole =   roleRepository.findByAuthority("ADMIN");
             Set<Role> authorities= new HashSet<>();
            authorities.add(userRole);
         return customerRepository.save(new Customer(username,encodedPassword,authorities ));
        }


    public LoginResponseDTO loginUser(String username, String password){ // quello che vedra' l'utente  dopo che inserisce i dati e questi sono corretti e la generazione del token che arriva tokenService
         try{
             System.out.println("Attempting login for user: " + username);
             Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,password)
            );

            String token = tokenService.generateJwt(auth);
            return new LoginResponseDTO(customerRepository.findByUsername(username).get(),token);
        }catch (AuthenticationException e){
             System.out.println("Login failed for user: " + username);
             return new LoginResponseDTO(null,null);
        }
    }


}
