package com.sonakbi.modules.editor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorRepository extends JpaRepository<Editor, Long> {
    boolean existsByUrl(String url);

    Editor findByUrl(String url);
}
