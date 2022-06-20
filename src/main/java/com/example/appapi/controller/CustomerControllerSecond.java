package com.example.appapi.controller;

import com.example.appapi.entity.Customer;
import com.example.appapi.payload.ApiResponse;
import com.example.appapi.payload.CustomerDto;
import com.example.appapi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
public class CustomerControllerSecond {
    @Autowired
    CustomerService customerService;

    @GetMapping
    public HttpEntity<List<Customer>> get(){
        List<Customer> service = customerService.getService();
        return ResponseEntity.ok(service);
    }

    @GetMapping("/{id}")
    public HttpEntity<Customer> getById(@PathVariable Integer id){
        Customer result = customerService.getByIdService(id);
        if (result==null){
            return ResponseEntity.status(409).body(result);
        }
         return ResponseEntity.status(201).body(result);
    }

    @PostMapping()
    public HttpEntity<ApiResponse> add(@Valid @RequestBody CustomerDto customerDto){
        ApiResponse apiResponse = customerService.addService(customerDto);
        if (apiResponse.isSuccess()) {
            return ResponseEntity.status(201).body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@Valid @PathVariable Integer id, @RequestBody CustomerDto customerDto){
        ApiResponse response = customerService.editService(id, customerDto);
        if (response.isSuccess())
            return ResponseEntity.status(202).body(response);
        return ResponseEntity.status(409).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponse response = customerService.deleteService(id);
        return ResponseEntity.status(response.isSuccess()?204:409).body(response);
    }

//   message is for error
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
