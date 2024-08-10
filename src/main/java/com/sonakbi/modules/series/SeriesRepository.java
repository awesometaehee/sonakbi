package com.sonakbi.modules.series;

import com.sonakbi.modules.editor.Editor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface SeriesRepository extends JpaRepository<Series, Long>, SeriesRepositoryExtension {

    Series findByTitle(String title);

    @EntityGraph(attributePaths = {"account"})
    @Query("select s from Series s where s.account.id = :id")
    List<Series> findSeriesWithWriterById(@Param("id") Long id);
}
