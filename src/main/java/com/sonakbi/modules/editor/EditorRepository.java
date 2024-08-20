package com.sonakbi.modules.editor;

import com.sonakbi.modules.series.Series;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface EditorRepository extends JpaRepository<Editor, Long>, EditorRepositoryExtension {
    boolean existsByUrl(String url);

    @EntityGraph(attributePaths = {"editorTags", "writer", "series"})
    @Query("select distinct e from Editor e where e.url = :url and e.writer.id = :id")
    Editor findEditorWithTagsByUrl(@Param("url") String url, @Param("id") Long id);

    @EntityGraph(attributePaths = {"writer"})
    @Query("select e from Editor e where e.url = :url and e.writer.id = :id")
    Editor findEditorByUrl(@Param("url") String url, @Param("id") Long id);

    Editor findEditorByUrl(String url);

    List<Editor> findAllBySeriesOrderByOrderIdAsc(Series series);

    // @EntityGraph(value = "Editor.withTags", type = EntityGraph.EntityGraphType.LOAD)
    // List<Editor> findEditorByWriterOrderByPublishedTimeDesc(Account writer, boolean disclosure);
}
