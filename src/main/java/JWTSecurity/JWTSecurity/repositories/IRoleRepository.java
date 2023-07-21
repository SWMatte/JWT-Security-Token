package JWTSecurity.JWTSecurity.repositories;

 import JWTSecurity.JWTSecurity.models.Role;
 import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role,Integer> {


    Role findByAuthority(String authority);
}
