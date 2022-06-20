package com.example.appapi.controller;

import com.example.appapi.payload.ApiResponse;
import com.example.appapi.payload.CustomerDto;
import com.example.appapi.service.CustomerService;
import com.example.appapi.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;

     @GetMapping
     public List<Customer> get(){
         return customerService.getService();
     }

     @GetMapping("/{id}")
     public Customer getById(@PathVariable Integer id){
         return customerService.getByIdService(id);
    }

     @PostMapping()
     public ApiResponse add(@Valid @RequestBody CustomerDto customerDto){
         return customerService.addService(customerDto);
     }

     @PutMapping("/{id}")
     public ApiResponse edit(@Valid @PathVariable Integer id, @RequestBody CustomerDto customerDto){
         return customerService.editService(id,customerDto);
     }

     @DeleteMapping("/{id}")
     public ApiResponse delete(@PathVariable Integer id){
         return customerService.deleteService(id);
     }

     // for valid operation
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }
        return errors;
    }

}





















