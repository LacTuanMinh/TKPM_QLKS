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
                    App.nhanVien.setValue(NhanVienDAO.getNhanVienById((int)object[0]));
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
}