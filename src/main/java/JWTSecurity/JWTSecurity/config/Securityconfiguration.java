package JWTSecurity.JWTSecurity.config;

 import JWTSecurity.JWTSecurity.jwt.RSAKeyProperties;
 import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Securityconfiguration {
   private final RSAKeyProperties keys; // x accedere alle key che hai impostato
   public Securityconfiguration(RSAKeyProperties keys) {
       this.keys = keys;
   }

   @Bean
   public JwtDecoder jwtDecoder(){ // operazione di decodifica delle public key
       return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
   }

   @Bean
   public JwtEncoder jwtEncoder(){ // crei delle coppie di chiavi criptografate
       JWK jwk= new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build(); // qua vengono bildate assieme la key pubblica e privata che verranno confrontate nel momento del login
       JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk)); // le chiavi non saranno modificabili
       return new NimbusJwtEncoder(jwks);
    /*

     */
   }

   @Bean
   public JwtAuthenticationConverter jwtAuthenticationConverter(){ // viene usato x ottenere le autorizzazioni dal token le converte e imposta un prefisso
       JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter(); //  Inizialmente, viene creato un oggetto JwtGrantedAuthoritiesConverter, che è una classe fornita da Spring Security per convertire le autorizzazioni (ruoli) presenti nel token JWT in oggetti GrantedAuthority perche permette di capire quali sono i ruoli associati all'utente perche nella creazione del token abbiamo messo Roles
       jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles"); // Qui si imposta il nome del claim nel token JWT che contiene le informazioni sui ruoli dell'utente. In questo caso, il claim "roles" viene utilizzato per specificare i ruoli dell'utente nel token JWT.
       jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_"); // qua converti gli oggetti da es: admin a ROLE_admin
       JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
       jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
       return jwtConverter;
       /*
       Il convertitore JwtGrantedAuthoritiesConverter serve principalmente per estrarre i ruoli dell'utente dal token JWT e convertirli in oggetti GrantedAuthority che Spring Security può utilizzare per la gestione dell'autorizzazione.
       il prefisso "ROLE_" viene aggiunto al nome dei ruoli durante la creazione degli oggetti GrantedAuthority. Questo è fatto per distinguere chiaramente che il valore rappresenta un ruolo e per evitare ambiguità con altri tipi di autorizzazioni.

        Ad esempio, se nel token JWT viene incluso il ruolo "ADMIN", il convertitore aggiungerà automaticamente il prefisso "ROLE_" e il risultato sarà "ROLE_ADMIN". Lo stesso vale per il ruolo "USER", che verrà convertito in "ROLE_USER".
        Questa convenzione è importante perché Spring Security si aspetta che i nomi dei ruoli siano preceduti da "ROLE_" quando verifica l'accesso alle risorse protette. Quando si configurano le regole di sicurezza nell'applicazione, è comune utilizzare i nomi dei ruoli con il prefisso "ROLE_" in modo da specificare chiaramente il tipo di autorizzazione.

        Per esempio, in una configurazione di sicurezza Spring, si potrebbe avere qualcosa del genere:
        http.authorizeRequests()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/user/**").hasRole("USER")
            .anyRequest().authenticated()
        */
   }


   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
       return   http
               .csrf(csfr->csfr.disable())
               .authorizeHttpRequests(auth-> {
                   auth.requestMatchers("/utente/**").permitAll(); //  Questa riga indica che qualsiasi richiesta che inizia con "/utente/" sarà permessa a tutti senza richiedere un'autenticazione. In altre parole, qualsiasi utente, autenticato o meno, potrà accedere alle risorse sotto "/utente/".
                   auth.requestMatchers("/admin/**").hasRole("ADMIN"); // Questa riga significa che qualsiasi richiesta che inizia con "/admin/" richiederà l'autenticazione e solo gli utenti con il ruolo "ADMIN" potranno accedere a queste risorse. Gli utenti con altri ruoli o senza autenticazione riceveranno un errore di accesso negato.
                   auth.requestMatchers("/user/**").hasAnyRole("ADMIN","USER"); // Questa riga richiede l'autenticazione per le richieste che iniziano con "/user/". Gli utenti con i ruoli "ADMIN" o "USER" saranno autorizzati ad accedere a queste risorse.
                   auth.anyRequest().authenticated(); // questa riga indica che tutte le altre richieste (quelle che non rientrano nei percorsi specificati sopra) richiederanno un'autenticazione. Cioe dovrai esserti autenticato prima di fare questa chiamata
               }) .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter( jwtAuthenticationConverter())))  // Qui stai dicendo a Spring Security che le richieste saranno protette utilizzando token JWT e stai configurando il convertitore JwtAuthenticationConverter che abbiamo discusso in precedenza per estrarre le autorizzazioni (ruoli) dal token JWT.
                  .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //imposta la gestione delle sessioni come "STATELESS". Questo significa che non verrà creata alcuna sessione per l'utente. La sicurezza basata sulle sessioni non è utilizzata in questo caso perché si sta utilizzando l'autenticazione basata su token JWT, che è stateless (senza stato)
                  .build();


   }


   @Bean // questo bean cripta le password
   public PasswordEncoder passwordEncoder(){
       return new BCryptPasswordEncoder();
   }

   @Bean // è un componente fondamentale di Spring Security, utilizzato per gestire il processo di autenticazione degli utenti all'interno dell'applicazione. Viene spesso utilizzato per eseguire operazioni come il controllo delle credenziali e la gestione delle autorizzazioni degli utenti durante il processo di login e autenticazione.
   public AuthenticationManager authManager(UserDetailsService detailsService){ // in questo caso l'istanza di UserDetailService è la stessa che fai implementare nel service dell'user perche si occupera' di interpellare il DB per chiedere i dati dell'utenti
       DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
       daoAuthenticationProvider.setUserDetailsService(detailsService);
       daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
       return new ProviderManager(daoAuthenticationProvider);
       /*
       L'AuthenticationManager è uno degli elementi chiave di Spring Security e svolge un ruolo cruciale nel processo di autenticazione degli utenti. Quando un utente tenta di accedere o autenticarsi nell'applicazione, l'AuthenticationManager viene coinvolto per verificare le credenziali dell'utente.

        L'AuthenticationProvider è responsabile di verificare le credenziali dell'utente rispetto ai dati memorizzati nel database o in un altro sistema di autenticazione.
        L'AuthenticationProvider pertinente viene selezionato in base al tipo di autenticazione richiesto (ad esempio, autenticazione HTTP Basic, form-based, token, ecc.).
        L'AuthenticationProvider confronta le credenziali fornite con quelle memorizzate per l'utente corrispondente (generalmente ottenuto dal UserDetailsService). Se le credenziali sono corrette, l'utente viene autenticato con successo.
        */
   }

}
