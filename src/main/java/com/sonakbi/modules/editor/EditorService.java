package com.sonakbi.modules.editor;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.tag.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EditorService {

    private final EditorRepository editorRepository;

    public void createNewWrite(Account account, Editor editor, List<Tag> tags) {
        editor.setWrite(account);
        editor.setTags(tags);

        editorRepository.save(editor);
    }

    public Editor getEditor(String url, Account writer) {
        Editor editor = editorRepository.findEditorWithTagsByUrl(url, writer.getUserId());
        checkIfExistingEditor(url, editor);
        return editor;
    }

    private static void checkIfExistingEditor(String url, Editor editor) {
        if(editor == null) {
            throw new IllegalArgumentException(url + "에 해당하는 글이 없습니다.");
        }
    }

    public List<Editor> getEditorList(Account writer) {
        return editorRepository.findEditorByWriterOrderByPublishedTimeDesc(writer);
    }
}
