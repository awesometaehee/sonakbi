package com.sonakbi.modules.account;

import com.sonakbi.modules.account.form.SignUpForm;
import com.sonakbi.modules.account.validator.SignUpValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final SignUpValidator signUpValidator;

    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpValidator);
    }

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
}
