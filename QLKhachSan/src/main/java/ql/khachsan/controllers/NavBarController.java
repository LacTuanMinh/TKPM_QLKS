package ql.khachsan.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import ql.khachsan.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static ql.khachsan.App.getFXMLLoader;

public class NavBarController implements Initializable {
    @FXML
    Button taiKhoan;
    @FXML
    Button dangXuat;
    @FXML
    Button dangNhap;
    @FXML
    Button dangKy;
    @FXML
    AnchorPane container;
    @FXML
    Label userName;
    @FXML
    HBox btnContainer;


    public void dangXuat_Clicked(ActionEvent actionEvent) throws IOException {
        App.nhanvien.setValue(null);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Bạn đã đăng xuất khỏi hệ thống");
        alert.showAndWait();
        App.homeStage.close();

        FXMLLoader loader = App.getFXMLLoader("login");
        Parent root = loader.load();
        App.controller = loader.getController();
        App.scene = new Scene(root);
        App.homeStage.setScene(App.scene);
        App.homeStage.setResizable(false);
        App.homeStage.setTitle("Đăng nhập");
        App.homeStage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userName.setText(App.nhanvien.getValue().getTenTaiKhoan());
    }

    public void taiKhoan_Clicked(ActionEvent actionEvent) throws IOException {

    }
}