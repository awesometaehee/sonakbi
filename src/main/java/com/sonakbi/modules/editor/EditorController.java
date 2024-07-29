package com.sonakbi.modules.editor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.CurrentAccount;
import com.sonakbi.modules.editor.form.EditorForm;
import com.sonakbi.modules.editor.validator.EditorValidator;
import com.sonakbi.modules.series.SeriesService;
import com.sonakbi.modules.tag.Tag;
import com.sonakbi.modules.tag.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EditorController {

    private final EditorService editorService;
    private final ModelMapper modelMapper;
    private final TagService tagService;
    private final SeriesService seriesService;
    private final ObjectMapper objectMapper;
    private final EditorValidator editorValidator;

    private static final String EDITOR_URL = "/editor";
    private static final String EDITOR = "editor";

    @InitBinder("editorForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(editorValidator);
    }

    @GetMapping(EDITOR_URL + "/write")
    public String writeForm(@CurrentAccount Account account, Model model) {
        model.addAttribute(account);
        model.addAttribute("editorForm", new EditorForm());

        return EDITOR + "/write";
    }

    @PostMapping(EDITOR_URL + "/write")
    public String writeFormSubmit(@CurrentAccount Account account, @Valid EditorForm editorForm,
                                  Errors errors, Model model) throws JsonProcessingException {
        if(errors.hasErrors()) {
            model.addAttribute(account);
            model.addAttribute(editorForm);
            return EDITOR + "/write";
        }

        String jsonString = editorForm.getTags();
        try {
            List<Tag> tagList = objectMapper.readValue(jsonString, new TypeReference<List<Tag>>() {});
            List<Tag> tags = tagService.addTags(tagList);
            editorService.createNewWrite(account, modelMapper.map(editorForm, Editor.class), tags);
            // seriesService.addSeries(editorForm.getSeries());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/blog/" + account.getAccountPath(account.getUserId()) + "/view/" + editorForm.getUrl();
    }
}
