package com.sonakbi.modules.blog;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.AccountService;
import com.sonakbi.modules.account.CurrentAccount;
import com.sonakbi.modules.account.form.AboutForm;
import com.sonakbi.modules.comment.Comment;
import com.sonakbi.modules.comment.CommentForm;
import com.sonakbi.modules.comment.CommentService;
import com.sonakbi.modules.editor.Editor;
import com.sonakbi.modules.editor.EditorRepository;
import com.sonakbi.modules.editor.EditorService;
import com.sonakbi.modules.like.LikesRepository;
import com.sonakbi.modules.series.*;
import com.sonakbi.modules.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.sonakbi.modules.blog.BlogController.BLOG_URL;

@Controller
@RequiredArgsConstructor
@RequestMapping(BLOG_URL + "/{id}")
public class BlogController {

    private final EditorService editorService;
    private final AccountService accountService;
    private final TagRepository tagRepository;
    private final CommentService commentService;
    private final SeriesRepository seriesRepository;
    private final EditorRepository editorRepository;
    private final ModelMapper modelMapper;
    private final SeriesService seriesService;

    public static final String BLOG_URL = "/blog";
    public static final String BLOG = "blog";

    @GetMapping("/post")
    public String myPostForm(@CurrentAccount Account account, @PathVariable Long id,
                             @RequestParam(value = "tag", required = false) String tagValue, Model model) {
        Account accountInfo = accountService.getAccountInfo(id);
        boolean checkEqualAccount = account.checkEqualAccount(account, accountInfo); // true = 본 계정 false = 방문자
        int total = editorRepository.countEditorById(id);

        model.addAttribute(account);
        model.addAttribute("accountInfo", accountInfo);
        model.addAttribute("total", total);
        model.addAttribute("postList", editorService.getEditorList(accountInfo, checkEqualAccount, tagValue));
        model.addAttribute("tagList", tagRepository.findTagCountById(id, checkEqualAccount));

        return BLOG + "/post";
    }

    @GetMapping("/post/search")
    public String myPostSearch(@CurrentAccount Account account, @PathVariable Long id,
                               @RequestParam(value = "keyword", required = false) String keyword, Model model) {

        Account accountInfo = accountService.getAccountInfo(id);
        boolean checkEqualAccount = account.checkEqualAccount(account, accountInfo);
        List<Editor> postList = editorService.getSeachEditorList(accountInfo, checkEqualAccount, keyword);

        model.addAttribute("postList", postList);
        return BLOG + "/post :: #postListWrap";
    }

    @GetMapping("/series")
    public String mySeriesForm(@CurrentAccount Account account, @PathVariable Long id, Model model) {
        Account accountInfo = accountService.getAccountInfo(id);
        boolean checkEqualAccount = account.checkEqualAccount(account, accountInfo);

        model.addAttribute(account);
        model.addAttribute("accountInfo", accountInfo);
        model.addAttribute("seriesList", seriesRepository.findSeriesWithEditorCount(accountInfo.getId(), checkEqualAccount));

        return BLOG + "/series";
    }

    @GetMapping("/series/{seriesId}")
    public String seriesListForm(@CurrentAccount Account account, @PathVariable Long id, @PathVariable Long seriesId, Model model) {
        Account accountInfo = accountService.getAccountInfo(id);
        boolean checkEqualAccount = account.checkEqualAccount(account, accountInfo);
        List<Editor> postList = editorRepository.findSeriesById(seriesId, checkEqualAccount);

        model.addAttribute(account);
        model.addAttribute("accountInfo", accountInfo);
        model.addAttribute("postList", postList);
        model.addAttribute(seriesRepository.findById(seriesId).orElseThrow());

        return BLOG + "/series-list";
    }

    @GetMapping("/series/{seriesId}/sort")
    public String seriesListSort(@CurrentAccount Account account, @PathVariable Long id, @PathVariable Long seriesId, @RequestParam String sort, Model model) {
        Account accountInfo = accountService.getAccountInfo(id);
        boolean checkEqualAccount = account.checkEqualAccount(account, accountInfo);
        List<Editor> postList = editorRepository.findSeriesById(seriesId, checkEqualAccount);

        if("desc".equals(sort)) {
            postList.sort(Comparator.comparing(Editor::getOrderId).reversed());
        } else {
            postList.sort(Comparator.comparing(Editor::getOrderId));
        }

        model.addAttribute(account);
        model.addAttribute("accountInfo", accountInfo);
        model.addAttribute("postList", postList);
        model.addAttribute(seriesRepository.findById(seriesId).orElseThrow());
        return "fragments :: post-list";
    }

    @GetMapping("/series/{seriesId}/update")
    public String seriesListUpdate(@CurrentAccount Account account, @PathVariable Long id, @PathVariable Long seriesId, Model model) {
        Account accountInfo = accountService.getAccountInfo(id);
        boolean checkEqualAccount = account.checkEqualAccount(account, accountInfo);
        List<Editor> postList = editorRepository.findSeriesById(seriesId, checkEqualAccount);

        model.addAttribute(account);
        model.addAttribute("accountInfo", accountInfo);
        model.addAttribute("postList", postList);
        model.addAttribute(seriesRepository.findById(seriesId).orElseThrow());

        return BLOG + "/update-series-list";
    }

    @PostMapping("/series/{seriesId}/update")
    @ResponseBody
    public ResponseEntity seriesListUpdateSubmit(@CurrentAccount Account account, @RequestBody SeriesUpdateDto seriesUpdateDto,
                                                 @PathVariable Long id, @PathVariable Long seriesId, Errors errors, Model model) {
        Account accountInfo = accountService.getAccountInfo(id);
        if(errors.hasErrors()) {
            model.addAttribute(account);
            model.addAttribute("accountInfo", accountInfo);
            return ResponseEntity.badRequest().build();
        }

        seriesService.updateSeriesList(seriesUpdateDto, seriesId);
        return ResponseEntity.ok("시리즈가 변경되었습니다.");
    }

    @PostMapping("/series/{seriesId}/delete")
    public String seriesListDelete(@CurrentAccount Account account, @PathVariable Long id, @PathVariable Long seriesId, Model model) {
        Account accountInfo = accountService.getAccountInfo(id);
        boolean checkEqualAccount = account.checkEqualAccount(account, accountInfo);

        seriesService.seriesListDelete(seriesId);

        model.addAttribute(account);
        model.addAttribute("accountInfo", accountInfo);
        model.addAttribute("seriesList", seriesRepository.findSeriesWithEditorCount(accountInfo.getId(), checkEqualAccount));

        return BLOG + "/series";
    }

    @GetMapping("/about")
    public String myAboutForm(@CurrentAccount Account account, @PathVariable Long id, Model model) {
        model.addAttribute(account);
        model.addAttribute("accountInfo", accountService.getAccountInfo(id));
        model.addAttribute("aboutForm", modelMapper.map(account, AboutForm.class));

        return BLOG + "/about";
    }

    @PostMapping("/about")
    public String myAboutFormSubmit(@CurrentAccount Account account, AboutForm aboutForm, Errors errors, Model model, RedirectAttributes attributes) {
        if(errors.hasErrors()) {
            model.addAttribute(account);
            return BLOG + "/about";
        }

        accountService.updateAbout(account, aboutForm);
        attributes.addFlashAttribute("message", "소개를 작성했습니다.");
        return "redirect:/" + BLOG + "/" + account.getId() + "/about";
    }

    @GetMapping("/view/{url}")
    public String viewForm(@CurrentAccount Account account, Model model,
                           @PathVariable Long id, @PathVariable String url) {
        Account accountInfo = accountService.getAccountInfo(id);
        boolean checkEqualAccount = account.checkEqualAccount(account, accountInfo);
        List<Editor> editorList = new ArrayList<>();
        Editor prevEditor = new Editor();
        Editor nextEditor = new Editor();

        Editor editor = editorService.getEditor(url, account, accountService.getAccountInfo(id));
        if(editor.getSeries() != null) {
            editorList = editorRepository.findSeriesById(editor.getSeries().getId(), checkEqualAccount);
            // 이전 포스트
            for(int i = 1; i<editorList.size(); i++) {
                if(editorList.get(i).getUrl().equals(url)) {
                    prevEditor = editorList.get(i - 1);
                }
            }

            // 다음 포스트
            for(int i = 0; i<editorList.size() - 1; i++) {
                if(editorList.get(i).getUrl().equals(url)) {
                    nextEditor = editorList.get(i + 1);
                }
            }
        }

        List<Comment> commentList = commentService.getComments(editor);

        model.addAttribute(account);
        model.addAttribute("accountInfo", accountInfo);
        model.addAttribute(editor);
        model.addAttribute("editorList", editorList);
        model.addAttribute("prevPost", prevEditor);
        model.addAttribute("nextPost", nextEditor);
        model.addAttribute("commentList", commentList);

        return BLOG + "/view";
    }

}
