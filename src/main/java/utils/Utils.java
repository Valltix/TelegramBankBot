package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {

    public static BigDecimal getScaledBigDecimal(BigDecimal b, int scale) {
        return b.setScale(scale, RoundingMode.DOWN);
    }

}
