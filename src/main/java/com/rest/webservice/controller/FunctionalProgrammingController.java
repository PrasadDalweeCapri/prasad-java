package com.rest.webservice.controller;

import com.rest.webservice.service.FunctionalProgrammingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/functional")
public class FunctionalProgrammingController
{
    @Autowired
    private FunctionalProgrammingService service;

    @GetMapping("/even")
    public List<Integer> getEven()
    {
        return service.evenCheck();
    }

    @GetMapping("/remainder")
    public Integer getRemainder()
    {
        return service.getRemainders();
    }

    @GetMapping("/even_sum")
    public Integer getEvenSum()
    {
        return service.sumEven();
    }

    @GetMapping("/even_square")
    public List<Integer> getEvenSquare()
    {
        return service.evenSquare();
    }
}
