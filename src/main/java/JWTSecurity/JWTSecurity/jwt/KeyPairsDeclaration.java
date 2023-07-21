package JWTSecurity.JWTSecurity.jwt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class KeyPairsDeclaration {

    public static KeyPair generateRsaKey(){
        KeyPair keyPair; //coppia di key asimmetrica  La chiave privata viene utilizzata per firmare o decifrare i dati, mentre la chiave pubblica viene utilizzata per verificare le firme o crittografare i dati.

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");// algoritmo di criptaggio
            keyPairGenerator.initialize(2048); //numero di caratteri usati x criptare
            keyPair= keyPairGenerator.generateKeyPair();

        }catch (Exception e){
            throw new IllegalStateException();
        }
        return keyPair;
    }


}
