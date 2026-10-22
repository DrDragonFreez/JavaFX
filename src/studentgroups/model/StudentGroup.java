package studentgroups.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.Comparator;
import java.util.function.Predicate;

public class StudentGroup {

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty faculty = new SimpleStringProperty();
    private final IntegerProperty course = new SimpleIntegerProperty();
    private final StringProperty educationType = new SimpleStringProperty();

    private final ObservableList<Student> students;

    public StudentGroup(String name, String faculty,
                        int course, String educationType) {
        this.name.set(name);
        this.faculty.set(faculty);
        this.course.set(course);
        this.educationType.set(educationType);
        students = FXCollections.observableArrayList();
    }

    // Наименование
    public String getName() { return name.get(); }
    public void setName(String value) { name.set(value); }
    public StringProperty nameProperty() { return name; }

    // Факультет
    public String getFaculty() { return faculty.get(); }
    public void setFaculty(String value) { faculty.set(value); }
    public StringProperty facultyProperty() { return faculty; }

    // Курс
    public int getCourse() { return course.get(); }
    public void setCourse(int value) { course.set(value); }
    public IntegerProperty courseProperty() { return course; }

    // Вид обучения
    public String getEducationType() { return educationType.get(); }
    public void setEducationType(String value) { educationType.set(value); }
    public StringProperty educationTypeProperty() { return educationType; }

    public ObservableList<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void showGroupInfo() {
        System.out.println("\n===== ИНФОРМАЦИЯ О ГРУППЕ =====");
        System.out.println("Наименование: " + getName());
        System.out.println("Факультет: " + getFaculty());
        System.out.println("Курс: " + getCourse());
        System.out.println("Вид обучения: " + getEducationType());
        System.out.println("Количество студентов: " + students.size());
    }

    public void showStudents() {
        System.out.println("\n===== СПИСОК СТУДЕНТОВ =====");
        if (students.isEmpty()) {
            System.out.println("Список студентов пуст.");
            return;
        }
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public void sortStudentsBySurname() {
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s1.getSurname().compareToIgnoreCase(s2.getSurname());
            }
        });
        System.out.println("Сортировка анонимным классом выполнена.");
    }

    public void findStudent(String surname) {
        System.out.println("\n===== РЕЗУЛЬТАТ ПОИСКА =====");
        boolean found = false;
        for (Student student : students) {
            if (student.getSurname().equalsIgnoreCase(surname)) {
                System.out.println(student);
                found = true;
            }
        }
        if (!found) System.out.println("Студент не найден.");
    }

    public void showGroupInfoUsingAnonymousClass() {
        GroupInfo info = new GroupInfo() {
            @Override
            public void print() {
                System.out.println("\n===== АНОНИМНЫЙ КЛАСС =====");
                System.out.println("Наименование: " + getName());
                System.out.println("Факультет: " + getFaculty());
                System.out.println("Курс: " + getCourse());
                System.out.println("Вид обучения: " + getEducationType());
            }
        };
        info.print();
    }

    public void sortStudentsBySurnameLambda() {
        students.sort((s1, s2) ->
                s1.getSurname().compareToIgnoreCase(s2.getSurname()));
        System.out.println("\nСортировка с помощью лямбда-выражения выполнена.");
    }

    public void findStudentLambda(String surname) {
        System.out.println("\n===== ПОИСК С ПОМОЩЬЮ ЛЯМБДА =====");
        students.stream()
                .filter(s -> s.getSurname().equalsIgnoreCase(surname))
                .forEach(System.out::println);
    }

    public void filterStudents(Predicate<Student> condition) {
        System.out.println("\n===== РЕЗУЛЬТАТ ФИЛЬТРАЦИИ =====");
        students.stream().filter(condition).forEach(System.out::println);
    }
}