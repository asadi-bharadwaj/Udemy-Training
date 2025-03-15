package com.UdemyCource.Accounts.Controller;

import com.UdemyCource.Accounts.Constants.AccountsConstants;
import com.UdemyCource.Accounts.dto.CustomerDto;
import com.UdemyCource.Accounts.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class AccountsController {

    @GetMapping("/test")
    public String sayHello(){
        return "Hello World";
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

}
