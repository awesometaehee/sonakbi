package com.sonakbi.modules.comment;

import com.sonakbi.modules.editor.Editor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"editor"})
    @Query("select c from Comment c where c.editor.url = :url and c.parentComment.id is null order by c.id asc")
    List<Comment> findCommentWithEditorByUrl(@Param("url") String url);
}
