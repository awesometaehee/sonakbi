package com.sonakbi.modules.editor.validator;

import com.sonakbi.modules.editor.EditorRepository;
import com.sonakbi.modules.editor.form.EditorForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class EditorValidator implements Validator {

    private final EditorRepository editorRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(EditorForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EditorForm editorForm = (EditorForm) target;

        if(editorRepository.existsByUrl(editorForm.getUrl())) {
            errors.rejectValue("url", "wrong.value", "입력하신 url은 이미 존재합니다.");
        }
    }
}
