package com.example.appapi.service;

import com.example.appapi.payload.ApiResponse;
import com.example.appapi.payload.CustomerDto;
import com.example.appapi.entity.Customer;
import com.example.appapi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getService(){
        return customerRepository.findAll();
    }

    public Customer getByIdService(Integer id){
        Optional<Customer> customerRepositoryById = customerRepository.findById(id);
        return customerRepositoryById.orElse(null);
    }

    public ApiResponse addService(CustomerDto customerDto){
        boolean existsByPhoneNumber = customerRepository.existsByPhoneNumber(customerDto.getPhoneNumber());
        if (existsByPhoneNumber){
            return new ApiResponse("this Customer is exists",false);
        }
        Customer customer = new Customer();
        customer.setFullName(customerDto.getFullName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setAddress(customerDto.getAddress());
        customerRepository.save(customer);
        return new ApiResponse("The Customer is saved",true);
    }

    public ApiResponse editService(Integer id, CustomerDto customerDto){
        boolean numberAndIdNot = customerRepository.existsByPhoneNumberAndIdNot(customerDto.getPhoneNumber(), id);
        if (numberAndIdNot){
            return new ApiResponse("there is a customer with such a phone number",false);
        }
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (!optionalCustomer.isPresent()){
            return new ApiResponse("such Customer is exists",false);
        }
        Customer customer = optionalCustomer.get();
        customer.setFullName(customerDto.getFullName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setAddress(customerDto.getAddress());
        customerRepository.save(customer);
        return new ApiResponse("customer saved",true);
    }

    public ApiResponse deleteService(Integer id){
        boolean existsById = customerRepository.existsById(id);
        if (!existsById){
            return new ApiResponse("Customer not found",false);
        }
        customerRepository.deleteById(id);
        return new ApiResponse("Customer deleted", true);
    }

}
