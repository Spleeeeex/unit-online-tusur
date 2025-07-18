package online_tusur.unit_online_tusur;
import online_tusur.unit_online_tusur.Student;

import java.util.Arrays;


public class Main {

	//главный метод класса
	public static void main(String[] args) {
		// s1, s2, s3 - объекты класса Student
		Student s1 = new Student();
		s1.setFirstName("ivAN");
		s1.setLastName("SIDORov");
		s1.setAge(25);

		Student s2 = new Student("Ivan", "nIkulin", 87);

		Student s3 = new Student("kONStantin","petrov", 33);

		Student s4 = new Student("evgeny","Kataev", 27);

		System.out.println(s1.getFullName() + ", " + s1.getAge());
		System.out.println(s2.getFullName() + ", " + s2.getAge());
		System.out.println(s3.getFullName() + ", " + s3.getAge());
		System.out.println(s4.getFullName() + ", " + s4.getAge());


		Student[] students = { s1,s2,s3};
		Student[] students2 = {new Student("Alexandr", "Ivanov", 20), new Student("Vladimir", "Petrov", 18),
				new Student("Aleksey", "Alekseev", 35)};

		System.out.println("AVG= "+Student.avgAge(students2));
		System.out.println("MAX= "+Student.maxAge(students));
		System.out.println("MIN= "+Student.minAge(students));

	}
}