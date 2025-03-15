package com.UdemyCource.Accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = "name cannot be empty")
    @Size(min = 5, max = 50, message = "Name cannot be more then 50 or less than 5")
    private String name;

    @NotEmpty(message = "email cannot be empty")
    @Email(message = "entered email is not in proper format")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
