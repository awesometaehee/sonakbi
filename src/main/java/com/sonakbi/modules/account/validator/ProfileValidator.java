package com.sonakbi.modules.account.validator;

import com.sonakbi.modules.account.AccountRepository;
import com.sonakbi.modules.account.form.ProfileForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ProfileValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(ProfileForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProfileForm profileForm = (ProfileForm) target;
        if(accountRepository.existsByUserId(profileForm.getUserId())) {
            errors.rejectValue("userId", "wrong.value", "입력하신 아이디는 사용할 수 없습니다.");
        }
    }
}
