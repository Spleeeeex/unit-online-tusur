package online_tusur.unit_online_tusur;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static Object[][] readCsvResource(String resourcePath) {
        List<String[]> lines = new ArrayList<>();

        try (InputStream is = CsvReader.class.getResourceAsStream(resourcePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            if (is == null) {
                throw new RuntimeException("Источник не найден: " + resourcePath);
            }

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line.split(";"));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при чтении CSV источника: " + resourcePath, e);
        }

        return lines.toArray(new Object[0][]);
    }
}