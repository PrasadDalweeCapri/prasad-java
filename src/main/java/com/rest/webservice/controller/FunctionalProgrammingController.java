package com.rest.webservice.controller;

import com.rest.webservice.service.FunctionalProgrammingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/functional")
public class FunctionalProgrammingController
{
    @Autowired
    private FunctionalProgrammingService functionalProgrammingServiceservice;

    @GetMapping("/even")
    public List<Integer> getEven()
    {
        return functionalProgrammingServiceservice.evenCheck();
    }

    @GetMapping("/remainder")
    public Map<Integer, Integer> getRemainder()
    {
        return functionalProgrammingServiceservice.getRemainders();
    }

    @GetMapping("/even_sum")
    public Integer getEvenSum()
    {
        return functionalProgrammingServiceservice.sumEven();
    }

    @GetMapping("/even_square")
    public List<Integer> getEvenSquare()
    {
        return functionalProgrammingServiceservice.evenSquare();
    }
}
