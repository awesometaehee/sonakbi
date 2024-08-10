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

    @PostMapping("/{id}/{url}/series/add")
    @ResponseBody
    public ResponseEntity addSeries(@CurrentAccount Account account, @RequestBody SeriesForm seriesForm, @PathVariable String url, @PathVariable Long id) {
        Editor editor = editorService.getEditor(url, account, accountService.getAccountInfo(id));
        Series series = seriesService.findCreateNew(seriesForm.getTitle(), account);
        editorService.addSeries(editor, series);
        return ResponseEntity.ok(series.getTitle());
    }
}
