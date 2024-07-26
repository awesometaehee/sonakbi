package com.sonakbi.modules.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<Tag> addTags(List<Tag> tags) {
        List<Tag> tagList = new ArrayList<>();

        for(Tag tag : tags) {
            Tag tagObj = tagRepository.findByValue(tag.getValue());
            if(tagObj == null) {
                Tag t = tagRepository.save(Tag.builder().value(tag.getValue()).build());
                tagList.add(t);
            }
        }

        return tagList;
    }
}
