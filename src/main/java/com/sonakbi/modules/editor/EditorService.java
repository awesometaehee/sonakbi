package com.sonakbi.modules.editor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.editor.form.EditorForm;
import com.sonakbi.modules.editorTag.EditorTagRepository;
import com.sonakbi.modules.like.Likes;
import com.sonakbi.modules.tag.Tag;
import com.sonakbi.modules.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class EditorService {

    private final EditorRepository editorRepository;
    private final ModelMapper modelMapper;
    private final TagRepository tagRepository;
    private final ObjectMapper objectMapper;
    private final EditorTagRepository editorTagRepository;

    public Editor createNewWrite(Account account, EditorForm editorForm) throws JsonProcessingException {
        Editor editor = modelMapper.map(editorForm, Editor.class);
        editor.setWrite(account);

        String jsonString = editorForm.getTags();
        List<Tag> tagList = objectMapper.readValue(jsonString, new TypeReference<List<Tag>>() {});
        String getTagValueToString = getTagValueToString(tagList);
        Set<Tag> tags = parseTags(getTagValueToString);
        for(Tag tag : tags) {
            editor.addTags(tag);
        }
        return editorRepository.save(editor);
    }

    public void updateWrite(EditorForm editorForm, Editor editor) throws JsonProcessingException {
        modelMapper.map(editorForm, editor);

        String jsonString = editorForm.getTags();
        List<Tag> tagList = objectMapper.readValue(jsonString, new TypeReference<List<Tag>>() {});
        String getTagValueToString = getTagValueToString(tagList);
        Set<Tag> tags = parseTags(getTagValueToString);
        editor.removeAllTags(); // 태그 전체 삭제 후 다시 추가
        for(Tag tag : tags) {
            editor.addTags(tag);
        }
    }

    public String getTagValueToString(List<Tag> tags) {
        List<String> tagValues = tags.stream().map(Tag::getValue).toList();
        return String.join(", ", tagValues);
    }

    private Set<Tag> parseTags(String tags) {
        Set<Tag> tagSet = new HashSet<>();
        if (tags != null && !tags.isEmpty()) {
            String[] tagValues = tags.split(",");
            for (String value : tagValues) {
                value = value.trim();
                Tag tag = tagRepository.findByValue(value);
                if (tag == null) {
                    tag = new Tag();
                    tag.setValue(value);
                    tagRepository.save(tag);
                }
                tagSet.add(tag);
            }
        }
        return tagSet;
    }

    public Editor getEditor(String url, Account userAccount, Account writer) {
        Editor editor = editorRepository.findEditorWithTagsByUrl(url, writer.getId());
        editor.checkIfExistingEditor(url, userAccount);

        return editor;
    }

    public List<Editor> getEditorList(Account writer, boolean disclosure) {
        return editorRepository.findEditorByWriterOrderByPublishedTimeDesc(writer, disclosure);
    }

    public void deleteEditor(Account account, Editor editor) {
        if(!editor.checkIfExistingWriter(editor, account)) {
            throw new IllegalArgumentException(account.getUserId() + "는 삭제할 수 없습니다.");
        }

        editorRepository.delete(editor);
    }
}
