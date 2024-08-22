package com.sonakbi.modules.series;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.AccountService;
import com.sonakbi.modules.account.CurrentAccount;
import com.sonakbi.modules.editor.Editor;
import com.sonakbi.modules.editor.EditorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class SeriesController {

    private final EditorService editorService;
    private final AccountService accountService;
    private final SeriesService seriesService;

    @PostMapping("/series/create")
    @ResponseBody
    public ResponseEntity createSeries(@CurrentAccount Account account, @RequestBody SeriesForm seriesForm) {
        Series series = seriesService.findCreateNew(seriesForm.getTitle(), account);
        return ResponseEntity.ok(series.getTitle());
    }

    @PostMapping("/series/update")
    @ResponseBody
    public ResponseEntity updateSeries(@CurrentAccount Account account, @RequestBody SeriesForm seriesForm) {
        // Editor editor = editorService.getEditor(url, account, accountService.getAccountInfo(id));
        Series series = seriesService.findCreateNew(seriesForm.getTitle(), account);
        return ResponseEntity.ok(series.getTitle());
    }
}
