package com.sonakbi.modules.account;

import com.sonakbi.modules.account.form.ProfileForm;
import com.sonakbi.modules.account.form.SignUpForm;
import com.sonakbi.modules.account.validator.ProfileValidator;
import com.sonakbi.modules.account.validator.SignUpValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final SignUpValidator signUpValidator;
    private final ProfileValidator profileValidator;
    private final ModelMapper modelMapper;

    @InitBinder("signUpForm")
    public void signUpInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpValidator);
    }

    /*
    @InitBinder("profileForm")
    public void profileInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(profileValidator);
    }
    */

    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpFormSubmit(@Valid SignUpForm signUpForm, Errors errors,
                                   HttpServletRequest request, HttpServletResponse response) {
        if(errors.hasErrors()) {
            return "/sign-up";
        }

        Account account = accountService.processNewAccount(signUpForm);
        accountService.login(account, request, response);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/{userId}/profile")
    public String profileForm(@CurrentAccount Account account, Model model) {
        model.addAttribute(account);
        model.addAttribute("profileForm", modelMapper.map(account, ProfileForm.class));
        return "account/profile";
    }

    @PostMapping("/{userId}/profile")
    public String profileFormSubmit(@CurrentAccount Account account, @PathVariable String userId, @Valid ProfileForm profileForm, Errors errors, Model model, RedirectAttributes attributes) {
        if(errors.hasErrors()) {
            model.addAttribute(account);
            return "account/profile";
        }

        accountService.updateProfile(account, profileForm);
        attributes.addFlashAttribute("message", "프로필을 수정헀습니다.");
        return "redirect:/" + account.getAccountPath(userId) + "/profile";

    }
}
