package sg.test.with.karate.demo.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {
    private Long id;
    private String name;
    private BankBalanceDto bankBalanceDto;
}
