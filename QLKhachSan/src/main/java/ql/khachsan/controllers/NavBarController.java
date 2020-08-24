package ql.khachsan.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import ql.khachsan.App;
import ql.khachsan.models.entities.NhanVien;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
        App.nhanVien.setValue(null);
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
        userName.setText(App.nhanVien.getValue().getTenTaiKhoan());
        App.nhanVien.addListener(new ChangeListener<NhanVien>() {
            @Override
            public void changed(ObservableValue<? extends NhanVien> observableValue, NhanVien nhanVien, NhanVien t1) {
                if (t1 != null) {
                    userName.setText(t1.getTenTaiKhoan());
                }
            }
        });
    }

    public void taiKhoan_Clicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = App.getFXMLLoader("thongTinCaNhan");
        Parent root = loader.load();
        Scene scene = new Scene(root, 720, 370);
        Stage stage = new Stage();
        stage.setTitle("Thông tin cá nhân");
        stage.setScene(scene);
        stage.setResizable(false);

        // Wait until this stage is complete
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.homeStage);
        stage.show();
    }
}
