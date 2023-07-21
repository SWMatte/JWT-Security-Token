package JWTSecurity.JWTSecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class TokenService {
    /*
    Il Token Service è responsabile per la generazione del token JWT una volta che l'autenticazione dell'utente ha avuto successo. L'utente ha fornito le proprie credenziali durante il processo di login e queste credenziali sono state verificate e validate con successo da parte dell'AuthenticationManager. Quando l'autenticazione ha successo, l'oggetto Authentication viene popolato con le informazioni dell'utente e i relativi ruoli.
     */

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    public String generateJwt(Authentication auth){ // metodo che genererà il token da utilizzare per loggarsi
        Instant now = Instant.now();

        String scope = auth.getAuthorities(). //scope è una stringa che rappresenta le autorizzazioni dell'utente in base ai ruoli associati. Le autorizzazioni vengono ottenute dalla Authentication tramite il metodo getAuthorities(). Questo metodo restituisce una collezione di oggetti GrantedAuthority, che rappresentano i ruoli dell'utente.
                stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" ")); // Lo stream di GrantedAuthority è mappato in una stringa contenente i nomi dei ruoli, utilizzando il metodo getAuthority() per ottenere il nome del ruolo. Questi nomi dei ruoli vengono quindi concatenati in una sola stringa utilizzando Collectors.joining(" "). L'uso dello spazio come delimitatore nella stringa è una convenzione comune per separare le autorizzazioni nel token JWT.

        JwtClaimsSet claims = JwtClaimsSet.builder() // JwtClaimsSet rappresenta i claims (dati) da includere nel token JWT. I claims sono coppie chiave-valore che contengono informazioni sull'utente e altri dati utili per l'applicazione.
                .issuer("self")              //Il claim "issuer" identifica l'emittente del token, ovvero l'entità che ha creato il token. Nell'esempio, viene impostato a "self" per indicare che il token è stato emesso dall'applicazione stessa.
                .issuedAt(now)               // I Il claim "issuedAt" (emissione) indica la data e l'ora in cui il token è stato emesso.
                .subject(auth.getName())     // Imposta il "subject" del token con il nome dell'utente autenticato
                .claim("roles", scope)       // Il metodo claim() consente di aggiungere claims personalizzati al token. In questo caso, viene aggiunto un claim "roles" con valore scope, che rappresenta le autorizzazioni dell'utente nel formato di una stringa contenente i ruoli separati da spazi.
                .build();
        // Restituisce il token JWT generato utilizzando la chiave privata per la firma del token
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
