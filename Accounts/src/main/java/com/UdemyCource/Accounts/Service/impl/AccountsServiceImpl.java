package com.UdemyCource.Accounts.Service.impl;

import com.UdemyCource.Accounts.Constants.AccountsConstants;
import com.UdemyCource.Accounts.Entity.Accounts;
import com.UdemyCource.Accounts.Entity.Customer;
import com.UdemyCource.Accounts.Mapper.AccountsMapper;
import com.UdemyCource.Accounts.Mapper.CustomerMapper;
import com.UdemyCource.Accounts.Repository.AccountsRepository;
import com.UdemyCource.Accounts.Repository.CustomerRepository;
import com.UdemyCource.Accounts.Service.IAccountsService;
import com.UdemyCource.Accounts.dto.CustomerDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

//    @Autowired
    private AccountsRepository accountsRepository;

//    @Autowired
    private CustomerRepository customerRepository;
    /**
     * @param customerDto
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    private Accounts createNewAccount(Customer customer){
        Accounts newAccount = new Accounts();
        newAccount.setCustomerID(customer.getCustomerID());
        long randomAccountNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccountNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }
}
