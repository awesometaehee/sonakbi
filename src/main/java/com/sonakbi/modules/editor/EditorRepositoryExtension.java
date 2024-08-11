package com.sonakbi.modules.editor;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.series.Series;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface EditorRepositoryExtension {

    List<Editor> findEditorByWriterOrderByPublishedTimeDesc(Account writer, boolean disclosure);

    List<Editor> findSeriesById(Long seriesId, boolean disclosure);

}
