package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import settings.UserSettings;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
    }

    // Метод "скорочування" знаків псля коми до scale
    public static BigDecimal getScaledBigDecimal(BigDecimal b, int scale) {
        return b.setScale(scale, RoundingMode.DOWN);
    }

    // Метод зчитування з json файлу, що включає перевірку помилки зчитування і викликання нової поточної помилки.
    public static <T> T readFromJsonFileChecked(String filePath, Class<T> className) {
        try {
            return readFromJsonFile(filePath, className);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.err.println("Error reading from " + className + " and converting into class " + className.getSimpleName());
            throw new RuntimeException(ioException.getMessage(), ioException);
        }
    }

    /**
     * Зчитує інформацію з файлу json і конвертує у об'єкт заданого классу.
     *
     * @param filePath   - шлях до файлу
     * @param className  - клас, у який конвертувати
     * @param <T>        параметризований класом, у який треба конвертувати данні з json
     * @return створений об'єкт заданного класу
     * @throws IOException у разі помилки зчитування, буде викликана помилка
     */
    public static <T> T readFromJsonFile(String filePath, Class<T> className) throws IOException {
        Path path = Paths.get(filePath);

        if (!path.toFile().exists()) {
            throw new IOException("File not found: " + filePath);
        }

        return mapper.readValue(path.toFile(), className);
    }

    public static void writeToJsonFile(String filePath, Object data) throws IOException {
        mapper.writeValue(new File(filePath), data);
    }
}
