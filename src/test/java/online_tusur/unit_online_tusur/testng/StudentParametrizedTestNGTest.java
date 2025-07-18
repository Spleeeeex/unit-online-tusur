package online_tusur.unit_online_tusur.testng;

import online_tusur.unit_online_tusur.CsvReader;
import online_tusur.unit_online_tusur.Student;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

public class StudentParametrizedTestNGTest {

    Student s = new Student();

    @Test(dataProvider = "ageProvider")
    public void testWithValueSource(int param) {
        s.setAge(param);
        Assert.assertTrue(s.getAge() >= 18, "Возраст должен быть >= 18");
    }

    @Test(dataProvider = "ageProvider")
    public void testAgeBoundaries(int input, int expected) {
        Student student = new Student();
        student.setAge(input);
        Assert.assertEquals(student.getAge(), expected);
    }

    @DataProvider(name = "ageProvider")
    public Object[][] ageProvider() {
        return new Object[][]{
                // Для теста testWithValueSource
                {18},
                {50},
                {39},
                {Integer.MAX_VALUE},
                {Integer.MIN_VALUE},
                // Для теста testAgeBoundaries
                {17, 18},
                {18, 18},
                {25, 25},
                {50, 50},
                {51, 18},
                {-10, 18}
        };
    }

    @Test(dataProvider = "intProvider")
    public void testWithMethodSource(Integer arg) {
        s.setAge(arg);
        Assert.assertNotNull(s.getAge());
    }

    @DataProvider(name = "intProvider")
    public Object[][] intProvider() {
        return IntStream.range(18, 51)
                .mapToObj(i -> new Object[]{i})
                .toArray(Object[][]::new);
    }

    // Тесты для avgAge()
    @Test(dataProvider = "avgAgeCsvFileProvider")
    public void testAvgAgeWithCsvFileSource(String csvLine) {
        Assert.assertNotNull(csvLine, "CSV строка не должна быть null");
        System.out.println("Обработка строки: " + csvLine);
        String [] ageA = csvLine.split(";");
        Assert.assertEquals(ageA.length, 5, "Должно быть 5 элементов в строке (4 возраста + ожидаемое среднее)");

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
        System.out.printf("Ожидаемое среднее: %d, Фактическое среднее: %d для возрастов: %s%n",
                expectedAvg,
                actualAvg,
                Arrays.toString(Arrays.copyOf(studentAges, 4)));
        Assert.assertEquals(actualAvg, expectedAvg);
    }

    @DataProvider(name = "avgAgeCsvFileProvider")
    public Object[][] avgAgeCsvFileProvider() {
        return CsvReader.readCsvResource("/AvgAge.csv");
    }

    @Test(dataProvider = "avgAgeCsvProvider")
    public void testAvgAgeWithCsvFileSource2(int a, int b, int c, int expectedAvg) {
        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = new Student();

        s1.setAge(a);
        s2.setAge(b);
        s3.setAge(c);

        Student [] students = {s1, s2, s3};
        int actualAvg = Student.avgAge(students);
        System.out.printf("Тест avgAge: %d, %d, %d. Ожидаем: %d, Получили: %d%n",
                a,
                b,
                c,
                expectedAvg,
                actualAvg);
        Assert.assertEquals(actualAvg, expectedAvg);
    }

    @DataProvider(name = "avgAgeCsvProvider")
    public Object[][] avgAgeCsvProvider() {
        return new Object[][]{
                {18, 20, 22, 20},
                {25, 30, 35, 30},
                {27, 27, 27, 27},
                {18, 18, 19, 18}
        };
    }

    // Тесты для maxAge()
    @Test(dataProvider = "maxAgeCsvProvider")
    public void testMaxAgeWithCsvSource(int a, int b, int c, int expectedMax) {
        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = new Student();

        s1.setAge(a);
        s2.setAge(b);
        s3.setAge(c);

        Student[] students = {s1, s2, s3};
        int actualMax = Student.maxAge(students);
        System.out.printf("Тест maxAge: %d, %d, %d. Ожидаем: %d, Получили: %d%n",
                a,
                b,
                c,
                expectedMax,
                actualMax);
        Assert.assertEquals(actualMax, expectedMax);
    }

    @DataProvider(name = "maxAgeCsvProvider")
    public Object[][] maxAgeCsvProvider() {
        return new Object[][]{
                {18, 20, 22, 22},
                {25, 30, 35, 35},
                {40, 40, 40, 40},
                {50, 30, 20, 50}
        };
    }

    @Test(dataProvider = "maxAgeCsvFileProvider")
    public void testMaxAgeWithCsvFileSource(String csvLine) {
        Assert.assertNotNull(csvLine, "CSV строка не должна быть null");
        System.out.println("Обработка строки: " + csvLine);
        String [] ageA = csvLine.split(";");
        Assert.assertEquals(ageA.length, 5, "Должно быть 5 элементов в строке (4 возраста + ожидаемое максимальное)");

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
        int expectedMax = Arrays.stream(Arrays.copyOf(age, 4)).max().getAsInt();
        int actualMax = Student.maxAge(students);
        System.out.printf("Тест maxAge из файла: %s. Ожидаем: %d, Получили: %d%n",
                Arrays.toString(Arrays.copyOf(age, 4)),
                expectedMax,
                actualMax);
        Assert.assertEquals(actualMax, expectedMax);
    }

    @DataProvider(name = "maxAgeCsvFileProvider")
    public Object[][] maxAgeCsvFileProvider() {
        return CsvReader.readCsvResource("/MaxAge.csv");
    }

    // Тесты для minAge()
    @Test(dataProvider = "minAgeCsvProvider")
    public void testMinAgeWithCsvSource(int a, int b, int c, int expectedMin) {
        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = new Student();

        s1.setAge(a);
        s2.setAge(b);
        s3.setAge(c);

        Student[] students = {s1, s2, s3};
        int actualMin = Student.minAge(students);

        System.out.printf("Тест minAge: %d, %d, %d. Ожидаем: %d, Получили: %d%n",
                a,
                b,
                c,
                expectedMin,
                actualMin);
        Assert.assertEquals(actualMin, expectedMin);
    }

    @DataProvider(name = "minAgeCsvProvider")
    public Object[][] minAgeCsvProvider() {
        return new Object[][]{
                {18, 20, 22, 18},
                {25, 30, 35, 25},
                {40, 40, 40, 40},
                {50, 30, 20, 20}
        };
    }

    @Test(dataProvider = "minAgeCsvFileProvider")
    public void testMinAgeWithCsvFileSource(String csvLine) {
        Assert.assertNotNull(csvLine, "CSV строка не должна быть null");
        System.out.println("Обработка строки: " + csvLine);
        String [] ageA = csvLine.split(";");
        Assert.assertEquals(ageA.length, 5, "Должно быть 5 элементов в строке (4 возраста + ожидаемое минимальное)");

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
        int expectedMin = Arrays.stream(Arrays.copyOf(age, 4)).min().getAsInt();
        int actualMin = Student.minAge(students);

        System.out.printf("Тест minAge из файла: %s. Ожидаем: %d, Получили: %d%n",
                Arrays.toString(Arrays.copyOf(age, 4)),
                expectedMin,
                actualMin);
        Assert.assertEquals(actualMin, expectedMin);
    }

    @DataProvider(name = "minAgeCsvFileProvider")
    public Object[][] minAgeCsvFileProvider() {
        return CsvReader.readCsvResource("/MinAge.csv");
    }

    // Добавляем параметризованные тесты
    @DataProvider(name = "firstNameProvider")
    public Object[][] firstNameProvider() {
        return new Object[][]{
                {null, null},
                {"", ""},
                {"a", "A"},
                {"john", "John"},
                {"MARY", "Mary"}
        };
    }

    @Test(dataProvider = "firstNameProvider")
    public void testFirstNameFormatting(String input, String expected) {
        Student student = new Student();
        student.setFirstName(input);
        Assert.assertEquals(student.getFirstName(), expected);
    }

    // DataProvider для проверки null/empty
    @DataProvider(name = "nameProvider")
    public Object[][] nameProvider() {
        return new Object[][]{
                {null, null},
                {"", ""},
                {"a", "A"},
                {"john", "John"},
                {"MARY", "Mary"}
        };
    }

    // Тест для проверки имен
    @Test(dataProvider = "nameProvider")
    public void testNameFormatting(String input, String expected) {
        Student student = new Student();
        student.setFirstName(input);
        Assert.assertEquals(student.getFirstName(), expected);
    }

    // Тест для проверки null массива
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMaxAgeWithNullArray() {
        Student.maxAge(null);
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