package com.sonakbi.modules.tag;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sonakbi.modules.account.QAccount;
import com.sonakbi.modules.editor.QEditor;
import com.sonakbi.modules.editorTag.QEditorTag;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class TagRepositoryExtensionImpl extends QuerydslRepositorySupport implements TagRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    public TagRepositoryExtensionImpl(JPAQueryFactory queryFactory) {
        super(Tag.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<TagCountDto> findTagCountByUserId(String userId, boolean disclosure) {
        QTag tag = QTag.tag;
        QEditorTag editorTag = QEditorTag.editorTag;
        QEditor editor = QEditor.editor;
        QAccount account = QAccount.account;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(account.userId.eq(userId));
        if(!disclosure) {
            builder.and(editor.disclosure.ne(disclosure));
        }

        return queryFactory.select(Projections.constructor(TagCountDto.class, tag.value, editorTag.count()))
                .from(tag).where(builder)
                .leftJoin(tag.editorTags, editorTag)
                .leftJoin(editorTag.editor, editor)
                .leftJoin(editor.writer, account)
                .groupBy(tag.value)
                .orderBy(tag.value.asc())
                .fetch();
    }
}
