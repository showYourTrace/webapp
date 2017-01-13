package com.showyourtrace.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MoneyFormat {

    private static MoneyFormat format = new MoneyFormat();

    private final DecimalFormat df;

    private MoneyFormat() {

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator('.');
//        otherSymbols.setGroupingSeparator('.');
        df = new DecimalFormat("######.##", otherSymbols);
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(false);
    }

    public static MoneyFormat getInstance() {
        if (format != null) {
            synchronized (MoneyFormat.class) {
                if (format != null) {
                    format = new MoneyFormat();
                }
            }
        }
        return format;
    }

    public String format(BigDecimal input) {
        return df.format(input);
    }

}
