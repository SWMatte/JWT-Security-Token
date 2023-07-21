package JWTSecurity.JWTSecurity.repositories;

 import JWTSecurity.JWTSecurity.models.Customer;
 import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICustomerRepository extends JpaRepository<Customer,Integer> {


    Optional<Customer> findByUsername(String username);
}
