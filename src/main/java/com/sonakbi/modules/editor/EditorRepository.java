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

    @EntityGraph(attributePaths = {"writer"})
    @Query("select count(*) from Editor e where e.writer.id = :id")
    Integer countEditorById(@Param("id") Long id);

    List<Editor> findAllBySeriesOrderByOrderIdAsc(Series series);

    @EntityGraph(attributePaths = {"writer"})
    List<Editor> findTop40ByDisclosureOrderByIdDesc(boolean disclosure);

    @Query("select e.id from Editor e order by e.id desc limit 1")
    Long findByLastId();

    @Query("select e from Editor e left join e.comments c where c.id = :id")
    Editor findEditorWithCommentById(Long id);

    // @EntityGraph(value = "Editor.withTags", type = EntityGraph.EntityGraphType.LOAD)
    // List<Editor> findEditorByWriterOrderByPublishedTimeDesc(Account writer, boolean disclosure);
}
