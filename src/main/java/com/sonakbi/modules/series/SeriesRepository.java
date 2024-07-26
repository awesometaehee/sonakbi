package com.sonakbi.modules.series;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface SeriesRepository extends JpaRepository<Series, Long> {
    Series findByTitle(String series);
}
