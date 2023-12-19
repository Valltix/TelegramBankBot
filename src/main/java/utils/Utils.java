package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import settings.UserSettings;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {

    public static BigDecimal getScaledBigDecimal(BigDecimal b, int scale) {
        return b.setScale(scale, RoundingMode.DOWN);
    }

    public static <T> T readFromJsonFile(String filePath, Class<T> className) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            var file = new File(filePath);
            return mapper.readValue(file, className);
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
            throw new RuntimeException(ioException.getMessage(), ioException);
        }
    }

}
