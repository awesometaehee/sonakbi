package com.sonakbi.modules.series;

import lombok.Data;

import java.util.List;

@Data
public class SeriesUpdateDto {

    private String title;

    private List<SeriesUpdateListDto> updateOrder;
}
