package com.sonakbi.modules.editor.form;

import com.sonakbi.modules.series.Series;
import com.sonakbi.modules.tag.Tag;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class EditorForm {

    private String title;

    private String mainText;

    private String description;

    private String url;

    private String tags;

    private String thumbnail;

    private boolean disclosure;

    private String series;
}
