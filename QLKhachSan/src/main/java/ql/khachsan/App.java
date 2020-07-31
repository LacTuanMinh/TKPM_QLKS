package ql.khachsan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.hibernate.SessionFactory;
import ql.khachsan.controllers.PrimaryController;
import ql.khachsan.utils.HibernateUtils;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
    public static Stage homeStage;

    private static PrimaryController controller;
    public static SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = getFXMLLoader("primary");
        Parent root = loader.load();

        controller = loader.getController();

        scene = new Scene(root, 1300, 700);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Trang chủ");
        homeStage = new Stage();
        homeStage = stage;

        stage.show();
    }

    public static FXMLLoader getFXMLLoader(String fxml) throws IOException {
        return new FXMLLoader(App.class.getResource("/ql/khachsan/views/" + fxml + ".fxml"));
    }

    public static void main(String[] args) {
        launch();
    }

}