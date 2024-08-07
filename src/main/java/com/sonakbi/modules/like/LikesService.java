package com.sonakbi.modules.like;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.editor.Editor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;

    public void likeSubmit(Editor editor, Account account, String url) {
        editor.checkIfExistingEditor(url, account);

        Optional<Likes> likes = likesRepository.findByEditorAndAccount(editor, account);
        if(likes.isPresent()) {
            likesRepository.delete(likes.get());
            editor.removeLike();
        } else {
            Likes newLike = Likes.builder().editor(editor).account(account).build();
            newLike.setAccount(account);
            newLike.setEditor(editor);
            likesRepository.save(newLike);
            editor.addLike();
        }
    }

    public Boolean isLiked(Editor editor, Account account, String url) {
        editor.checkIfExistingEditor(url, account);

        return likesRepository.findByEditorAndAccount(editor, account).isPresent();
    }
}
