package com.example.globalweatherapp.converters;

import java.math.BigDecimal;

public class UnitsConverter {

    public static Integer fahrenhietToCelsius(double fahrenhiet) {
        BigDecimal bigDecimal = new BigDecimal( ((fahrenhiet - 32) / 18)*10);


        return bigDecimal.intValue();
    }
    public static double celsiusToFahrenhiet(double calcius) {

        return (calcius * 1.8) + 32;
    }
}
