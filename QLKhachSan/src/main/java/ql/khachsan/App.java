package ql.khachsan;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.hibernate.SessionFactory;
import ql.khachsan.controllers.LoginController;
import ql.khachsan.models.entities.NhanVien;
import ql.khachsan.utils.HibernateUtils;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    public static Scene scene;
    public static Stage homeStage;
    public static LoginController controller;
    public static SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
    public static ObjectProperty<NhanVien> nhanVien = new SimpleObjectProperty<>();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = getFXMLLoader("login");
        Parent root = loader.load();

        controller = loader.getController();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Đăng nhập");
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