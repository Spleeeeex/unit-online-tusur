package online_tusur.unit_online_tusur.junit;

import online_tusur.unit_online_tusur.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.stream.IntStream;

public class StudentParametrizedTest {

    Student s = new Student();

    @ParameterizedTest
    @ValueSource(ints = {18, 50, 39, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void testWithValueSource(int param) {
        s.setAge(param);
        Assertions.assertTrue(s.getAge()>=18);
    }

    static IntStream intProvider() {
        return IntStream.range(18, 51);
    }
    @ParameterizedTest
    @MethodSource("intProvider")
    void testWithMethodSource(Integer arg) {
        s.setAge(arg);
        Assertions.assertNotNull(s.getAge());
    }

    // Тесты для avgAge()
    @ParameterizedTest
    @CsvFileSource(resources = "/AvgAge.csv")
    void testAvgAgeWithCsvFileSource(String arg) {
        String [] ageA = arg.split(";");
        Assertions.assertEquals(5, ageA.length, "Должно быть 5 элементов в строке (4 возраста + ожидаемое среднее)");
        int []studentAges = new int [5];
        for (int i = 0; i < 5; i++) {
            studentAges[i] = Integer.parseInt(ageA[i]);
        }

        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = new Student();
        Student s4 = new Student();

        s1.setAge(studentAges[0]);
        s2.setAge(studentAges[1]);
        s3.setAge(studentAges[2]);
        s4.setAge(studentAges[3]);

        Student [] students = {s1, s2, s3, s4};
        int expectedAvg = studentAges[4];
        int actualAvg = Student.avgAge(students);
        Assertions.assertEquals(expectedAvg, actualAvg,
        String.format("Ожидаемое среднее: %d, но фактическое среднее: %d для возрастов: %s",
                expectedAvg,
                actualAvg,
                Arrays.toString(Arrays.copyOf(studentAges, 4))));
    }

    @ParameterizedTest
    @CsvSource({
            "18, 20, 22, 20", // (18+20+22)/3 = 20
            "25, 30, 35, 30", // (25+30+35)/3 = 30
            "27, 27, 27, 27", // все возраста одинаковые
            "18, 18, 19, 18"  // с округлением вниз
    })

    void testAvgAgeWithCsvFileSource2(int a, int b, int c, int expectedAvg) {
        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = new Student();

        s1.setAge(a);
        s2.setAge(b);
        s3.setAge(c);

        Student [] students = {s1, s2, s3};
        int actualAvg = Student.avgAge(students);
        Assertions.assertEquals(expectedAvg, actualAvg);
    }

    // Тесты для maxAge()
    @ParameterizedTest
    @CsvSource({
            "18, 20, 22, 22",
            "25, 30, 35, 35",
            "40, 40, 40, 40",
            "50, 30, 20, 50"
    })

    void testMaxAgeWithCsvSource(int a, int b, int c, int expectedMax) {
        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = new Student();

        s1.setAge(a);
        s2.setAge(b);
        s3.setAge(c);

        Student[] students = {s1, s2, s3};
        int actualMax = Student.maxAge(students);
        Assertions.assertEquals(expectedMax, actualMax);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/MaxAge.csv")
    void testMaxAgeWithCsvFileSource(String arg) {
        String [] ageA = arg.split(";");
        Assertions.assertEquals(5, ageA.length, "Должно быть 5 элементов в строке (4 возраста + ожидаемое максимальное)");
        int []age = new int [5];
        for (int i = 0; i < 5; i++) {
            age[i] = Integer.parseInt(ageA[i]);
        }
        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = new Student();
        Student s4 = new Student();

        s1.setAge(age[0]);
        s2.setAge(age[1]);
        s3.setAge(age[2]);
        s4.setAge(age[3]);

        Student [] students = {s1, s2, s3, s4};
        int expectedMax = age[0]; // Предполагаем, что первый элемент — максимальный
        if (age[1] > expectedMax) expectedMax = age[1];
        if (age[2] > expectedMax) expectedMax = age[2];
        if (age[3] > expectedMax) expectedMax = age[3];
        int actualMax = Student.maxAge(students);
        Assertions.assertEquals(expectedMax, actualMax,
                String.format("Ожидаемое максимальное: %d, но фактическое максимальное: %d для возрастов: %s",
                        expectedMax,
                        actualMax,
                        Arrays.toString(Arrays.copyOf(age, 4))));
    }

    // Тесты для minAge()
    @ParameterizedTest
    @CsvSource({
            "18, 20, 22, 18",
            "25, 30, 35, 25",
            "40, 40, 40, 40",
            "50, 30, 20, 20"
    })

    void testMinAgeWithCsvSource(int a, int b, int c, int expectedMin) {
        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = new Student();

        s1.setAge(a);
        s2.setAge(b);
        s3.setAge(c);

        Student[] students = {s1, s2, s3};
        int actualMin = Student.minAge(students);
        Assertions.assertEquals(expectedMin, actualMin);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/MinAge.csv")
    void testMinAgeWithCsvFileSource(String arg) {
        String [] ageA = arg.split(";");
        Assertions.assertEquals(5, ageA.length, "Должно быть 5 элементов в строке (4 возраста + ожидаемое минимальное)");
        int []age = new int [5];
        for (int i = 0; i < 5; i++) {
            age[i] = Integer.parseInt(ageA[i]);
        }
        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = new Student();
        Student s4 = new Student();

        s1.setAge(age[0]);
        s2.setAge(age[1]);
        s3.setAge(age[2]);
        s4.setAge(age[3]);

        Student [] students = {s1, s2, s3, s4};
        int expectedMin = age[0]; // Предполагаем, что первый элемент — минимальный
        if (age[1] < expectedMin) expectedMin = age[1];
        if (age[2] < expectedMin) expectedMin = age[2];
        if (age[3] < expectedMin) expectedMin = age[3];
        int actualMin = Student.minAge(students);
        Assertions.assertEquals(expectedMin, actualMin,
                String.format("Ожидаемое минимальное: %d, но фактическое минимальное: %d для возрастов: %s",
                        expectedMin,
                        actualMin,
                        Arrays.toString(Arrays.copyOf(age, 4))));
    }
    /*
    Добавить для вывода числа после теста:
    int expectedMax = ...; // Ожидаемое значение
    int actualMax = ...;   // Реальный результат

    System.out.println("Максимальный возраст: " + minAge);

    System.out.println("Ожидаемый результат: " + expectedAvg + "\nФактический результат: " + actualAvg);

    System.out.println("Ожидаемый результат: " + expectedMax);
    System.out.println("Фактический результат: " + actualMax);
    */
}