package com.sonakbi.modules.series;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sonakbi.modules.account.QAccount;
import com.sonakbi.modules.editor.QEditor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class SeriesRepositoryExtensionImpl extends QuerydslRepositorySupport implements SeriesRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    public SeriesRepositoryExtensionImpl(JPAQueryFactory queryFactory) {
        super(Series.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<SeriesDto> findSeriesWithEditorCount(Long id, boolean disclosure) {
        QSeries series = QSeries.series;
        QAccount account = QAccount.account;
        QEditor editor = QEditor.editor;

        // BooleanBuilder builder = new BooleanBuilder();

        return queryFactory.select(Projections.constructor(SeriesDto.class, series.id, series.title, editor.count(), account.id))
                .from(series)
                .leftJoin(series.account, account)
                .leftJoin(series.editor, editor)
                .groupBy(series.id, series.title, account.id)
                .fetch();
    }
}
