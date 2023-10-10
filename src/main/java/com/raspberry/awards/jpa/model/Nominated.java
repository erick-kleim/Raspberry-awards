package com.raspberry.awards.jpa.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Nominated {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "CHAMPIONSHIP_YEAR")
    private int year;
    private String title;
    private String studios;

    @ManyToMany
    @JoinTable(
            name = "nominated_producer",
            joinColumns = @JoinColumn(name = "nominated_id"),
            inverseJoinColumns = @JoinColumn(name = "producer_id")
    )
    private List<Producer> producers;
    private boolean winner;

    public Nominated() {}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudios() {
        return studios;
    }

    public void setStudios(String studios) {
        this.studios = studios;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public Nominated(Long id, int year, String title, String studios, List<Producer> producers, boolean winner) {
        this.id = id;
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.producers = producers;
        this.winner = winner;
    }

    public Nominated (String[] dado, List<Producer> producers){
        this.year = Integer.parseInt(dado[0]);
        this.title = dado[1];
        this.studios = dado[2];
        this.producers = producers;
        this.winner = isWinner(dado[4]);
    }

    private boolean isWinner(String record) {
        if(record == null)
            return false;

        record = record.toUpperCase();
        return record.equals("YES");
    }
}