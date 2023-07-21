# JWT-Token-authentication

## Creazione delle classi di entità:

User: Rappresenta un utente dell'applicazione con attributi come username, password, ecc.
Role: Rappresenta un ruolo dell'utente con attributi come il nome del ruolo.
 
## Creazione dei repository per le classi di entità:

 
## Implementazione del service:

UserService: Implementa l'interfaccia UserDetailsService di Spring Security per gestire la registrazione e l'autenticazione degli utenti.

## Configurazione delle classi per la sicurezza: **classe config**

- Securityconfiguration: Configura Spring Security per gestire l'autenticazione e l'autorizzazione.
- Definire il bean PasswordEncoder per crittografare le password degli utenti.
- Definire il bean AuthenticationManager per gestire l'autenticazione degli utenti.
- Definire il bean JwtDecoder per decodificare i token JWT ricevuti nelle richieste.
- Definire il bean JwtEncoder per creare e firmare i token JWT da inviare come risposta.
- Definire il bean JwtAuthenticationConverter per convertire le autorizzazioni (ruoli) dai token JWT in oggetti GrantedAuthority.
- Configurare le regole di sicurezza per le richieste attraverso http.authorizeRequests().
- Impostare la gestione delle sessioni come "STATELESS" per evitare l'uso di sessioni.
-Impostare il filtro di sicurezza JWT con .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))).

## Generazione delle coppie di chiavi RSA per la firma e la verifica dei token JWT:

-RSAKeyProperties: Classe per generare una coppia di chiavi RSA utilizzando il builder pattern.

## Creazione di un controller per la registrazione e l'autenticazione degli utenti:

## Creazione di un servizio per la generazione e la gestione dei token JWT:

 

 
