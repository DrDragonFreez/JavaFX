package studentgroups;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import studentgroups.controller.RootLayoutController;
import studentgroups.controller.StudentEditDialogController;
import studentgroups.controller.StudentOverviewController;
import studentgroups.model.Student;
import studentgroups.model.StudentGroup;
import studentgroups.model.StudentGroupListWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.prefs.Preferences;

public class Main extends Application {

    private final ObservableList<StudentGroup> groups = FXCollections.observableArrayList();
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Студенческие группы");

        // Иконка
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/images/student_icon.png")));

        createSampleData();

        FXMLLoader rootLoader = new FXMLLoader(Main.class.getResource("view/RootLayout.fxml"));
        BorderPane rootLayout = rootLoader.load();

        RootLayoutController rootController = rootLoader.getController();
        rootController.setMainApp(this);

        FXMLLoader overviewLoader = new FXMLLoader(Main.class.getResource("view/StudentOverview.fxml"));
        AnchorPane overview = overviewLoader.load();
        rootLayout.setCenter(overview);

        StudentOverviewController overviewController = overviewLoader.getController();
        overviewController.setGroups(groups);
        overviewController.setMainApp(this);

        Scene scene = new Scene(rootLayout);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Загружаем последний файл при запуске
        File lastFile = getGroupFilePath();
        if (lastFile != null) {
            loadGroupDataFromFile(lastFile);
        }
    }

    // Геттеры для доступа из контроллеров
    public Stage getPrimaryStage() { return primaryStage; }
    public ObservableList<StudentGroup> getGroupData() { return groups; }

    // ===== СОХРАНЕНИЕ И ЗАГРУЗКА XML =====
    public void loadGroupDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(StudentGroupListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();
            StudentGroupListWrapper wrapper = (StudentGroupListWrapper) um.unmarshal(file);

            groups.clear();
            groups.addAll(wrapper.getGroups());
            setGroupFilePath(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveGroupDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(StudentGroupListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StudentGroupListWrapper wrapper = new StudentGroupListWrapper();
            wrapper.setGroups(groups);

            m.marshal(wrapper, file);
            setGroupFilePath(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== НАСТРОЙКИ =====
    public File getGroupFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String filePath = prefs.get("filePath", null);
        return filePath != null ? new File(filePath) : null;
    }

    public void setGroupFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
            primaryStage.setTitle("Студенческие группы - " + file.getName());
        } else {
            prefs.remove("filePath");
            primaryStage.setTitle("Студенческие группы");
        }
    }

    public boolean showStudentEditDialog(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/StudentEditDialog.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование студента");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setScene(new Scene(page));

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

    private void createSampleData() {
        // Твой старый метод, оставляем как есть (я его сократил для примера)
        StudentGroup group = new StudentGroup("ПИ-21", "КТ и ИБ", 2, "Очное");
        group.addStudent(new Student("Иванов", "Иван", "Иванович", LocalDate.of(2005, 3, 15), "+7 900 111-22-33", "ivanov@mail.ru"));
        groups.add(group);
    }

    public static void main(String[] args) {
        launch(args);
    }
}