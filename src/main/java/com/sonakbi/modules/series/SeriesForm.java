package com.sonakbi.modules.series;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SeriesForm {

    @NotBlank
    private String title;
}
