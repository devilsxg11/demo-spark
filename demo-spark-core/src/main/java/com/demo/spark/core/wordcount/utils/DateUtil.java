package com.demo.spark.core.wordcount.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Copyright：dp.com
 * Author: SongXiaoGuang
 * Date: 2022/9/5.
 * Description:
 */
public class DateUtil {

    public static String getToday(String patten) {
        return getToday(DateTimeFormatter.ofPattern(patten));
    }
    public static String getToday(DateTimeFormatter patten) {
        return LocalDateTime.now().format(patten);
    }

    public static String getCurrentTime() {
        return getToday("yyyy-MM-dd HH:mm:ss.SSS");
    }
}
