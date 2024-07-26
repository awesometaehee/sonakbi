package com.sonakbi.modules.series;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SeriesService {

    private final SeriesRepository seriesRepository;

    public void addSeries(String series) {
        Series seriesObj = seriesRepository.findByTitle(series);
        if(seriesObj == null) {
            seriesRepository.save(Series.builder().title(series).build());
        }
    }
}
