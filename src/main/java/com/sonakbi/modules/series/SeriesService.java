package com.sonakbi.modules.series;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.editor.Editor;
import com.sonakbi.modules.editor.EditorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SeriesService {

    private final SeriesRepository seriesRepository;

    public Series findCreateNew(String title, Account account) {
        Series series = seriesRepository.findByTitle(title);
        if(series == null && !title.isEmpty()) {
            series = seriesRepository.save(Series.builder().title(title).account(account).build());
        }

        return series;
    }
}
