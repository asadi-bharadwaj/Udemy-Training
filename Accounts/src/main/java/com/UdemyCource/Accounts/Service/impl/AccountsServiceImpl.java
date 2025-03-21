package com.UdemyCource.Accounts.Service.impl;

import com.UdemyCource.Accounts.Constants.AccountsConstants;
import com.UdemyCource.Accounts.Entity.Accounts;
import com.UdemyCource.Accounts.Entity.Customer;
import com.UdemyCource.Accounts.Exceptions.CustomerAlreadyExistsException;
import com.UdemyCource.Accounts.Exceptions.ResourceNotFoundException;
import com.UdemyCource.Accounts.Mapper.AccountsMapper;
import com.UdemyCource.Accounts.Mapper.CustomerMapper;
import com.UdemyCource.Accounts.Repository.AccountsRepository;
import com.UdemyCource.Accounts.Repository.CustomerRepository;
import com.UdemyCource.Accounts.Service.IAccountsService;
import com.UdemyCource.Accounts.dto.AccountsDto;
import com.UdemyCource.Accounts.dto.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
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
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    +customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
       Customer details = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
               () -> new ResourceNotFoundException("Customer", "MobileNumber", mobileNumber)
       );

       Accounts accDetails = accountsRepository.findByCustomerId(details.getCustomerId()).orElseThrow(
               () -> new ResourceNotFoundException("Account", "customerId","--")
       );
      CustomerDto customerDto = CustomerMapper.mapToCustomerDto(details, new CustomerDto());
      customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accDetails, new AccountsDto()));
       return customerDto;
    }

    /**
     * @param customerDto
     * @return
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", "--")
            );

            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("customer","mobilenumber",mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }
}
