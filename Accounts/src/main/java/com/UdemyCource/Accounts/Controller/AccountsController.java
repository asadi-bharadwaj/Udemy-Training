package com.UdemyCource.Accounts.Controller;

import com.UdemyCource.Accounts.Constants.AccountsConstants;
import com.UdemyCource.Accounts.Service.IAccountsService;
import com.UdemyCource.Accounts.dto.CustomerDto;
import com.UdemyCource.Accounts.dto.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor
public class AccountsController {

    private IAccountsService iAccountsService;
    @GetMapping("/test")
    public String sayHello(){
        return "Hello World";
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto){
        iAccountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam String mobileNumber){
       CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerDto);
    }


    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@RequestBody CustomerDto customerDto){
      boolean isUpdated = iAccountsService.updateAccount(customerDto);
      if(isUpdated){
          return ResponseEntity.status(HttpStatus.OK)
                  .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
      }else{
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                  .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
      }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam String mobileNumber){
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }
}
