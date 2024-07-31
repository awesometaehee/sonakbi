package com.sonakbi.modules.editorTag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorTagRepository extends JpaRepository<EditorTag, Long> {
    void deleteByEditorId(Long id);
}
