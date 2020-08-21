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
import java.util.List;
import java.util.ResourceBundle;

public class ThongTinCaNhanController implements Initializable {
    public TextField tenTaiKhoan;
    public TextField hoTen;
    public TextField ngaySinh;
    public TextField soDienThoai;
    public RadioButton maleButton;
    public RadioButton femaleButton;
    public TextField cmnd;
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

        oldValue = App.nhanVien.get();

        ToggleGroup group = new ToggleGroup();
        maleButton.setToggleGroup(group);
        femaleButton.setToggleGroup(group);

        if (App.nhanVien.getValue().getGioiTinh().equals("Nam")) {
            maleButton.setSelected(true);
        }
        else {
            femaleButton.setSelected(true);
        }

        tenTaiKhoan.setText(App.nhanVien.getValue().getTenTaiKhoan());
        hoTen.setText(App.nhanVien.getValue().getHoTen());
        ngaySinh.setText(format.format(App.nhanVien.getValue().getNgaySinh()));
        soDienThoai.setText(App.nhanVien.getValue().getSoDienThoai());
        cmnd.setText(App.nhanVien.getValue().getCmnd());
        queQuan.setText(App.nhanVien.getValue().getQueQuan());
        diaChi.setText(App.nhanVien.getValue().getDiaChi());
        ngayBatDauDiLam.setText(format.format(App.nhanVien.getValue().getNgayBatDauDiLam()));
        luongThang.setText(String.format("%.0f", App.nhanVien.getValue().getLuongThang()));
        chucVu.setText(App.nhanVien.get().getLoaiNhanVien().getTenLoaiNhanVien());
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
        cmnd.setText(oldValue.getCmnd());
        queQuan.setText(oldValue.getQueQuan());
        diaChi.setText(oldValue.getDiaChi());
        ngayBatDauDiLam.setText(format.format(oldValue.getNgayBatDauDiLam()));
        luongThang.setText(String.format("%.0f", oldValue.getLuongThang()));
        chucVu.setText(oldValue.getLoaiNhanVien().getTenLoaiNhanVien());
        matKhau.setText("");

        tenTaiKhoan.setEditable(false);
        hoTen.setEditable(false);
        ngaySinh.setEditable(false);
        soDienThoai.setEditable(false);
        diaChi.setEditable(false);
        cmnd.setEditable(false);
        queQuan.setEditable(false);
        matKhau.setEditable(false);
        maleButton.setDisable(true);
        femaleButton.setDisable(true);

        cancelButton.setDisable(true);
        saveButton.setDisable(true);
    }

    private int checkInput() {
        List<String> tenTaiKhoanList = NhanVienDAO.getAllTenTaiKhoan();
        tenTaiKhoanList.remove(App.nhanVien.getValue().getTenTaiKhoan());
        // Ô nhập liệu rỗng
        if (tenTaiKhoan.getText().equals("") || hoTen.getText().equals("") ||
                ngaySinh.getText().equals("") || luongThang.getText().equals("") ||
                cmnd.getText().equals("") || queQuan.getText().equals("") ||
                diaChi.getText().equals("") || ngayBatDauDiLam.getText().equals("") ||
                soDienThoai.getText().equals("")) {
            return -1;
        }
        // Kiểm tra nhập mật khẩu hiện tại
        else if (!PasswordUtils.verifyHash(matKhau.getText(), App.nhanVien.getValue().getMatKhau())) {
            return -2;
        }
        // Kiểm tra trùng tên tài khoản
        else if (tenTaiKhoanList.contains(tenTaiKhoan.getText())) {
            return -3;
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
        else if (check == -3) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tên tài khoản không hợp lệ");
            alert.setContentText("Đã tồn tại tên tài khoản này, hãy chọn tên tài khoản khác");
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
            NhanVien nhanVien = App.nhanVien.getValue();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            nhanVien.setTenTaiKhoan(tenTaiKhoan.getText());
            nhanVien.setHoTen(hoTen.getText());
            nhanVien.setNgaySinh(format.parse(ngaySinh.getText()));
            nhanVien.setSoDienThoai(soDienThoai.getText());
            nhanVien.setDiaChi(diaChi.getText());
            nhanVien.setCmnd(cmnd.getText());
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

            App.nhanVien.setValue(null);
            App.nhanVien.setValue(nhanVien);
            resetValues();
        }
    }

    public void editButtonClicked(ActionEvent actionEvent) {
        tenTaiKhoan.setEditable(true);
        hoTen.setEditable(true);
        ngaySinh.setEditable(true);
        soDienThoai.setEditable(true);
        diaChi.setEditable(true);
        cmnd.setEditable(true);
        queQuan.setEditable(true);
        matKhau.setEditable(true);
        maleButton.setDisable(false);
        femaleButton.setDisable(false);

        cancelButton.setDisable(false);
        saveButton.setDisable(false);
    }
}
