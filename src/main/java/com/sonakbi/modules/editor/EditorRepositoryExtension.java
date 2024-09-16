package com.sonakbi.modules.editor;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.series.Series;
import com.sonakbi.modules.tag.Tag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface EditorRepositoryExtension {

    List<Editor> findEditorByWriterOrderByPublishedTimeDesc(Account writer, boolean disclosure, Tag tag, Long lastId);

    List<Editor> findSeriesById(Long seriesId, boolean disclosure);

    List<Editor> findByKeywordContainingIgnoreCase(Account writer, boolean disclosure, String keyword);

    List<Editor> findByKeywordIgnoreCase(String keyword, Long lastId);

    Editor findByLastId(Account account, boolean disclosure);

    List<Editor> findTop20ByDisclosureOrderByIdDesc(boolean disclosure, Long lastId);

    List<Editor> findFirst20ByKeywordContainingOrderByIdDesc(String keyword);

}
