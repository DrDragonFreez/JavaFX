package studentgroups.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class RootLayoutController {

    @FXML
    private void handleExit() {
        Platform.exit();
    }
}
