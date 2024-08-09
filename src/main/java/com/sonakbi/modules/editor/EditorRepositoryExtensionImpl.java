package com.sonakbi.modules.editor;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.QAccount;
import com.sonakbi.modules.editorTag.QEditorTag;
import com.sonakbi.modules.like.QLikes;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class EditorRepositoryExtensionImpl extends QuerydslRepositorySupport implements EditorRepositoryExtension {

    private JPAQueryFactory queryFactory;

    public EditorRepositoryExtensionImpl(JPAQueryFactory queryFactory) {
        super(Editor.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Editor> findEditorByWriterOrderByPublishedTimeDesc(Account writer, boolean disclosure) {

        QEditor editor = QEditor.editor;
        QEditorTag editorTag = QEditorTag.editorTag;
        QAccount account = QAccount.account;
        QLikes likes = QLikes.likes;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(account.id.eq(writer.getId()));
        if(!disclosure) {
            builder.and(editor.disclosure.ne(disclosure));
        }

        return from(editor).where(builder)
                .leftJoin(editor.editorTags, editorTag)
                .leftJoin(editor.writer, account)
                .leftJoin(editor.likes, likes)
                .orderBy(editor.publishedTime.desc())
                .fetch();
    }
}
