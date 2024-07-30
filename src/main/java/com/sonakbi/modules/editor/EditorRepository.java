package com.sonakbi.modules.editor;

import com.sonakbi.modules.account.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EditorRepository extends JpaRepository<Editor, Long> {
    boolean existsByUrl(String url);

    @EntityGraph(attributePaths = {"tags", "writer"})
    @Query("select e from Editor e where e.url = :url and e.writer.userId = :userId")
    Editor findEditorWithTagsByUrl(@Param("url") String url, @Param("userId") String userId);

    @EntityGraph(attributePaths = {"writer"})
    @Query("select e from Editor e where e.url = :url and e.writer.userId = :userId")
    Editor findEditorByUrl(@Param("url") String url, @Param("userId") String userId);

    @EntityGraph(value = "Editor.withTags", type = EntityGraph.EntityGraphType.LOAD)
    List<Editor> findEditorByWriterOrderByPublishedTimeDesc(Account writer);
}
