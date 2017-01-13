package com.showyourtrace.test.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;

@Component
public class TestValues {

    public String randomMoneyString(double left, double right) {
        return randomMoney(left, right).toString();
    }

    public BigDecimal randomMoney(double left, double right) {
        Random r = new Random();
        double d = left + r.nextDouble()*(right-left);
        return new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
