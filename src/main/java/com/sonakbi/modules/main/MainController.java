package com.sonakbi.modules.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.AccountRepository;
import com.sonakbi.modules.account.AccountService;
import com.sonakbi.modules.account.CurrentAccount;
import com.sonakbi.modules.editor.Editor;
import com.sonakbi.modules.editor.dto.EditorDto;
import com.sonakbi.modules.editor.EditorRepository;
import com.sonakbi.modules.editor.EditorService;
import lombok.RequiredArgsConstructor;
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

    private final EditorService editorService;

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
        List<EditorDto> postList = EditorDto.from(editorService.getList20(true, lastId));
        return ResponseEntity.ok(postList);
    }

    @GetMapping("/search")
    public String searchForm(@CurrentAccount Account account, Model model) {
        if(account != null) {
            model.addAttribute(account);
        }

        model.addAttribute("isFirst", true);
        return "search/list";
    }

    @GetMapping("/search/q")
    public String searchWithKeyword(@RequestParam(value="keyword") String keyword
            , @RequestParam(value = "lastId", required = false) Long lastId, Model model) {
        List<Editor> searchList = editorService.searchWithKeyword(keyword, lastId);
        model.addAttribute("searchList", searchList);

        // 마지막 데이터의 ID를 다음 커서로 넘김
        if(!searchList.isEmpty()) {
            Long nextCursor = searchList.get(searchList.size() - 1).getId();
            model.addAttribute("nextCursor", nextCursor);
        }

        return "fragments :: search-list-fragment";
    }
}
