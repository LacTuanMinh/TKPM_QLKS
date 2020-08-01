package ql.khachsan.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;
import ql.khachsan.App;
import ql.khachsan.models.NhanVien;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField userName;
    public PasswordField password;
    public Button loginBtn;
    public Label alert;

    private List<NhanVien> list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Session session = App.sessionFactory.getCurrentSession();

        try {

            session.getTransaction().begin();
            list = session.createNativeQuery("select * from nhanvien", NhanVien.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception ex) {
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

    public void loginBtn_Clicked(ActionEvent actionEvent) throws IOException {


        if (userName.getText().equals("") || password.getText().equals("")) {
            alert.setText("Vui lòng nhập đầy đủ thông tin.");
            alert.setVisible(true);
        } else {
            for (NhanVien nv : list) {
                if (userName.getText().equals(nv.getTenTaiKhoan())) {
                    if (BCrypt.checkpw(password.getText(), nv.getMatKhau())) {
                        App.nhanvien.setValue(nv);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Đăng nhập thành công!");
                        alert.showAndWait();
                        ((Stage) this.loginBtn.getScene().getWindow()).close();

                        HomeController controller = new HomeController();
                        controller.homeWindow();
                        return;
                    } else {
                        alert.setText("Mật khẩu không đúng");
                        alert.setVisible(true);
                        return;
                    }
                }
            }
            alert.setText("Không tồn tại tài khoản");
            alert.setVisible(true);
        }
    }
}
