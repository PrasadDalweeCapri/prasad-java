package com.rest.webservice.entity;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;

    //size validation, the message although defined, it will not be provided in output, overriding is required
    @Size(min = 2, message = "Minimum 2 letters are required in name field")
    private String name;

    //date validation requiring the date to be of the past, overriding is required
    @Past(message = "Valid birth date is expected, you can't exist today if you are getting born in future.")
    private LocalDate date;
}
