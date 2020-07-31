package ql.khachsan.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.hibernate.Session;
import ql.khachsan.App;

public class PrimaryController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Session session = App.sessionFactory.getCurrentSession();

        try {
            session.getTransaction().begin();
            // do something

        } catch(Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }

        session.close();
    }

    @FXML
    private void switchToSecondary() throws IOException {
        FXMLLoader loader = App.getFXMLLoader("secondary");
        Parent root = loader.load();
        Scene scene = new Scene(root, 1120, 420);
        Stage stage = new Stage();
        stage.setTitle("Dang nhap");
        stage.setScene(scene);
        stage.setResizable(false);

        // Wait until this stage is complete
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.homeStage);
        stage.show();
    }
}
