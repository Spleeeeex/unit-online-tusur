package online_tusur.unit_online_tusur.testng;

import online_tusur.unit_online_tusur.Student;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class StudentDynamicTestNGTest {

    // Провайдеры данных для позитивных тестов возраста
    @DataProvider(name = "positiveAgeData")
    public Object[][] positiveAgeData() {
        return new Object[][] {
                {"Проверка минимального допустимого возраста (18)", 18, 18},
                {"Проверка типичного возраста студента (20)", 20, 20},
                {"Проверка верхней границы возраста (50)", 50, 50}
        };
    }

    // Позитивные тесты для метода getAge() с использованием провайдера данных
    @Test(dataProvider = "positiveAgeData")
    public void positiveAgeTests(String testName, int inputAge, int expectedAge) {
        Student student = new Student();
        student.setAge(inputAge);
        Assert.assertEquals(student.getAge(), expectedAge, testName);
    }

    // Провайдер данных для последовательного изменения возраста
    @DataProvider(name = "sequentialAgeData")
    public Object[][] sequentialAgeData() {
        return new Object[][] {
                {18, 25, 37, 43}
        };
    }

    @Test(dataProvider = "sequentialAgeData")
    public void testSequentialAgeChange(int age1, int age2, int age3, int age4) {
        Student student = new Student();
        student.setAge(age1);
        Assert.assertEquals(student.getAge(), age1);
        student.setAge(age2);
        Assert.assertEquals(student.getAge(), age2);
        student.setAge(age3);
        Assert.assertEquals(student.getAge(), age3);
        student.setAge(age4);
        Assert.assertEquals(student.getAge(), age4);
    }

    // Провайдеры данных для тестов имени
    @DataProvider(name = "firstNameData")
    public Object[][] firstNameData() {
        return new Object[][] {
                {"Проверка простого имени", "Ivan", "Ivan"},
                {"Проверка имени с дефисом", "Anna-Maria", "Anna-Maria"},
                {"Проверка имени с апострофом", "O'Connor", "O'Connor"},
                {"Проверка имени с Unicode-символами", "Jöhn", "Jöhn"}
        };
    }

    // Позитивные тесты для метода getFirstName()
    @Test(dataProvider = "firstNameData")
    public void positiveFirstNameTests(String testName, String inputName, String expectedName) {
        Student student = new Student();
        student.setFirstName(inputName);
        Assert.assertEquals(student.getFirstName(), expectedName, testName);
    }

    // Провайдеры данных для тестов фамилии
    @DataProvider(name = "lastNameData")
    public Object[][] lastNameData() {
        return new Object[][] {
                {"Проверка простой фамилии", "Petrov", "Petrov"},
                {"Проверка фамилии с пробелами", "De La Cruz", "De La Cruz"},
                {"Проверка длинной фамилии", "Alexandrovsky-Sidorov", "Alexandrovsky-Sidorov"}
        };
    }

    // Позитивные тесты для метода getLastName()
    @Test(dataProvider = "lastNameData")
    public void positiveLastNameTests(String testName, String inputLastName, String expectedLastName) {
        Student student = new Student();
        student.setLastName(inputLastName);
        Assert.assertEquals(student.getLastName(), expectedLastName, testName);
    }

    // Провайдер данных для изменения фамилии
    @DataProvider(name = "lastNameChangeData")
    public Object[][] lastNameChangeData() {
        return new Object[][] {
                {"Ivanov", "Ivanova"}
        };
    }

    @Test(dataProvider = "lastNameChangeData")
    public void testLastNameChange(String lastName1, String lastName2) {
        Student student = new Student();
        student.setLastName(lastName1);
        Assert.assertEquals(student.getLastName(), lastName1);
        student.setLastName(lastName2);
        Assert.assertEquals(student.getLastName(), lastName2);
    }

    // Провайдер данных для тестов getFullName()
    @DataProvider(name = "fullNameData")
    public Object[][] fullNameData() {
        return new Object[][] {
                {"John", "Doe", "Doe John"},
                {"", "", " "},  // Пустые имена
                {null, null, "null null"},  // Null значения
                {"Anna-Maria", "Schmidt", "Schmidt Anna-Maria"}  // Сложные имена
        };
    }

    // Тест для метода getFullName()
    @Test(dataProvider = "fullNameData")
    public void testGetFullName(String firstName, String lastName, String expected) {
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        Assert.assertEquals(student.getFullName(), expected);
    }

    // Комплексный позитивный тест
    @Test
    public void combinedPositiveTests() {
        Student student = new Student("Ivan", "Ivanov", 20);
        Assert.assertEquals(20, student.getAge());
        Assert.assertEquals("Ivan", student.getFirstName());
        Assert.assertEquals("Ivanov", student.getLastName());
    }

    // Провайдеры данных для негативных тестов возраста
    @DataProvider(name = "negativeAgeData")
    public Object[][] negativeAgeData() {
        return new Object[][] {
                {"Проверка отрицательного возраста", -10, 0},
                {"Проверка нереалистично большого возраста", 75, 50}
        };
    }

    // Негативные тесты для метода getAge()
    @Test(dataProvider = "negativeAgeData")
    public void negativeAgeTests(String testName, int inputAge, int expectedAge) {
        Student student = new Student();
        student.setAge(inputAge);
        Assert.assertEquals(student.getAge(), expectedAge, testName);
    }

    // Провайдеры данных для негативных тестов имени
    @DataProvider(name = "negativeFirstNameData")
    public Object[][] negativeFirstNameData() {
        return new Object[][] {
                {"Проверка пустого имени", "", true},
                {"Проверка имени с цифрами", "123", false},
                {"Проверка очень длинного имени", "A".repeat(1000), false}
        };
    }

    // Негативные тесты для метода getFirstName()
    @Test(dataProvider = "negativeFirstNameData")
    public void negativeFirstNameTests(String testName, String inputName, boolean shouldBeEmpty) {
        Student student = new Student();
        student.setFirstName(inputName);
        if (shouldBeEmpty) {
            Assert.assertTrue(student.getFirstName().isEmpty(), testName);
        } else {
            Assert.assertFalse(student.getFirstName().matches("[a-zA-Z]+"), testName);
        }
    }

    // Провайдеры данных для негативных тестов фамилии
    @DataProvider(name = "negativeLastNameData")
    public Object[][] negativeLastNameData() {
        return new Object[][] {
                {"Проверка пустой фамилии", "", true},
                {"Проверка фамилии со спецсимволами", "Ivanov@#", false},
                {"Проверка фамилии с пробелами", "Ivan ov", true}
        };
    }

    // Негативные тесты для метода getLastName()
    @Test(dataProvider = "negativeLastNameData")
    public void negativeLastNameTests(String testName, String inputLastName, boolean shouldContainSpace) {
        Student student = new Student();
        student.setLastName(inputLastName);
        if (shouldContainSpace) {
            Assert.assertTrue(student.getLastName().isEmpty() ||
                    student.getLastName().contains(" "), testName);
        } else {
            Assert.assertFalse(student.getLastName().matches("[a-zA-Z]+"), testName);
        }
    }

    // Комплексный негативный тест
    @Test(dependsOnMethods = {
            "negativeAgeTests",
            "negativeFirstNameTests",
            "negativeLastNameTests"
    })
    public void combinedNegativeTests() {
        // Этот тест будет выполняться после всех указанных негативных тестов
        System.out.println("Все негативные тесты выполнены успешно");

        // Можно добавить дополнительные проверки, которые должны выполняться
        // после всех отдельных негативных тестов
        Student student = new Student();
        student.setAge(-5);
        student.setFirstName("");
        student.setLastName("Bad@Name");

        Assert.assertTrue(student.getAge() >= 0, "Возраст должен быть корректным");
        Assert.assertFalse(student.getFirstName().matches(".*\\d+.*"), "Имя не должно содержать цифр");
        Assert.assertFalse(student.getLastName().matches(".*[@#].*"), "Фамилия не должна содержать спецсимволов");
    }
}