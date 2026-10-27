package studentgroups.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE) // Игнорировать всё, что не помечено @XmlElement
public class StudentGroup {

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty faculty = new SimpleStringProperty();
    private final IntegerProperty course = new SimpleIntegerProperty();
    private final StringProperty educationType = new SimpleStringProperty();

    private final ObservableList<Student> students;

    public StudentGroup() {
        this(null, null, 0, null);
    }

    public StudentGroup(String name, String faculty, int course, String educationType) {
        this.name.set(name);
        this.faculty.set(faculty);
        this.course.set(course);
        this.educationType.set(educationType);
        students = FXCollections.observableArrayList();
    }

    // --- Обычные геттеры/сеттеры для JAXB ---
    @XmlElement(name = "name")
    public String getName() { return name.get(); }
    public void setName(String value) { name.set(value); }

    @XmlElement(name = "faculty")
    public String getFaculty() { return faculty.get(); }
    public void setFaculty(String value) { faculty.set(value); }

    @XmlElement(name = "course")
    public int getCourse() { return course.get(); }
    public void setCourse(int value) { course.set(value); }

    @XmlElement(name = "educationType")
    public String getEducationType() { return educationType.get(); }
    public void setEducationType(String value) { educationType.set(value); }

    @XmlElementWrapper(name = "students")
    @XmlElement(name = "student")
    public ObservableList<Student> getStudents() { return students; }

    public void setStudents(List<Student> students) {
        this.students.clear();
        if (students != null) {
            this.students.addAll(students);
        }
    }

    // --- JavaFX Properties (без аннотаций JAXB) ---
    public StringProperty nameProperty() { return name; }
    public StringProperty facultyProperty() { return faculty; }
    public IntegerProperty courseProperty() { return course; }
    public StringProperty educationTypeProperty() { return educationType; }

    public void addStudent(Student student) {
        students.add(student);
    }
}