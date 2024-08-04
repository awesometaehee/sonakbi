package com.sonakbi.modules.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface TagRepositoryExtension {

    List<TagCountDto> findTagCountByUserId(String userId, boolean disclosure);
}
