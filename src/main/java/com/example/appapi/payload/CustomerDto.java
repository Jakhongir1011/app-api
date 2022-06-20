package com.example.appapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    @NotNull(message = "fullName should not be empty")
    public String fullName;

    @NotNull(message = "PhoneNumber should not be empty")
    public String phoneNumber;

    @NotNull(message = "Address should not be empty")
    public String address;

}
