package com.sonakbi.modules.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Chrono {

    public static String timesAgo(LocalDateTime dayBefore) {
        long gap = ChronoUnit.MINUTES.between(dayBefore, LocalDateTime.now());
        String word;
        if (gap == 0){
            word = "방금 전";
        }else if (gap < 60) {
            word = gap + "분 전";
        }else if (gap < 60 * 24){
            word = (gap/60) + "시간 전";
        }else if (gap < 60 * 24 * 10) {
            word = (gap/60/24) + "일 전";
        } else {
            word = dayBefore.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        }
        return word;
    }
}
