package com.rest.webservice.dto;

import jakarta.validation.constraints.NotNull;

/*
    This DTO is used for utilisation of various findBy queries in StudentRepository.
    Requirements(Parameters):
        1. name : string to be searched : (necessary,not null)
        2. type: search type : {IgnoreCase, StartingWith, EndingWith, Like, Containing} : (null=>exact search)
        3. order: supported only for exact search, orders by ID : {Asc, Desc, Id} : (null=>ascending)
 */
public record SearchStudentNameDto(@NotNull(message = "Name should not be null.") String name, String type, String order) {

}