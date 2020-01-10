package com.bonsoirdabord.lo52_badtastic;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TimeSeparators {

    H("%02dh%02d", "^\\s*(\\d+)\\s*[hH]\\s*(\\d+)\\s*$"),
    COLONS("%02d:%02d", "^\\s*(\\d+)\\s*:\\s*(\\d+)\\s*$");

    private final String format;
    private final Pattern pattern;

    TimeSeparators(String format, String pattern) {
        this.format = format;
        this.pattern = Pattern.compile(pattern);
    }

    public String getFormat() {
        return format;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String format(int hours) {
        int h = hours / 60;
        int m = hours % 60;

        return format(h, m);
    }

    public String format(int h, int m) {
        return String.format(Locale.getDefault(), format, h, m);
    }

    public int parse(String str) {
        Matcher m = pattern.matcher(str);

        if(m.matches()) {
            try {
                int hrs = Integer.parseInt(m.group(1));
                int min = Integer.parseInt(m.group(2));

                if(hrs >= 0 && hrs < 24 && min >= 0 && min < 60)
                    return hrs * 60 + min;
            } catch(NumberFormatException ignore) {}
        }

        return -1;
    }

}
