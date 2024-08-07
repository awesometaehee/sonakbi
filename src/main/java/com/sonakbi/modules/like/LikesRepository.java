package com.sonakbi.modules.like;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.editor.Editor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByEditorAndAccount(Editor editor, Account account);
}
