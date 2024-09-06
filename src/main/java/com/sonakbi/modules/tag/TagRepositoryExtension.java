package com.sonakbi.modules.tag;

import com.sonakbi.modules.tag.dto.TagCountDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface TagRepositoryExtension {

    List<TagCountDto> findTagCountById(Long id, boolean disclosure);
}
