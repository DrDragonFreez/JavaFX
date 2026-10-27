package studentgroups.model;

import studentgroups.util.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.*;

@XmlAccessorType(XmlAccessType.NONE) // Игнорировать всё, что не помечено @XmlElement
public class Student {

    private final StringProperty surname = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty patronymic = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> birthDate = new SimpleObjectProperty<>();
    private final StringProperty phone = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();

    public Student() {
        this(null, null, null, null, null, null);
    }

    public Student(String surname, String name, String patronymic,
                   LocalDate birthDate, String phone, String email) {
        this.surname.set(surname);
        this.name.set(name);
        this.patronymic.set(patronymic);
        this.birthDate.set(birthDate);
        this.phone.set(phone);
        this.email.set(email);
    }

    // --- Обычные геттеры/сеттеры для JAXB ---
    @XmlElement(name = "surname")
    public String getSurname() { return surname.get(); }
    public void setSurname(String value) { surname.set(value); }

    @XmlElement(name = "name")
    public String getName() { return name.get(); }
    public void setName(String value) { name.set(value); }

    @XmlElement(name = "patronymic")
    public String getPatronymic() { return patronymic.get(); }
    public void setPatronymic(String value) { patronymic.set(value); }

    @XmlElement(name = "birthDate")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getBirthDate() { return birthDate.get(); }
    public void setBirthDate(LocalDate value) { birthDate.set(value); }

    @XmlElement(name = "phone")
    public String getPhone() { return phone.get(); }
    public void setPhone(String value) { phone.set(value); }

    @XmlElement(name = "email")
    public String getEmail() { return email.get(); }
    public void setEmail(String value) { email.set(value); }


    // --- JavaFX Properties (без аннотаций JAXB) ---
    public StringProperty surnameProperty() { return surname; }
    public StringProperty nameProperty() { return name; }
    public StringProperty patronymicProperty() { return patronymic; }
    public ObjectProperty<LocalDate> birthDateProperty() { return birthDate; }
    public StringProperty phoneProperty() { return phone; }
    public StringProperty emailProperty() { return email; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return getSurname() + " " + getName() + " " + getPatronymic() +
                " | " + (getBirthDate() != null ? getBirthDate().format(formatter) : "") +
                " | " + getPhone() + " | " + getEmail();
    }
}