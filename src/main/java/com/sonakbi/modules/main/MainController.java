package com.sonakbi.modules.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.AccountRepository;
import com.sonakbi.modules.account.CurrentAccount;
import com.sonakbi.modules.editor.Editor;
import com.sonakbi.modules.editor.EditorDto;
import com.sonakbi.modules.editor.EditorRepository;
import com.sonakbi.modules.editor.EditorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final AccountRepository accountRepository;
    private final EditorRepository editorRepository;
    private final EditorService editorService;
    private final ObjectMapper objectMapper;

    @GetMapping("/")
    public String home(@CurrentAccount Account account, Model model) {
        if (account != null) {
            model.addAttribute(account);
        }

        List<EditorDto> postList = EditorDto.from(editorService.getListTop40(true));
        model.addAttribute("postList", postList);

        return "index";
    }

    @GetMapping("/scroll/{lastId}")
    @ResponseBody
    public ResponseEntity<?> getScrollHome(@PathVariable Long lastId) {
        List<EditorDto> postList = editorRepository.findTop20ByDisclosureOrderByIdDesc(true, lastId);
        return ResponseEntity.ok().body(postList);
    }
}
