package com.UdemyCource.Accounts.dto;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class AccountsDto {

    private long AccountNumber;

    private String accountType;

    private String branchAddress;
}
