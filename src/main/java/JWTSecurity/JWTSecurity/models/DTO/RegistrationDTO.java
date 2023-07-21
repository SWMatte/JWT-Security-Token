package JWTSecurity.JWTSecurity.models.DTO;

public class RegistrationDTO {
    private String username;
    private String password;

    public RegistrationDTO() {
    }

    public RegistrationDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
