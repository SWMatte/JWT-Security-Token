package JWTSecurity.JWTSecurity.models.DTO;


import JWTSecurity.JWTSecurity.models.Customer;

public class LoginResponseDTO { // l'utente loggato ricevera' i dati del customer e la string jwt contenente i ltoken

    private Customer customer;
    private String jwt;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(Customer customer, String jwt) {
        this.customer = customer;
        this.jwt = jwt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
