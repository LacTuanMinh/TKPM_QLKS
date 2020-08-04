package ql.khachsan.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ql.khachsan.App;
import ql.khachsan.DAO.NhanVienDAO;
import ql.khachsan.models.NhanVien;
import ql.khachsan.utils.PasswordUtils;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class ThongTinCaNhanController implements Initializable {
    public TextField tenTaiKhoan;
    public TextField hoTen;
    public TextField ngaySinh;
    public TextField soDienThoai;
    public RadioButton maleButton;
    public RadioButton femaleButton;
    public TextField queQuan;
    public TextField diaChi;
    public Text ngayBatDauDiLam;
    public Text luongThang;
    public Text chucVu;
    public PasswordField matKhau;
    public Button cancelButton;
    public Button saveButton;
    public Button editButton;
    public Button changePasswordButton;

    private NhanVien oldValue;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        oldValue = App.nhanvien.get();

        ToggleGroup group = new ToggleGroup();
        maleButton.setToggleGroup(group);
        femaleButton.setToggleGroup(group);

        if (App.nhanvien.getValue().getGioiTinh().equals("Nam")) {
            maleButton.setSelected(true);
        }
        else {
            femaleButton.setSelected(true);
        }

        tenTaiKhoan.setText(App.nhanvien.getValue().getTenTaiKhoan());
        hoTen.setText(App.nhanvien.getValue().getHoTen());
        ngaySinh.setText(format.format(App.nhanvien.getValue().getNgaySinh()));
        soDienThoai.setText(App.nhanvien.getValue().getSoDienThoai());
        queQuan.setText(App.nhanvien.getValue().getQueQuan());
        diaChi.setText(App.nhanvien.getValue().getDiaChi());
        ngayBatDauDiLam.setText(format.format(App.nhanvien.getValue().getNgayBatDauDiLam()));
        luongThang.setText(String.format("%.0f", App.nhanvien.getValue().getLuongThang()));
        chucVu.setText(App.nhanvien.get().getLoaiNhanVien().getTenLoaiNhanVien());
    }

    public void changePasswordButtonClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = App.getFXMLLoader("doiMatKhau");
        Parent root = loader.load();
        Scene scene = new Scene(root, 420, 320);
        Stage stage = new Stage();
        stage.setTitle("Đổi mật khẩu");
        stage.setScene(scene);
        stage.setResizable(false);

        // Wait until this stage is close
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.homeStage);
        stage.show();
    }

    private void resetValues() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        if (oldValue.getGioiTinh().equals("Nam")) {
            maleButton.setSelected(true);
            femaleButton.setSelected(false);
        }
        else {
            maleButton.setSelected(false);
            femaleButton.setSelected(true);
        }

        tenTaiKhoan.setText(oldValue.getTenTaiKhoan());
        hoTen.setText(oldValue.getHoTen());
        ngaySinh.setText(format.format(oldValue.getNgaySinh()));
        soDienThoai.setText(oldValue.getSoDienThoai());
        queQuan.setText(oldValue.getQueQuan());
        diaChi.setText(oldValue.getDiaChi());
        ngayBatDauDiLam.setText(format.format(oldValue.getNgayBatDauDiLam()));
        luongThang.setText(String.format("%.0f", oldValue.getLuongThang()));
        chucVu.setText(oldValue.getLoaiNhanVien().getTenLoaiNhanVien());

        tenTaiKhoan.setEditable(false);
        hoTen.setEditable(false);
        ngaySinh.setEditable(false);
        soDienThoai.setEditable(false);
        diaChi.setEditable(false);
        queQuan.setEditable(false);
        matKhau.setEditable(false);
        maleButton.setDisable(true);
        femaleButton.setDisable(true);

        cancelButton.setDisable(true);
        saveButton.setDisable(true);
    }

    private int checkInput() {
        // Ô nhập liệu rỗng
        if (tenTaiKhoan.getText().equals("") || hoTen.getText().equals("") ||
                ngaySinh.getText().equals("") || luongThang.getText().equals("") ||
                queQuan.getText().equals("") || diaChi.getText().equals("") ||
                ngayBatDauDiLam.getText().equals("") || soDienThoai.getText().equals("")) {
            return -1;
        }
        else if (!PasswordUtils.verifyHash(matKhau.getText(), App.nhanvien.getValue().getMatKhau())) {
            return -2;
        }
        return 0;
    }

    private void showAlert(int check) {
        if (check == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Các vùng nhập liệu không hợp lệ");
            alert.setContentText("Các vùng nhập liệu không thể bỏ trống");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
        else if (check == -2) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mật khẩu hiện tại không đúng");
            alert.setContentText("Mật khẩu hiện tại không hợp lệ, vui lòng kiểm tra lại");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

    public void cancelButtonClicked(ActionEvent actionEvent) {
        resetValues();
    }

    public void saveButtonClicked(ActionEvent actionEvent) throws ParseException {
        int check = checkInput();
        if (check != 0) {
            showAlert(check);
        }
        else {
            NhanVien nhanVien = App.nhanvien.getValue();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            nhanVien.setTenTaiKhoan(tenTaiKhoan.getText());
            nhanVien.setHoTen(hoTen.getText());
            nhanVien.setNgaySinh(format.parse(ngaySinh.getText()));
            nhanVien.setSoDienThoai(soDienThoai.getText());
            nhanVien.setDiaChi(diaChi.getText());
            nhanVien.setQueQuan(queQuan.getText());
            nhanVien.setGioiTinh(maleButton.isSelected() ? "Nam" : "Nữ");

            NhanVienDAO.update(nhanVien);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Chúc mừng!!!");
            alert.setContentText("Đã chỉnh sửa thành công");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });

            App.nhanvien.setValue(nhanVien);
            resetValues();
        }
    }

    public void editButtonClicked(ActionEvent actionEvent) {
        tenTaiKhoan.setEditable(true);
        hoTen.setEditable(true);
        ngaySinh.setEditable(true);
        soDienThoai.setEditable(true);
        diaChi.setEditable(true);
        queQuan.setEditable(true);
        matKhau.setEditable(true);
        maleButton.setDisable(false);
        femaleButton.setDisable(false);

        cancelButton.setDisable(false);
        saveButton.setDisable(false);
    }
}
