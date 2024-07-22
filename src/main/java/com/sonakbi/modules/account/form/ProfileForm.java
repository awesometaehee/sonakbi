package com.sonakbi.modules.account.form;

import jakarta.persistence.Basic;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ProfileForm {

    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
    private String userId;

    @Length(max = 50)
    private String url;

    private String location;

    private String company;

    @Length(max = 50)
    private String shortDescription;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;
}
