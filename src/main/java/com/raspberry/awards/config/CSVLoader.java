package com.raspberry.awards.config;


import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.raspberry.awards.jpa.model.Nominated;
import com.raspberry.awards.jpa.model.Producer;
import com.raspberry.awards.jpa.repository.NominatedRepository;
import com.raspberry.awards.jpa.repository.ProducerRepository;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CSVLoader {
    public static final String REPLACE_AND_REGEX = "\\s+and\\s+";
    public static final String SEPARATOR_REGEX = "\\s*,\\s*";

    private final NominatedRepository nominatedRepository;
    final ProducerRepository producerRepository;

    String enabled;

    public CSVLoader(NominatedRepository nominatedRepository, ProducerRepository producerRepository,  @Value("${csvloader.enabled}") String enabled) {
        this.nominatedRepository = nominatedRepository;
        this.producerRepository = producerRepository;
        this.enabled = enabled;
    }

    @Transactional
    @PostConstruct
    public void loadCSVDate() throws IOException, CsvException {
        if(!Boolean.parseBoolean(enabled))
            return;

        ClassPathResource resource = new ClassPathResource("movielist.csv");
        Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).build();

        List<String[]> data = csvReader.readAll();
        for(String[] record: data){
            List<Producer> producers = new ArrayList<>();
            Arrays.stream(record[3].replaceAll(REPLACE_AND_REGEX, ",").split(SEPARATOR_REGEX))
                    .filter(StringUtils::isNoneBlank)
                    .forEach(name -> {
                        Producer producer = producerRepository.findByName(name);
                        if(producer == null) {
                            Producer p = new Producer(name);
                            producerRepository.save(p);
                            producers.add(p);
                        }else {
                            producers.add(producer);
                        }
                    });
            nominatedRepository.save(new Nominated(record, producers));
        }
        csvReader.close();
    }

}
