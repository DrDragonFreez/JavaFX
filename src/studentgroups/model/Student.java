package studentgroups.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.*;

public class Student {

    private final StringProperty surname = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty patronymic = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> birthDate = new SimpleObjectProperty<>();
    private final StringProperty phone = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();

    public Student(String surname, String name, String patronymic,
                   LocalDate birthDate, String phone, String email) {
        this.surname.set(surname);
        this.name.set(name);
        this.patronymic.set(patronymic);
        this.birthDate.set(birthDate);
        this.phone.set(phone);
        this.email.set(email);
    }

    // Фамилия
    public String getSurname() { return surname.get(); }
    public void setSurname(String value) { surname.set(value); }
    public StringProperty surnameProperty() { return surname; }

    // Имя
    public String getName() { return name.get(); }
    public void setName(String value) { name.set(value); }
    public StringProperty nameProperty() { return name; }

    // Отчество
    public String getPatronymic() { return patronymic.get(); }
    public void setPatronymic(String value) { patronymic.set(value); }
    public StringProperty patronymicProperty() { return patronymic; }

    // Дата рождения
    public LocalDate getBirthDate() { return birthDate.get(); }
    public void setBirthDate(LocalDate value) { birthDate.set(value); }
    public ObjectProperty<LocalDate> birthDateProperty() { return birthDate; }

    // Телефон
    public String getPhone() { return phone.get(); }
    public void setPhone(String value) { phone.set(value); }
    public StringProperty phoneProperty() { return phone; }

    // E-mail
    public String getEmail() { return email.get(); }
    public void setEmail(String value) { email.set(value); }
    public StringProperty emailProperty() { return email; }

    public void printInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        System.out.println(
                "ФИО: " + getSurname() + " " + getName() + " " + getPatronymic() +
                        ", дата рождения: " + getBirthDate().format(formatter) +
                        ", телефон: " + getPhone() +
                        ", E-mail: " + getEmail()
        );
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return getSurname() + " " + getName() + " " + getPatronymic() +
                " | " + getBirthDate().format(formatter) +
                " | " + getPhone() +
                " | " + getEmail();
    }
}