package com.sonakbi.modules.editor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.AccountService;
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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.sonakbi.modules.editor.EditorController.EDITOR_URL;

@Controller
@RequiredArgsConstructor
@RequestMapping(EDITOR_URL)
public class EditorController {

    private final EditorService editorService;
    private final ModelMapper modelMapper;
    private final TagService tagService;
    private final SeriesService seriesService;
    private final ObjectMapper objectMapper;
    private final EditorValidator editorValidator;
    private final AccountService accountService;
    private final EditorRepository editorRepository;

    public static final String EDITOR_URL = "/editor";
    private static final String EDITOR = "editor";

    @InitBinder("editorForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(editorValidator);
    }

    @GetMapping("/write")
    public String writeForm(@CurrentAccount Account account, Model model) {
        model.addAttribute(account);
        model.addAttribute("editorForm", new EditorForm());

        return EDITOR + "/write";
    }

    @PostMapping("/write")
    public String writeFormSubmit(@CurrentAccount Account account, @Valid EditorForm editorForm, Errors errors, Model model)
            throws JsonProcessingException {
        if(errors.hasErrors()) {
            model.addAttribute(account);
            model.addAttribute(editorForm);
            return EDITOR + "/write";
        }

        editorService.createNewWrite(account, editorForm);

        return "redirect:/blog/" + account.getAccountPath(account.getUserId()) + "/view/" + editorForm.getUrl();
    }

    /*
    @GetMapping("/{userId}/{url}/update")
    public String updateEditorForm(@CurrentAccount Account account, @PathVariable String userId, @PathVariable String url, Model model) {
        Editor editor = editorService.getEditor(url, accountService.getAccountInfo(userId));
        String getTagValueToString = getTagValueToString(editor);

        EditorForm editorForm = EditorForm.builder()
                .title(editor.getTitle())
                .description(editor.getDescription())
                .thumbnail(editor.getThumbnail())
                .url(editor.getUrl())
                .mainText(editor.getMainText())
                .disclosure(editor.isDisclosure())
                .tags(getTagValueToString)
                .series("") // TODO 시리즈 추가해야됨
                .build();

        model.addAttribute(account);
        model.addAttribute(editor);
        model.addAttribute(editorForm);

        return "editor/update-write";
    }

    public String getTagValueToString(Editor editor) {
        List<Tag> tags = editor.getTags();
        List<String> tagValues = tags.stream().map(Tag::getValue).toList();

        return String.join(", ", tagValues);
    }


    @PostMapping("/{userId}/{url}/update")
    public String updateEditorFormSubmit(@CurrentAccount Account account, @PathVariable String url, @PathVariable String userId,
                                         EditorForm editorForm, Errors errors, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute(account);
            model.addAttribute(editorForm);
            return EDITOR + "/write";
        }

        String jsonString = editorForm.getTags();
        try {
            List<Tag> tagList = objectMapper.readValue(jsonString, new TypeReference<List<Tag>>() {});
            Editor editor = editorService.getEditor(url, accountService.getAccountInfo(userId));
            tagService.addTags(tagList);
            editorService.updateWrite(account, editorForm, editor, tagList);
            // TODO 시리즈 추가해야됨
            // seriesService.addSeries(editorForm.getSeries());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/blog/" + account.getAccountPath(account.getUserId()) + "/view/" + editorForm.getUrl();
    }
    */
}
