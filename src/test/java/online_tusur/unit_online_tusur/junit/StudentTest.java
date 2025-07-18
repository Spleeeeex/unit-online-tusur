package online_tusur.unit_online_tusur.junit;

import static org.junit.jupiter.api.Assertions.*;

import online_tusur.unit_online_tusur.Student;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class StudentTest {

	// Тесты конструкторов
	@Test
	void testDefaultConstructor() {
		Student student = new Student();
		assertNull(student.getFirstName());
		assertNull(student.getLastName());
		assertEquals(0, student.getAge());
	}

	@Test
	void testParameterizedConstructor() {
		Student student = new Student("John", "Doe", 25);
		assertEquals("John", student.getFirstName());
		assertEquals("Doe", student.getLastName());
		assertEquals(25, student.getAge());
	}

	// Тесты для setAge() и getAge()
	@Test
	void testAgeBelow18() {
		Student student = new Student();
		student.setAge(17);
		assertEquals(18, student.getAge());
	}

	@Test
	void testAgeAbove50() {
		Student student = new Student();
		student.setAge(51);
		assertEquals(18, student.getAge());
	}

	@Test
	void testAgeExactly18() {
		Student student = new Student();
		student.setAge(18);
		assertEquals(18, student.getAge());
	}

	@Test
	void testAgeExactly50() {
		Student student = new Student();
		student.setAge(50);
		assertEquals(50, student.getAge());
	}

	@Test
	void testAgeWithValidRange() {
		Student student = new Student();
		student.setAge(25);
		assertEquals(25, student.getAge());
	}

	@Test
	void testAgeBelow0() {
		Student student = new Student();
		student.setAge(-1);
		assertEquals(18, student.getAge());
	}

	// Тесты для setFirstName() и getFirstName()
	@Test
	void testNameFormatting() {
		Student student = new Student();
		student.setFirstName("ivAN");
		assertEquals("Ivan", student.getFirstName());
	}

	@Test
	void testSingleCharFirstName() {
		Student student = new Student();
		student.setFirstName("a");
		assertEquals("A", student.getFirstName());
	}

	@Test
	void testNullFirstName() {
		Student student = new Student();
		student.setFirstName(null);
		assertNull(student.getFirstName());
	}

	@Test
	void testEmptyFirstName() {
		Student student = new Student();
		student.setFirstName("");
		assertEquals("", student.getFirstName());
	}

	@Test
	void testDoubleName() {
		Student student = new Student();
		student.setFirstName("anna-maria");
		assertEquals("Anna-Maria", student.getFirstName());
	}

	// Тесты для setLastName() и getLastName()
	@Test
	void testLastNameFormatting() {
		Student student = new Student();
		student.setLastName("peTROV");
		assertEquals("Petrov", student.getLastName());
	}

	@Test
	void testSingleCharLastName() {
		Student student = new Student();
		student.setLastName("b");
		assertEquals("B", student.getLastName());
	}

	@Test
	void testNullLastName() {
		Student student = new Student();
		student.setLastName(null);
		assertNull(student.getLastName());
	}

	@Test
	void testEmptyLastName() {
		Student student = new Student();
		student.setLastName("");
		assertEquals("", student.getLastName());
	}

	// Тесты для getFullName()
	@Test
	void testFullName() {
		Student student = new Student("Andrey", "Petrov", 31);
		assertEquals("Petrov Andrey", student.getFullName());
	}

	@Test
	void testFullNameWithNullValues() {
		Student student = new Student();
		assertEquals("null null", student.getFullName());
	}

	// Тесты для статических методов
	@Test
	void testMinAge() {
		Student[] students = {
				new Student("A", "B", 20),
				new Student("C", "D", 18),
				new Student("E", "F", 35)
		};
		assertEquals(18, Student.minAge(students));
	}

	@Test
	void testMaxAge() {
		Student[] students = {
				new Student("A", "B", 20),
				new Student("C", "D", 18),
				new Student("E", "F", 35)
		};
		assertEquals(35, Student.maxAge(students));
	}

	@Test
	void testAvgAge() {
		Student[] students = {
				new Student("A", "B", 20),
				new Student("C", "D", 30),
				new Student("E", "F", 40)
		};
		assertEquals(30, Student.avgAge(students));
	}

	@Test
	void testEmptyArrayThrowsException() {
		Student[] emptyArray = {};
		assertThrows(IllegalArgumentException.class, () -> Student.minAge(emptyArray));
	}

	// Параметризованные тесты
	@ParameterizedTest
	@ValueSource(ints = {18, 25, 50})
	void testValidAgeRange(int age) {
		Student student = new Student();
		student.setAge(age);
		assertEquals(age, student.getAge());
	}

	@ParameterizedTest
	@ValueSource(ints = {-100, 0, 17, 51, 100})
	void testInvalidAgeRange(int age) {
		Student student = new Student();
		student.setAge(age);
		assertTrue(student.getAge() >= 18 && student.getAge() <= 50);
	}

	// Дополнительные тесты для конструктора
	@Test
	void testDefaultConstructorInitialization() {
		Student student = new Student();
		assertNull(student.getFirstName());
		assertNull(student.getLastName());
		assertEquals(0, student.getAge());
	}

	// Тесты для setFirstName()
	@Test
	void testSetFirstNameWithNull() {
		Student student = new Student();
		student.setFirstName(null);
		assertNull(student.getFirstName());
	}

	@Test
	void testSetFirstNameWithEmptyString() {
		Student student = new Student();
		student.setFirstName("");
		assertEquals("", student.getFirstName());
	}

	// Тесты для setLastName()
	@Test
	void testSetLastNameWithNull() {
		Student student = new Student();
		student.setLastName(null);
		assertNull(student.getLastName());
	}

	@Test
	void testSetLastNameWithSingleChar() {
		Student student = new Student();
		student.setLastName("a");
		assertEquals("a", student.getLastName());
	}

	// Тесты для maxAge()
	@Test
	void testMaxAgeWithEmptyArray() {
		Student[] emptyArray = {};
		assertThrows(IllegalArgumentException.class, () -> Student.maxAge(emptyArray));
	}

	@Test
	void testMaxAgeWithNullArray() {
		assertThrows(IllegalArgumentException.class, () -> Student.maxAge(null));
	}

	@Test
	void testMaxAgeWithNegativeAges() {
		Student s1 = new Student("A", "B", -10);
		Student s2 = new Student("C", "D", -5);
		Student[] students = {s1, s2};
		assertEquals(-5, Student.maxAge(students));
	}

	// Тесты для avgAge()
	@Test
	void testAvgAgeWithEmptyArray() {
		Student[] emptyArray = {};
		assertThrows(IllegalArgumentException.class, () -> Student.avgAge(emptyArray));
	}

	@Test
	void testAvgAgeWithNullArray() {
		assertThrows(IllegalArgumentException.class, () -> Student.avgAge(null));
	}

	// Тест на установку null имени
	@Test
	void testSetFirstNameNull() {
		Student student = new Student();
		student.setFirstName(null);
		assertNull(student.getFirstName());
	}

	// Тест на пустое имя
	@Test
	void testSetFirstNameEmpty() {
		Student student = new Student();
		student.setFirstName("");
		assertEquals("", student.getFirstName());
	}

	// Тест на односимвольное имя
	@Test
	void testSetFirstNameSingleChar() {
		Student student = new Student();
		student.setFirstName("a");
		assertEquals("A", student.getFirstName());
	}

	// Тест на null фамилию
	@Test
	void testSetLastNameNull() {
		Student student = new Student();
		student.setLastName(null);
		assertNull(student.getLastName());
	}

	// Тест maxAge с одним студентом
	@Test
	void testMaxAgeSingleStudent() {
		Student[] students = {new Student("A", "B", 25)};
		assertEquals(25, Student.maxAge(students));
	}

	// Тест avgAge с одним студентом
	@Test
	void testAvgAgeSingleStudent() {
		Student[] students = {new Student("A", "B", 30)};
		assertEquals(30, Student.avgAge(students));
	}

	// Тест для конструктора с null значениями
	@Test
	void testParameterizedConstructorWithNulls() {
		Student student = new Student(null, null, 20);
		assertNull(student.getFirstName());
		assertNull(student.getLastName());
		assertEquals(20, student.getAge());
	}

	// Тест для граничных значений возраста
	@Test
	void testAgeBoundaries() {
		Student student = new Student();

		student.setAge(17);
		assertEquals(18, student.getAge()); // проверка нижней границы

		student.setAge(51);
		assertEquals(18, student.getAge()); // проверка верхней границы
	}

	// Тест для метода getFullName() с null значениями
	@Test
	void testGetFullNameWithNulls() {
		Student student = new Student(null, null, 25);
		assertEquals("null null", student.getFullName());
	}
}