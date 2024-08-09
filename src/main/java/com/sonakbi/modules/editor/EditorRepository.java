package com.sonakbi.modules.editor;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface EditorRepository extends JpaRepository<Editor, Long>, EditorRepositoryExtension {
    boolean existsByUrl(String url);

    @EntityGraph(attributePaths = {"editorTags", "writer"})
    @Query("select e from Editor e where e.url = :url and e.writer.id = :id")
    Editor findEditorWithTagsByUrl(@Param("url") String url, @Param("id") Long id);

    @EntityGraph(attributePaths = {"writer"})
    @Query("select e from Editor e where e.url = :url and e.writer.id = :id")
    Editor findEditorByUrl(@Param("url") String url, @Param("id") Long id);

    Editor findEditorByUrl(String url);

    // @EntityGraph(value = "Editor.withTags", type = EntityGraph.EntityGraphType.LOAD)
    // List<Editor> findEditorByWriterOrderByPublishedTimeDesc(Account writer, boolean disclosure);
}
