package studentgroups.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import studentgroups.Main;
import studentgroups.model.Student;
import studentgroups.model.StudentGroup;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.Alert;

public class StudentOverviewController {

    private static final String EMPTY_VALUE = "—";
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final ObservableList<Student> emptyStudents =
            FXCollections.observableArrayList();

    // ===== Таблица групп =====
    @FXML private TableView<StudentGroup> groupsTable;
    @FXML private TableColumn<StudentGroup, String> groupNameColumn;
    @FXML private TableColumn<StudentGroup, String> facultyColumn;
    @FXML private TableColumn<StudentGroup, Number> courseColumn;
    @FXML private TableColumn<StudentGroup, String> educationTypeColumn;

    @FXML private Label groupNameLabel;
    @FXML private Label groupFacultyLabel;
    @FXML private Label groupCourseLabel;
    @FXML private Label groupEducationTypeLabel;

    // ===== Таблица студентов =====
    @FXML private TableView<Student> studentsTable;
    @FXML private TableColumn<Student, String> surnameColumn;
    @FXML private TableColumn<Student, String> studentNameColumn;
    @FXML private TableColumn<Student, String> patronymicColumn;
    @FXML private TableColumn<Student, LocalDate> birthDateColumn;

    @FXML private Label surnameLabel;
    @FXML private Label nameLabel;
    @FXML private Label patronymicLabel;
    @FXML private Label birthDateLabel;
    @FXML private Label phoneLabel;
    @FXML private Label emailLabel;

    @FXML
    private void initialize() {
        groupsTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_NEXT_COLUMN);
        studentsTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_NEXT_COLUMN);

        // ===== Группы: привязка напрямую к Properties модели =====
        groupNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().nameProperty());
        facultyColumn.setCellValueFactory(
                cellData -> cellData.getValue().facultyProperty());
        courseColumn.setCellValueFactory(
                cellData -> cellData.getValue().courseProperty());
        educationTypeColumn.setCellValueFactory(
                cellData -> cellData.getValue().educationTypeProperty());

        // ===== Студенты: привязка напрямую к Properties модели =====
        surnameColumn.setCellValueFactory(
                cellData -> cellData.getValue().surnameProperty());
        studentNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().nameProperty());
        patronymicColumn.setCellValueFactory(
                cellData -> cellData.getValue().patronymicProperty());

        // Форматирование даты в колонке (пример CellFactory)
        birthDateColumn.setCellValueFactory(
                cellData -> cellData.getValue().birthDateProperty());
        birthDateColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(DATE_FORMATTER.format(item));
                }
            }
        });

        // ===== Слушатели выделения строк =====
        groupsTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldG, newG) -> showGroupDetails(newG));
        studentsTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldS, newS) -> showStudentDetails(newS));

        showGroupDetails(null);
        showStudentDetails(null);
    }

    public void setGroups(ObservableList<StudentGroup> groups) {
        groupsTable.setItems(groups);
        if (!groups.isEmpty()) {
            groupsTable.getSelectionModel().selectFirst();
        }
    }

    private void showGroupDetails(StudentGroup group) {
        if (group == null) {
            groupNameLabel.setText(EMPTY_VALUE);
            groupFacultyLabel.setText(EMPTY_VALUE);
            groupCourseLabel.setText(EMPTY_VALUE);
            groupEducationTypeLabel.setText(EMPTY_VALUE);
            studentsTable.setItems(emptyStudents);
            showStudentDetails(null);
            return;
        }
        groupNameLabel.setText(group.getName());
        groupFacultyLabel.setText(group.getFaculty());
        groupCourseLabel.setText(String.valueOf(group.getCourse()));
        groupEducationTypeLabel.setText(group.getEducationType());
        studentsTable.setItems(group.getStudents());
        studentsTable.getSelectionModel().selectFirst();
    }

    private void showStudentDetails(Student student) {
        if (student == null) {
            surnameLabel.setText(EMPTY_VALUE);
            nameLabel.setText(EMPTY_VALUE);
            patronymicLabel.setText(EMPTY_VALUE);
            birthDateLabel.setText(EMPTY_VALUE);
            phoneLabel.setText(EMPTY_VALUE);
            emailLabel.setText(EMPTY_VALUE);
            return;
        }
        surnameLabel.setText(student.getSurname());
        nameLabel.setText(student.getName());
        patronymicLabel.setText(student.getPatronymic());
        birthDateLabel.setText(
                student.getBirthDate() == null
                        ? EMPTY_VALUE
                        : DATE_FORMATTER.format(student.getBirthDate()));
        phoneLabel.setText(student.getPhone());
        emailLabel.setText(student.getEmail());
    }

    @FXML
    private void handleDeleteStudent() {
        int selectedIndex = studentsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            studentsTable.getItems().remove(selectedIndex);
        } else {
            // Ничего не выбрано — показываем предупреждение
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Нет выбора");
            alert.setHeaderText("Студент не выбран");
            alert.setContentText("Пожалуйста, выберите студента в таблице.");
            alert.showAndWait();
        }
    }
    @FXML
    private void handleNewStudent() {
        Student tempStudent = new Student("", "", "", null, "", "");
        boolean okClicked = mainApp.showStudentEditDialog(tempStudent);
        if (okClicked) {
            StudentGroup selectedGroup = groupsTable.getSelectionModel().getSelectedItem();
            if (selectedGroup != null) {
                selectedGroup.addStudent(tempStudent);
            }
        }
    }

    @FXML
    private void handleEditStudent() {
        Student selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            boolean okClicked = mainApp.showStudentEditDialog(selectedStudent);
            if (okClicked) {
                showStudentDetails(selectedStudent);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Нет выбора");
            alert.setHeaderText("Студент не выбран");
            alert.setContentText("Пожалуйста, выберите студента в таблице.");
            alert.showAndWait();
        }
    }
    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}