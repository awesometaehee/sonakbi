package com.sonakbi.modules.editor.form;

import com.sonakbi.modules.series.Series;
import com.sonakbi.modules.tag.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
public class EditorForm {

    @NotBlank
    private String title;

    private String mainText;

    private String description;

    @NotBlank
    private String url;

    private String tags;

    private String thumbnail = null;

    private boolean disclosure;

    private String series;
}
