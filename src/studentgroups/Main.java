package studentgroups;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import studentgroups.controller.StudentOverviewController;
import studentgroups.model.Student;
import studentgroups.model.StudentGroup;

import java.io.IOException;
import java.time.LocalDate;

import javafx.stage.Modality;
import javafx.scene.Scene;
import studentgroups.controller.StudentEditDialogController;

import javafx.scene.image.Image;

public class Main extends Application {

    private final ObservableList<StudentGroup> groups = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws IOException {
        createSampleData();

        FXMLLoader rootLoader = new FXMLLoader(Main.class.getResource("view/RootLayout.fxml"));
        BorderPane rootLayout = rootLoader.load();

        FXMLLoader overviewLoader = new FXMLLoader(Main.class.getResource("view/StudentOverview.fxml"));
        AnchorPane overview = overviewLoader.load();
        rootLayout.setCenter(overview);

        StudentOverviewController overviewController = overviewLoader.getController();
        overviewController.setGroups(groups);
        overviewController.setMainApp(this);

        Scene scene = new Scene(rootLayout);
        primaryStage.setTitle("Студенческие группы");
        primaryStage.getIcons().add(new Image("file:src/resources/images/student_icon.png"));
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createSampleData() {
        StudentGroup softwareEngineeringGroup = new StudentGroup(
                "ПИ-21", "КТ и ИБ", 2, "Очное"
        );
        softwareEngineeringGroup.addStudent(new Student(
                "Иванов", "Иван", "Иванович", LocalDate.of(2005, 3, 15),
                "+7 900 111-22-33", "ivanov@mail.ru"
        ));
        softwareEngineeringGroup.addStudent(new Student(
                "Петров", "Петр", "Петрович", LocalDate.of(2005, 7, 21),
                "+7 900 222-33-44", "petrov@mail.ru"
        ));
        softwareEngineeringGroup.addStudent(new Student(
                "Смирнова", "Мария", "Александровна", LocalDate.of(2005, 1, 30),
                "+7 900 555-66-77", "smirnova@mail.ru"
        ));

        StudentGroup informationSystemsGroup = new StudentGroup(
                "ИС-22", "КТ и ИБ", 3, "Заочное"
        );
        informationSystemsGroup.addStudent(new Student(
                "Сидоров", "Алексей", "Александрович", LocalDate.of(2004, 12, 10),
                "+7 900 333-44-55", "sidorov@mail.ru"
        ));
        informationSystemsGroup.addStudent(new Student(
                "Кузнецов", "Дмитрий", "Сергеевич", LocalDate.of(2005, 5, 8),
                "+7 900 444-55-66", "kuznetsov@mail.ru"
        ));
        informationSystemsGroup.addStudent(new Student(
                "Морозова", "Елена", "Викторовна", LocalDate.of(2004, 9, 17),
                "+7 900 666-77-88", "morozova@mail.ru"
        ));

        StudentGroup cybersecurityGroup = new StudentGroup(
                "ИБ-20", "КТ и ИБ", 4, "Очное"
        );
        cybersecurityGroup.addStudent(new Student(
                "Волков", "Никита", "Андреевич", LocalDate.of(2003, 11, 5),
                "+7 900 777-88-99", "volkov@mail.ru"
        ));
        cybersecurityGroup.addStudent(new Student(
                "Новиков", "Артём", "Игоревич", LocalDate.of(2003, 6, 22),
                "+7 900 888-99-00", "novikov@mail.ru"
        ));

        groups.addAll(
                softwareEngineeringGroup,
                informationSystemsGroup,
                cybersecurityGroup
        );
    }
    /**
     * Открывает диалог редактирования студента.
     * Возвращает true, если пользователь нажал OK.
     */
    public boolean showStudentEditDialog(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Main.class.getResource("view/StudentEditDialog.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование студента");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            StudentEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setStudent(student);

            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
