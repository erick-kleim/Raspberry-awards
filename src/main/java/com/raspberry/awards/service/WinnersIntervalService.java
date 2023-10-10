package com.raspberry.awards.service;

import com.raspberry.awards.dto.WinnerIntervalDTO;
import com.raspberry.awards.dto.WinnersIntervalDTO;
import com.raspberry.awards.jpa.model.Nominated;
import com.raspberry.awards.jpa.model.Producer;
import com.raspberry.awards.jpa.repository.NominatedRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class WinnersIntervalService {

    private final NominatedRepository nominatedRepository;

    @Autowired
    public WinnersIntervalService(NominatedRepository nominatedRepository){
        this.nominatedRepository = nominatedRepository;
    }

    @Transactional
    public List<WinnerIntervalDTO> getWinners() {
        return getWinnerDtos();
    }

    private List<WinnerIntervalDTO> getWinnerDtos() {
        List<Nominated> nominations = nominatedRepository.findByWinnerOrderByYear(true);
        List<WinnerIntervalDTO> winnerIntervals = new ArrayList<>();

        for (Nominated nominated: nominations) {
            winnerIntervals.addAll(nominated.getProducers()
                    .stream()
                    .map(producer -> createWinnerInterval(nominated, producer, winnerIntervals))
                    .toList());
            nominated.getStudios().toString();
        }

        return winnerIntervals;
    }

    private WinnerIntervalDTO createWinnerInterval(Nominated nominated, Producer producer, List<WinnerIntervalDTO> intervals) {
        WinnerIntervalDTO winnerInterval = new WinnerIntervalDTO(producer.getName(), nominated.getYear());
        Optional<WinnerIntervalDTO> lastWinner = getLastWinner(producer, intervals);

        if(lastWinner.isPresent()) {
            winnerInterval.setPreviousWin(lastWinner.get().getFollowingWin());
            winnerInterval.setInterval(winnerInterval.getFollowingWin() - lastWinner.get().getFollowingWin());
        }

        return winnerInterval;
    }

    @Transactional
    public WinnersIntervalDTO getWinnersIntervals(){
        List<WinnerIntervalDTO> winnersIntervals = getWinnerDtos();

        List<WinnerIntervalDTO> filteredIntervals = winnersIntervals.stream()
                .filter(producer -> producer.getInterval() > 0).toList();

        if (filteredIntervals.isEmpty())
            return null;

        List<WinnerIntervalDTO> max = getMaxWinnerIntervalDtos(filteredIntervals);
        List<WinnerIntervalDTO> min = getMinWinnerIntervalDtos(filteredIntervals);

        return new WinnersIntervalDTO(min, max);
    }

    private static List<WinnerIntervalDTO> getMinWinnerIntervalDtos(List<WinnerIntervalDTO> filteredIntervals) {
        int minInterval = filteredIntervals.stream()
                .mapToInt(WinnerIntervalDTO::getInterval).min().orElse(1);
        return filterByInterval(filteredIntervals, minInterval);
    }

    private static List<WinnerIntervalDTO> getMaxWinnerIntervalDtos(List<WinnerIntervalDTO> filteredIntervals) {
        int maxInterval = filteredIntervals.stream()
                .mapToInt(WinnerIntervalDTO::getInterval).max().orElse(1);

        return filterByInterval(filteredIntervals, maxInterval);
    }

    private static List<WinnerIntervalDTO> filterByInterval(List<WinnerIntervalDTO> filteredIntervals, int interval) {
        return filteredIntervals.stream()
                .filter(producer -> producer.getInterval() == interval).toList();
    }

    private static Optional<WinnerIntervalDTO> getLastWinner(Producer producer, List<WinnerIntervalDTO> intervals) {
        return intervals.stream()
                .filter(winnerInterval -> winnerInterval.getProducer().equals(producer.getName()))
                .max(Comparator.comparingInt(WinnerIntervalDTO::getFollowingWin));
    }

}
