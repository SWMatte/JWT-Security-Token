package JWTSecurity.JWTSecurity.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Customer implements UserDetails { // rappresenta l'utente che prova ad accedere all app
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCustomer;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "idCustomer")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> authorities;



    public Customer(){   // qua definisci un costruttore vuoto della classe che forma un nuovo hashset
        this.authorities= new HashSet<Role>();
    }

    public Customer(String username, String password, Set<Role> authorities) { // costruttore pieno
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    // definisco i metodi che vengono implementati da UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }




    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
