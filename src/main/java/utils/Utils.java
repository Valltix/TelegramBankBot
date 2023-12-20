package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {

    // Метод "скорочування" знаків псля коми до scale
    public static BigDecimal getScaledBigDecimal(BigDecimal b, int scale) {
        return b.setScale(scale, RoundingMode.DOWN);
    }

    // Метод зчитування з json файлу, що включає перевірку помилки зчитування і викликання нової поточної помилки.
    public static <T> T readFromJsonFileChecked(String filePath, Class<T> className) {
        try {
            return readFromJsonFile(filePath, className);
        } catch (IOException ioException) {
            System.err.println("Error reading from " + className + "and converting into class " + className.getSimpleName());
            throw new RuntimeException(ioException.getMessage(), ioException);
        }
    }

    /**
     * Зчитує інформацію з файлу json і конвертує у об'єкт заданого классу.
     * @param filePath - шлях до файлу
     * @param className - клас, у який конвертувати
     * @return створений об'єкт заданного класу
     * @param <T> параметризований класом, у який треба конвертувати даннні з json
     * @throws IOException у разі помилки зчитування, буде викликана помилка
     */
    public static <T> T readFromJsonFile(String filePath, Class<T> className) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        var file = new File(filePath);
        return mapper.readValue(file, className);
    }


}
