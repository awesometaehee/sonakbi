package com.sonakbi.modules.series;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface SeriesRepositoryExtension {

    List<SeriesDto> findSeriesWithEditorCount(Long id, boolean disclosure);
}
