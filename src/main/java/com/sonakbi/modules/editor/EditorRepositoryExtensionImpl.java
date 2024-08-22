package com.sonakbi.modules.editor;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.QAccount;
import com.sonakbi.modules.editorTag.QEditorTag;
import com.sonakbi.modules.like.QLikes;
import com.sonakbi.modules.series.QSeries;
import com.sonakbi.modules.tag.Tag;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class EditorRepositoryExtensionImpl extends QuerydslRepositorySupport implements EditorRepositoryExtension {

    private JPAQueryFactory queryFactory;

    public EditorRepositoryExtensionImpl(JPAQueryFactory queryFactory) {
        super(Editor.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Editor> findEditorByWriterOrderByPublishedTimeDesc(Account writer, boolean disclosure, Tag tag) {

        QEditor editor = QEditor.editor;
        QEditorTag editorTag = QEditorTag.editorTag;
        QAccount account = QAccount.account;
        QLikes likes = QLikes.likes;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(account.id.eq(writer.getId()));

        if(!disclosure) {
            builder.and(editor.disclosure.ne(disclosure));
        }

        if(tag != null) {
            builder.and(editorTag.tag.eq(tag));
        }

        return from(editor).where(builder)
                .leftJoin(editor.editorTags, editorTag)
                .leftJoin(editor.writer, account)
                .leftJoin(editor.likes, likes)
                .orderBy(editor.publishedTime.desc())
                .fetch();
    }

    @Override
    public List<Editor> findSeriesById(Long seriesId, boolean disclosure) {
        QSeries series = QSeries.series;
        QEditor editor = QEditor.editor;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(series.id.eq(seriesId));
        if(!disclosure) {
            builder.and(editor.disclosure.ne(disclosure));
        }

        return from(editor).where(builder)
                .leftJoin(editor.series, series).fetchJoin()
                .orderBy(editor.orderId.asc())
                .fetch();
    }

    @Override
    public List<Editor> findByKeywordContainingIgnoreCase(Account writer, boolean disclosure, String keyword) {
        QEditor editor = QEditor.editor;
        QEditorTag editorTag = QEditorTag.editorTag;
        QAccount account = QAccount.account;
        QLikes likes = QLikes.likes;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(account.id.eq(writer.getId()));
        builder.and(editor.title.containsIgnoreCase(keyword));

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
