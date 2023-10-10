package com.raspberry.awards.controller;

import com.raspberry.awards.dto.WinnerIntervalDTO;
import com.raspberry.awards.dto.WinnersIntervalDTO;
import com.raspberry.awards.service.WinnersIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/winners-intervals")
public class WinnersController {

    private final WinnersIntervalService winnersService;

    @Autowired
    public WinnersController(WinnersIntervalService winnersService) {
        this.winnersService = winnersService;
    }

    @GetMapping
    public List<WinnerIntervalDTO> getWinners(){
        return winnersService.getWinners();
    }

    @GetMapping("/min-max")
    public WinnersIntervalDTO getWinnersIntervals(){
        return winnersService.getWinnersIntervals();
    }

}
