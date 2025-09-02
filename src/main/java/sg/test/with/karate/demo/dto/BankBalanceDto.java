package sg.test.with.karate.demo.dto;


import lombok.Data;

@Data
public class BankBalanceDto {

    private String customerId;
    private double balance;
    private String currency;
    private String asOf;

}
