package JWTSecurity.JWTSecurity.service;


 import JWTSecurity.JWTSecurity.repositories.ICustomerRepository;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service // x il basic auth || ma serve anche per JWT per recuperare i dati dell'utente dal repository
public class CustomerService implements UserDetailsService { // avra la funzionalita' di interagire con il DB x caricare i dettagli dell utente richiesti da spring x autentificarlo

    @Autowired
    private PasswordEncoder encoder; // usato nel metodo x criptare pasw

    @Autowired
    ICustomerRepository customerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Sono nel service del cliente");

            return customerRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("username non trovato"));



/*
        if (username.equals("Matteo")) {

            Set<Role> roles = new HashSet<>();
            roles.add(new Role("USER"));   // se l'username  inserito combaciava con il nome matteo venva creata un utenza con passw codificata

            return new Customer(username, encoder.encode("password"), roles);
        } else {
            throw new UsernameNotFoundException("");
        }
*/

    }



}
