package com.sonakbi.modules.editor;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.series.Series;
import com.sonakbi.modules.tag.Tag;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface EditorRepositoryExtension {

    List<Editor> findEditorByWriterOrderByPublishedTimeDesc(Account writer, boolean disclosure, Tag tag);

    List<Editor> findSeriesById(Long seriesId, boolean disclosure);

    List<Editor> findByKeywordContainingIgnoreCase(Account writer, boolean disclosure, String keyword);

}
