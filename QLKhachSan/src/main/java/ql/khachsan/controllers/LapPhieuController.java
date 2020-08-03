package ql.khachsan.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import ql.khachsan.App;
import ql.khachsan.models.KhachHang;
import ql.khachsan.models.Phong;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class LapPhieuController implements Initializable {
    public TextField loaiPhong;
    public TextField nhanVien;
    public DatePicker ngayThue;
    public DatePicker ngayTra;
    public TextField tenKhach;
    public TextField cmnd;
    public TextField diaChi;
    public TextField soDienThoai;
    public Button themPhieu;
    public TextField tenPhong;
    public Button lapHoaDon;
    public Button luuThayDoi;
    public Button searchKhachHang;
    private Phong phong = new Phong();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ngayThue.setConverter(new StringConverter<LocalDate>() {
            String pattern = "dd/MM/yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                return date != null ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    LocalDate date;
                    try {
                        date = LocalDate.parse(string, dateFormatter);
                    } catch (DateTimeParseException e) {
                        date = null;
                    }
                    return date;
                } else {
                    return null;
                }
            }
        });
        ngayThue.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    ngayThue.setValue(ngayThue.getConverter().fromString(ngayThue.getEditor().getText()));
                }
            }
        });
        soDienThoai.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d*")) {
                    t1 = t1.replaceAll("[^\\d]", "");
                    soDienThoai.setText(t1);
                }
            }
        });
        cmnd.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d*")) {
                    t1 = t1.replaceAll("[^\\d]", "");
                    cmnd.setText(t1);
                }
            }
        });
    }

    private void setWindow(Phong phong) {
        this.tenPhong.setText(phong.getTenPhong());
        this.loaiPhong.setText(phong.getLoaiPhong().getTenLoaiPhong());
        this.ngayThue.setValue(LocalDate.now());
        this.nhanVien.setText(App.nhanvien.getValue().getHoTen());

        if (phong.getTrangThai() == 1) {
            themPhieu.setDisable(false);
            luuThayDoi.setDisable(true);
            lapHoaDon.setDisable(true);
        }
        else {
            themPhieu.setDisable(true);
            luuThayDoi.setDisable(false);
            lapHoaDon.setDisable(false);
        }
    }

    private boolean existEmptyField() {
        StringBuilder warningContent = new StringBuilder("");
        if (this.ngayThue.getValue() == null)
            warningContent.append("Ngày thuê không được để trống\n");
        if (isEmptyString(this.tenKhach.getText()))
            warningContent.append("Tên khách không được để trống\n");
        if (isEmptyString(this.diaChi.getText()))
            warningContent.append("Địa chỉ không được để trống\n");
        if (isEmptyString(this.cmnd.getText()))
            warningContent.append("CMND không được để trống\n");
        if (isEmptyString(this.soDienThoai.getText()))
            warningContent.append("SĐT không được để trống\n");

        if (!isEmptyString(warningContent.toString())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Thông báo");
            alert.setContentText(warningContent.toString());
            alert.showAndWait();
            return true;
        }
        return false;
    }

    private boolean isEmptyString(String str) {
        return str.trim().isEmpty();
    }

    public void LapPhieuWindow(Phong phong) throws IOException {
        this.phong = phong;
        FXMLLoader loader = App.getFXMLLoader("lapPhieu");
        Parent root = loader.load();

        LapPhieuController controller = loader.getController();
        controller.setWindow(phong);
        Stage stage = new Stage();
        stage.setTitle("Lập phiếu thuê phòng");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.homeStage);
        stage.show();
    }

    public void themPhieuBtn_Clicked(ActionEvent actionEvent) {

        KhachHang khachHang = new KhachHang(tenKhach.getText(),cmnd.getText(),soDienThoai.getText(),diaChi.getText(),null);

        int idPhong = this.phong.getIdPhong();
        int idNhanVien = App.nhanvien.getValue().getIdNhanVien();



    }

    public void searchBtn_Clicked(ActionEvent actionEvent) {
        if(isEmptyString(cmnd.getText())){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Vui lòng nhập CMND");
            alert.showAndWait();
            return;
        }

        
    }
}
