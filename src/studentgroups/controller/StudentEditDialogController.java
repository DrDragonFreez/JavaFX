package studentgroups.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import studentgroups.model.Student;
import studentgroups.util.DateUtil;

import java.time.LocalDate;

public class StudentEditDialogController {

    @FXML private TextField surnameField;
    @FXML private TextField nameField;
    @FXML private TextField patronymicField;
    @FXML private TextField birthDateField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;

    private Stage dialogStage;
    private Student student;
    private boolean okClicked = false;

    @FXML
    private void initialize() {}

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setStudent(Student student) {
        this.student = student;
        surnameField.setText(student.getSurname());
        nameField.setText(student.getName());
        patronymicField.setText(student.getPatronymic());
        birthDateField.setText(student.getBirthDate() == null
                ? "" : DateUtil.format(student.getBirthDate()));
        birthDateField.setPromptText("дд.мм.гггг");
        phoneField.setText(student.getPhone());
        emailField.setText(student.getEmail());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            student.setSurname(surnameField.getText());
            student.setName(nameField.getText());
            student.setPatronymic(patronymicField.getText());
            student.setBirthDate(DateUtil.parse(birthDateField.getText()));
            student.setPhone(phoneField.getText());
            student.setEmail(emailField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (isEmpty(surnameField.getText()))
            errorMessage += "Не введена фамилия!\n";
        if (isEmpty(nameField.getText()))
            errorMessage += "Не введено имя!\n";
        if (isEmpty(patronymicField.getText()))
            errorMessage += "Не введено отчество!\n";

        if (isEmpty(birthDateField.getText())) {
            errorMessage += "Не введена дата рождения!\n";
        } else if (!DateUtil.validDate(birthDateField.getText())) {
            errorMessage += "Неверная дата. Используйте формат дд.мм.гггг!\n";
        }

        if (isEmpty(phoneField.getText()))
            errorMessage += "Не введён телефон!\n";
        if (isEmpty(emailField.getText()))
            errorMessage += "Не введён E-mail!\n";

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Неверные поля");
            alert.setHeaderText("Пожалуйста, исправьте введённые данные");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}