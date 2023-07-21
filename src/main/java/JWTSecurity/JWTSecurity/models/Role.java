package JWTSecurity.JWTSecurity.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity   // la classe rappresenta i ruoli che hanno le persone
@Table(name = "roles")
public class Role implements GrantedAuthority { // rappresenta questa interfaccia il ruolo che l'utente avra' nell applicazione

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "role_id")
    private Integer roleId;
    private String authority;


    public Role() {
    }

    public Role(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
