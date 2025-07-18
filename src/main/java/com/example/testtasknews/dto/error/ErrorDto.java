package com.example.testtasknews.dto.error;

import com.example.testtasknews.utils.Constants;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Data
public abstract class ErrorDto {

    private int code;
    private String timestamp;

    public ErrorDto(int code) {
        this.code = code;
        this.timestamp = formatTimestamp();
    }

    private String formatTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.TIMESTAMP_PATTERN, Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone(Constants.TIME_ZONE));
        return sdf.format(new Date());
    }
}
