package com.sonakbi.modules.editor;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.QAccount;
import com.sonakbi.modules.comment.QComment;
import com.sonakbi.modules.editorTag.QEditorTag;
import com.sonakbi.modules.like.Likes;
import com.sonakbi.modules.like.QLikes;
import com.sonakbi.modules.series.QSeries;
import com.sonakbi.modules.tag.QTag;
import com.sonakbi.modules.tag.Tag;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public class EditorRepositoryExtensionImpl extends QuerydslRepositorySupport implements EditorRepositoryExtension {

    private JPAQueryFactory queryFactory;

    public EditorRepositoryExtensionImpl(JPAQueryFactory queryFactory) {
        super(Editor.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Editor> findEditorByWriterOrderByPublishedTimeDesc(Account writer, boolean disclosure, Tag tag, Long lastId) {

        QEditor editor = QEditor.editor;
        QEditorTag editorTag = QEditorTag.editorTag;
        QAccount account = QAccount.account;
        QLikes likes = QLikes.likes;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(account.id.eq(writer.getId()));
        builder.and(editor.id.lt(lastId));

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
                .orderBy(editor.id.desc())
                .limit(10)
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

    @Override
    public List<Editor> findByKeywordIgnoreCase(String keyword, Long lastId) {
        QEditor editor = QEditor.editor;
        QEditorTag editorTag = QEditorTag.editorTag;
        QTag tag = QTag.tag;
        QAccount account = QAccount.account;
        QLikes likes = QLikes.likes;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(editor.title.containsIgnoreCase(keyword));
        builder.or(tag.value.containsIgnoreCase(keyword));
        builder.and(editor.disclosure.eq(true));
        builder.and(editor.id.lt(lastId));

        return from(editor).where(builder)
                .leftJoin(editor.editorTags, editorTag)
                .leftJoin(editor.writer, account)
                .leftJoin(editorTag.tag, tag)
                .leftJoin(editor.likes, likes)
                .distinct()
                .orderBy(editor.publishedTime.desc())
                .limit(20)
                .fetch();
    }

    @Override
    public Editor findByLastId(Account writer, boolean disclosure) {
        QEditor editor = QEditor.editor;
        QAccount account = QAccount.account;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(account.id.eq(writer.getId()));

        if(!disclosure) {
            builder.and(editor.disclosure.ne(disclosure));
        }

        return from(editor).where(builder)
                .leftJoin(editor.writer, account)
                .orderBy(editor.id.desc())
                .limit(1)
                .fetchFirst();
    }

    @Override
    public List<Editor> findTop20ByDisclosureOrderByIdDesc(boolean disclosure, Long lastId) {
        QEditor editor = QEditor.editor;
        QAccount account = QAccount.account;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(editor.id.lt(lastId));

        if(!disclosure) {
            builder.and(editor.disclosure.ne(disclosure));
        }

        return from(editor).where(builder)
                .leftJoin(editor.writer, account)
                .orderBy(editor.id.desc())
                .limit(20)
                .fetch();
    }

    @Override
    public List<Editor> findFirst20ByKeywordContainingOrderByIdDesc(String keyword) {
        QEditor editor = QEditor.editor;
        QEditorTag editorTag = QEditorTag.editorTag;
        QTag tag = QTag.tag;
        QAccount account = QAccount.account;
        QLikes likes = QLikes.likes;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(editor.title.containsIgnoreCase(keyword));
        builder.or(tag.value.containsIgnoreCase(keyword));
        builder.and(editor.disclosure.eq(true));

        return from(editor).where(builder)
                .leftJoin(editor.editorTags, editorTag)
                .leftJoin(editor.writer, account)
                .leftJoin(editorTag.tag, tag)
                .leftJoin(editor.likes, likes)
                .distinct()
                .orderBy(editor.publishedTime.desc())
                .limit(20)
                .fetch();
    }

    @Override
    public List<Editor> findByLikes(Set<Likes> likesSet) {
        QEditor editor = QEditor.editor;
        QAccount account = QAccount.account;
        QLikes likes = QLikes.likes;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(editor.disclosure.eq(true));
        builder.and(editor.likes.any().in(likesSet));

        return from(editor).where(builder)
                .leftJoin(editor.writer, account)
                .leftJoin(editor.likes, likes)
                .orderBy(editor.publishedTime.desc())
                .distinct()
                .fetch();
    }
}
