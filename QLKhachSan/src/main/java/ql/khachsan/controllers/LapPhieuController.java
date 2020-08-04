package ql.khachsan.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import ql.khachsan.App;
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

    private Phong phong;

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

    private boolean existEmptyField(){
        return true;
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
}
