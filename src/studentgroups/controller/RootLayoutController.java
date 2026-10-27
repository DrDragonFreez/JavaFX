package studentgroups.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import studentgroups.Main;

import java.io.File;

public class RootLayoutController {

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleNew() {
        mainApp.getGroupData().clear();
        mainApp.setGroupFilePath(null);
    }

    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML файлы (*.xml)", "*.xml"));

        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if (file != null) {
            mainApp.loadGroupDataFromFile(file);
        }
    }

    @FXML
    private void handleSave() {
        File file = mainApp.getGroupFilePath();
        if (file != null) {
            mainApp.saveGroupDataToFile(file);
        } else {
            handleSaveAs();
        }
    }

    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML файлы (*.xml)", "*.xml"));

        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
        if (file != null) {
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.saveGroupDataToFile(file);
        }
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }
}