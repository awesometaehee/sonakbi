package com.sonakbi.modules.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface TagRepository extends JpaRepository<Tag, Long>, TagRepositoryExtension {
    Tag findByValue(String value);

    /*
    @Query("select new com.sonakbi.modules.tag.dto.TagCountDto(t.value, count(et.id)) "
            + "from Tag t left join t.editorTags et "
            + "left join et.editor e "
            + "left join e.writer a "
            + "where a.userId = :userId "
            + "group by t.value "
            + "order by t.value asc")
    List<TagCountDto> findTagCountByUserId(String userId);
    */
}
