package com.sonakbi.modules.editor;

import com.sonakbi.modules.account.Account;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface EditorRepositoryExtension {

    List<Editor> findEditorByWriterOrderByPublishedTimeDesc(Account writer, boolean disclosure);

}
