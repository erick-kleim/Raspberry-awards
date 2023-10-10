package com.raspberry.awards.dto;

import java.util.List;

public class WinnersIntervalDTO {

    private final List<WinnerIntervalDTO> min;
    private final List<WinnerIntervalDTO> max;

    public List<WinnerIntervalDTO> getMin() {
        return min;
    }

    public List<WinnerIntervalDTO> getMax() {
        return max;
    }

    public WinnersIntervalDTO(List<WinnerIntervalDTO> min, List<WinnerIntervalDTO> max) {
        this.min = min;
        this.max = max;
    }
}
