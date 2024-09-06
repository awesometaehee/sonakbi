package com.sonakbi.modules.series.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SeriesForm {

    @NotBlank
    private String title;
}
