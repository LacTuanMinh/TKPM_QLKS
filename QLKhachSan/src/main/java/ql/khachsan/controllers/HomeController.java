package ql.khachsan.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ql.khachsan.App;

public class HomeController {

    @FXML
    public void homeWindow() throws IOException {
        FXMLLoader loader = App.getFXMLLoader("home");
        Parent root = loader.load();
        Scene scene = new Scene(root, 1120, 420);
        Stage stage = new Stage();
        stage.setTitle("Trang chủ");
        stage.setScene(scene);
        stage.setResizable(false);
        // Wait until this stage is complete
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.initOwner(App.homeStage);
        stage.show();
    }
}