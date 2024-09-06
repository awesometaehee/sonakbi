package com.sonakbi.modules.series;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.editor.Editor;
import com.sonakbi.modules.editor.EditorRepository;
import com.sonakbi.modules.series.dto.SeriesUpdateDto;
import com.sonakbi.modules.series.dto.SeriesUpdateListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SeriesService {

    private final SeriesRepository seriesRepository;
    private final EditorRepository editorRepository;

    public Series findCreateNew(String title, Account account) {
        Series series = seriesRepository.findByTitle(title);
        if(series == null && !title.isEmpty()) {
            series = seriesRepository.save(Series.builder().title(title).account(account).build());
        }

        return series;
    }

    public void updateSeriesList(SeriesUpdateDto seriesUpdateDto, Long seriesId) {
        Series series = seriesRepository.findById(seriesId).orElseThrow();
        series.setTitle(seriesUpdateDto.getTitle());

        if(!seriesUpdateDto.getUpdateOrder().isEmpty()) {
            for(SeriesUpdateListDto update : seriesUpdateDto.getUpdateOrder()) {
                Editor editor = editorRepository.findById(update.getId()).orElseThrow();
                editor.setOrderId(update.getOrderId());
                editorRepository.save(editor);
            }
        }
    }

    public void seriesListDelete(Long seriesId) {
        Series series = seriesRepository.findById(seriesId).orElseThrow();

        for(Editor editor : series.getEditor()) {
            editor.setSeries(null);
        }

        seriesRepository.delete(series);
    }
}
