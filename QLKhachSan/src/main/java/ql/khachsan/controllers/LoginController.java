package ql.khachsan.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ql.khachsan.App;
import ql.khachsan.DAO.NhanVienDAO;
import ql.khachsan.models.NhanVien;
import ql.khachsan.utils.PasswordUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField userName;
    public PasswordField password;
    public Button loginBtn;
    public Label alert;

    public Button qlNhanVienButton;
    public Button qlPhongButton;
    public Button qlLoaiPhongButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void loginBtn_Clicked(ActionEvent actionEvent) throws IOException {
        if (userName.getText().equals("") || password.getText().equals("")) {
            alert.setText("Vui lòng nhập đầy đủ thông tin.");
            alert.setVisible(true);
        }
        else {
            System.out.println(PasswordUtils.hash(password.getText()));

            Object[] object = NhanVienDAO.getIdAndPasswordByUserName(userName.getText());
            if (object == null) {
                alert.setText("Không tồn tại tài khoản");
                alert.setVisible(true);
            }
            else {
                if (PasswordUtils.verifyHash(password.getText(), (String)object[1])) {

                    App.nhanvien.setValue(NhanVienDAO.getNhanVienById((int)object[0]));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Đăng nhập thành công!");
                    alert.showAndWait();
                    ((Stage)this.loginBtn.getScene().getWindow()).close();

                    HomeController controller = new HomeController();
                    controller.homeWindow();
                }
                else {
                    alert.setText("Sai mật khẩu");
                    alert.setVisible(true);
                }
            }
        }
    }


    public void qlPhongButtonClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = App.getFXMLLoader("qlPhong");
        Parent root = loader.load();
        Scene scene = new Scene(root, 420, 520);
        Stage stage = new Stage();
        stage.setTitle("Quản lý phòng");
        stage.setScene(scene);
        stage.setResizable(false);

        // Wait until this stage is complete
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.homeStage);
        stage.show();
    }

    public void qlLoaiPhongButtonClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = App.getFXMLLoader("qlLoaiPhong");
        Parent root = loader.load();
        Scene scene = new Scene(root, 420, 520);
        Stage stage = new Stage();
        stage.setTitle("Quản lý loại phòng");
        stage.setScene(scene);
        stage.setResizable(false);

        // Wait until this stage is complete
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.homeStage);
        stage.show();
    }
}
