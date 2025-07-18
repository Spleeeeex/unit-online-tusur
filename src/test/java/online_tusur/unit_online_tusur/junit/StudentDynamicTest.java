package online_tusur.unit_online_tusur.junit;

import online_tusur.unit_online_tusur.Student;;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

public class StudentDynamicTest {

    // Позитивные тесты для метода getAge()
    @TestFactory
    Stream<DynamicTest> positiveAgeTests() {
        Student student = new Student();

        return Stream.of(
                DynamicTest.dynamicTest("Проверка минимального допустимого возраста (18)", () -> {
                    student.setAge(18);
                    Assertions.assertEquals(18, student.getAge());
                }),

                DynamicTest.dynamicTest("Проверка типичного возраста студента (20)", () -> {
                    student.setAge(20);
                    Assertions.assertEquals(20, student.getAge());
                }),

                DynamicTest.dynamicTest("Проверка верхней границы возраста (50)", () -> {
                    student.setAge(50);
                    Assertions.assertEquals(50, student.getAge());
                }),

                DynamicTest.dynamicTest("Проверка последовательного изменения возраста", () -> {
                    student.setAge(18);
                    Assertions.assertEquals(18, student.getAge());
                    student.setAge(25);
                    Assertions.assertEquals(25, student.getAge());
                    student.setAge(37);
                    Assertions.assertEquals(37, student.getAge());
                    student.setAge(43);
                    Assertions.assertEquals(43, student.getAge());
                })
        );
    }

    // Позитивные тесты для метода getFirstName()
    @TestFactory
    Stream<DynamicTest> positiveFirstNameTests() {
        Student student = new Student();

        return Stream.of(
                DynamicTest.dynamicTest("Проверка простого имени", () -> {
                    student.setFirstName("Ivan");
                    Assertions.assertEquals("Ivan", student.getFirstName());
                }),

                DynamicTest.dynamicTest("Проверка имени с дефисом", () -> {
                    student.setFirstName("Anna-Maria");
                    Assertions.assertEquals("Anna-Maria", student.getFirstName());
                }),

                DynamicTest.dynamicTest("Проверка имени с апострофом", () -> {
                    student.setFirstName("O'Connor");
                    Assertions.assertEquals("O'Connor", student.getFirstName());
                }),

                DynamicTest.dynamicTest("Проверка имени с Unicode-символами", () -> {
                    student.setFirstName("Jöhn");
                    Assertions.assertEquals("Jöhn", student.getFirstName());
                })
        );
    }

    // Позитивные тесты для метода getLastName()
    @TestFactory
    Stream<DynamicTest> positiveLastNameTests() {
        Student student = new Student();

        return Stream.of(
                DynamicTest.dynamicTest("Проверка простой фамилии", () -> {
                    student.setLastName("Petrov");
                    Assertions.assertEquals("Petrov", student.getLastName());
                }),

                DynamicTest.dynamicTest("Проверка фамилии с пробелами", () -> {
                    student.setLastName("De La Cruz");
                    Assertions.assertEquals("De La Cruz", student.getLastName());
                }),

                DynamicTest.dynamicTest("Проверка длинной фамилии", () -> {
                    student.setLastName("Alexandrovsky-Sidorov");
                    Assertions.assertEquals("Alexandrovsky-Sidorov", student.getLastName());
                }),

                DynamicTest.dynamicTest("Проверка изменения фамилии", () -> {
                    student.setLastName("Ivanov");
                    Assertions.assertEquals("Ivanov", student.getLastName());
                    student.setLastName("Ivanova");
                    Assertions.assertEquals("Ivanova", student.getLastName());
                })
        );
    }

    // Комплексный позитивный тест
    @TestFactory
    Stream<DynamicNode> combinedPositiveTests() {
        Student student = new Student("Ivan", "Ivanov", 20);

        return Stream.of(
                DynamicContainer.dynamicContainer("Позитивные тесты возраста",
                        positiveAgeTests()
                ),

                DynamicContainer.dynamicContainer("Позитивные тесты имени",
                        positiveFirstNameTests()
                ),

                DynamicContainer.dynamicContainer("Позитивные тесты фамилии",
                        positiveLastNameTests()
                ),

                DynamicTest.dynamicTest("Проверка всех геттеров вместе", () -> {
                    Assertions.assertEquals(20, student.getAge());
                    Assertions.assertEquals("Ivan", student.getFirstName());
                    Assertions.assertEquals("Ivanov", student.getLastName());
                })
        );
    }

    // Негативные тесты для метода getAge()
    @TestFactory
    Stream<DynamicTest> negativeAgeTests() {
        Student student = new Student("Ivan", "Ivanov", 20);

        return Stream.of(

                DynamicTest.dynamicTest("Проверка отрицательного возраста", () -> {
                    student.setAge(-10);
                    Assertions.assertNotEquals(-10, student.getAge());
                    Assertions.assertTrue(student.getAge() >= 0);
                }),

                DynamicTest.dynamicTest("Проверка нереалистично большого возраста", () -> {
                    student.setAge(75);
                    Assertions.assertNotEquals(75, student.getAge());
                    Assertions.assertTrue(student.getAge() <= 50);
                })
        );
    }

    // Негативные тесты для метода getFirstName()
    @TestFactory
    Stream<DynamicTest> negativeFirstNameTests() {
        Student student = new Student();

        return Stream.of(
                DynamicTest.dynamicTest("Проверка пустого имени", () -> {
                    student.setFirstName("");
                    Assertions.assertTrue(student.getFirstName().isEmpty());
                }),

                DynamicTest.dynamicTest("Проверка имени с цифрами", () -> {
                    student.setFirstName("123");
                    Assertions.assertFalse(student.getFirstName().matches("[a-zA-Z]+"));
                }),

                DynamicTest.dynamicTest("Проверка очень длинного имени", () -> {
                    String longName = "A".repeat(1000);
                    student.setFirstName(longName);
                    Assertions.assertNotEquals(longName.length(), student.getFirstName().length());
                })
        );
    }

    // Негативные тесты для метода getLastName()
    @TestFactory
    Stream<DynamicTest> negativeLastNameTests() {
        Student student = new Student();

        return Stream.of(
                DynamicTest.dynamicTest("Проверка пустой фамилии", () -> {
                    student.setLastName("");
                    Assertions.assertTrue(student.getLastName().isEmpty());
                }),

                DynamicTest.dynamicTest("Проверка фамилии со спецсимволами", () -> {
                    student.setLastName("Ivanov@#");
                    Assertions.assertFalse(student.getLastName().matches("[a-zA-Z]+"));
                }),

                DynamicTest.dynamicTest("Проверка фамилии с пробелами", () -> {
                    student.setLastName("Ivan ov");
                    Assertions.assertTrue(student.getLastName().contains(" "));
                })
        );
    }

    // Комплексный негативный тест
    @TestFactory
    Stream<DynamicNode> combinedNegativeTests() {
        Student student = new Student();

        return Stream.of(
                DynamicContainer.dynamicContainer("Негативные тесты возраста",
                        negativeAgeTests()
                ),

                DynamicContainer.dynamicContainer("Негативные тесты имени",
                        negativeFirstNameTests()
                ),

                DynamicContainer.dynamicContainer("Негативные тесты фамилии",
                        negativeLastNameTests()
                )
        );
    }

    @TestFactory
    Stream<DynamicTest> edgeCaseAgeTests() {
        return Stream.of(
                dynamicTest("Null first name", () -> {
                    Student s = new Student();
                    s.setFirstName(null);
                    Assertions.assertNull(s.getFirstName());
                }),
                dynamicTest("Empty last name", () -> {
                    Student s = new Student();
                    s.setLastName("");
                    Assertions.assertEquals("", s.getLastName());
                }),
                dynamicTest("Single student array", () -> {
                    Student[] arr = {new Student("A", "B", 25)};
                    Assertions.assertEquals(25, Student.maxAge(arr));
                })
        );
    }

    @TestFactory
    Stream<DynamicTest> testEdgeCases() {
        return Stream.of(
                dynamicTest("Test null array in maxAge", () -> {
                    assertThrows(IllegalArgumentException.class, () -> Student.maxAge(null));
                }),
                dynamicTest("Test empty array in minAge", () -> {
                    assertThrows(IllegalArgumentException.class, () -> Student.minAge(new Student[0]));
                })
        );
    }
}