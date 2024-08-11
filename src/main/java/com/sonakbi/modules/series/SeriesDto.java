package com.sonakbi.modules.series;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class SeriesDto {

    private Long seriesId;

    private String title;

    private String thumbnailImage;

    private long count;

    private Long accountId;

}
