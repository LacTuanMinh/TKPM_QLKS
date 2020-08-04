package ql.khachsan.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import ql.khachsan.App;
import ql.khachsan.DAO.KhachHangDAO;
import ql.khachsan.DAO.PhieuDatPhongDAO;
import ql.khachsan.DAO.PhongDAO;
import ql.khachsan.models.KhachHang;
import ql.khachsan.models.PhieuDatPhong;
import ql.khachsan.models.Phong;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;

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
    public TextField giaPhong;
    public TextField tongTien;
    private Phong phong = new Phong();

    private KhachHang khachHang = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ngayThue.setEditable(false);
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

        ngayTra.setConverter(new StringConverter<LocalDate>() {
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
        ngayTra.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    ngayTra.setValue(ngayTra.getConverter().fromString(ngayTra.getEditor().getText()));
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

    public static float tongTien(int soNgay, float donGia) {
        int sum = 0;
        if (soNgay < 10)
            return soNgay * donGia;
        if (soNgay < 20)
            return (float) (9 * donGia + (soNgay - 9) * donGia * 0.95);
        return (float) (9 * donGia + 10 * donGia * 0.95 + (soNgay - 19) * donGia * 0.9);
    }

    private void setWindow(Phong phong) {
        this.phong = phong;
        this.tenPhong.setText(phong.getTenPhong());
        this.loaiPhong.setText(phong.getLoaiPhong().getTenLoaiPhong());
        this.nhanVien.setText(App.nhanvien.getValue().getHoTen());
        this.giaPhong.setText(phong.getLoaiPhong().getGia() + "");
        ngayTra.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                if (t1 != null) {
                    long dayBetween = DAYS.between(ngayThue.getValue(), ngayTra.getValue());
                    tongTien.setText(LapPhieuController.tongTien((int) dayBetween, phong.getLoaiPhong().getGia()) + "");
                }else tongTien.setText("");
            }
        });

        if (phong.getTrangThai() == 1) {// phòng trống
            this.ngayThue.setValue(LocalDate.now());
            themPhieu.setDisable(false);
            luuThayDoi.setDisable(true);
            lapHoaDon.setDisable(true);
        } else {// có khách

            themPhieu.setDisable(true);
            luuThayDoi.setDisable(false);
            lapHoaDon.setDisable(false);
            searchKhachHang.setDisable(true);
            PhieuDatPhong phieu = PhieuDatPhongDAO.getPhieuDatPhongByIDPhong(phong.getIdPhong());

            assert phieu != null;
            this.khachHang = phieu.getKhachHang();
            ngayThue.setValue(phieu.getNgayThue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            ngayTra.setValue(phieu.getNgayTra().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            tenKhach.setText(phieu.getKhachHang().getTenKhachHang());
            diaChi.setText(phieu.getKhachHang().getDiaChi());
            soDienThoai.setText(phieu.getKhachHang().getSoDienThoai());
            cmnd.setText(phieu.getKhachHang().getCmnd());

            tenKhach.setDisable(false);
            diaChi.setDisable(false);
            soDienThoai.setDisable(false);
            cmnd.setDisable(false);
            ngayThue.setDisable(true);
            ngayThue.setStyle("-fx-opacity: 1");
            cmnd.setEditable(true);
            tenKhach.setEditable(true);
            diaChi.setEditable(true);
            soDienThoai.setEditable(true);
        }
    }

    private boolean existEmptyField() {
        StringBuilder warningContent = new StringBuilder("");
        if (this.ngayThue.getValue() == null)
            warningContent.append("Ngày thuê không được để trống\n");
        if (this.ngayTra.getValue() == null)
            warningContent.append("Ngày trả không được để trống\n");
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

    private FlowPane cardView;

    public void LapPhieuWindow(FlowPane cardView, Phong phong) throws IOException {
        FXMLLoader loader = App.getFXMLLoader("lapPhieu");
        Parent root = loader.load();
        LapPhieuController controller = loader.getController();
        controller.setWindow(phong);
        controller.cardView = cardView;
        Stage stage = new Stage();
        stage.setTitle("Lập phiếu thuê phòng");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.homeStage);
        stage.show();
    }

    public void themPhieuBtn_Clicked(ActionEvent actionEvent) {

        if (existEmptyField())
            return;
        if (!isValid())
            return;

        if (this.khachHang == null) {
            this.khachHang = new KhachHang(tenKhach.getText(), cmnd.getText(), soDienThoai.getText(), diaChi.getText(), null);
        } else {
            this.khachHang.setCmnd(cmnd.getText());
            this.khachHang.setDiaChi(diaChi.getText());
            this.khachHang.setSoDienThoai(soDienThoai.getText());
            this.khachHang.setTenKhachHang(tenKhach.getText());
        }
        KhachHangDAO.addOrUpdateKhachHang(this.khachHang);

        phong.setTrangThai(2);
        PhongDAO.updatePhong(phong);

        PhieuDatPhong phieuDatPhong = new PhieuDatPhong(
                this.phong,
                App.nhanvien.getValue(),
                this.khachHang,
                new Date(),
                Date.from(ngayTra.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                null);
        PhieuDatPhongDAO.addPhieu(phieuDatPhong);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Thêm phiếu thành công");
        alert.setContentText(phieuDatPhong.getIdPhieuDatPhong() + "");
        alert.showAndWait();
        themPhieu.setDisable(true);
        luuThayDoi.setDisable(false);
        lapHoaDon.setDisable(false);
        searchKhachHang.setDisable(true);


        Button btn = (Button) this.cardView.lookup("#" + phong.getIdPhong() + "");

        AnchorPane anchorPane = new AnchorPane();
        Image img = new Image(getClass().getResourceAsStream("/assets/img/closed.png"), 103, 121, true, true);
        ImageView imgView = new ImageView();
        imgView.setImage(img);
        imgView.setLayoutX(12);

        AnchorPane.setTopAnchor(imgView, 10.0);

        Label tenPhong = new Label("P." + phong.getTenPhong());
        tenPhong.setPrefHeight(40);
        tenPhong.setPrefWidth(115);
        tenPhong.setLayoutX(7);
        tenPhong.setLayoutY(117);
        tenPhong.setTextAlignment(TextAlignment.CENTER);
        tenPhong.setTextFill(Color.web("#fc0000"));
        tenPhong.setFont(new Font("System Bold", 15));
        AnchorPane.setLeftAnchor(tenPhong, 4.0);
        AnchorPane.setRightAnchor(tenPhong, 55.0);


        Label loaiPhong = new Label(phong.getLoaiPhong().getTenLoaiPhong());//Label("Tham gia: " + model.soNguoiDuocDuyetThamDuHoiNghi(hoiNghi_i.getId()) + "/" + hoiNghi_i.getSoNguoiMax() + " người");
        loaiPhong.setPrefHeight(25);
        loaiPhong.setPrefWidth(139);
        loaiPhong.setLayoutX(20);
        loaiPhong.setLayoutY(155);
        loaiPhong.setAlignment(Pos.CENTER_RIGHT);
        loaiPhong.setFont(new Font(13));
        loaiPhong.setTextAlignment(TextAlignment.RIGHT);
        AnchorPane.setLeftAnchor(loaiPhong, 10.0);
        AnchorPane.setRightAnchor(loaiPhong, 5.0);

        anchorPane.getChildren().addAll(imgView, tenPhong, loaiPhong);
        btn.setId(phong.getIdPhong() + "");
        btn.setGraphic(anchorPane);
        btn.setPrefHeight(135);
        btn.setPrefWidth(140);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LapPhieuController controller = new LapPhieuController();
                try {
                    controller.LapPhieuWindow(cardView, phong);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        FlowPane.setMargin(btn, new Insets(5));
    }

    public void searchBtn_Clicked(ActionEvent actionEvent) {
        if (isEmptyString(cmnd.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Vui lòng nhập CMND");
            alert.showAndWait();
            return;
        }

        this.khachHang = KhachHangDAO.getKhachHangByCMND(cmnd.getText());
        if (khachHang == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Không tìm thấy khách hàng. Vui lòng nhập thông tin chi tiết");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Đã tìm thấy khách hàng");
            alert.showAndWait();
            tenKhach.setText(khachHang.getTenKhachHang());
            diaChi.setText(khachHang.getDiaChi());
            soDienThoai.setText(khachHang.getSoDienThoai());
        }
        tenKhach.setEditable(true);
        diaChi.setEditable(true);
        soDienThoai.setEditable(true);
        tenKhach.setDisable(false);
        diaChi.setDisable(false);
        soDienThoai.setDisable(false);
    }

    private boolean isValid() {

        StringBuilder warningContent = new StringBuilder("");
        if (ngayThue.getValue().isBefore(LocalDate.now()))
            warningContent.append("Ngày thuê không hợp lệ\n");
        if (ngayTra.getValue().isBefore(ngayThue.getValue()))
            warningContent.append("Ngày trả phải sau ngày thuê\n");
        if (!warningContent.toString().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Thông báo");
            alert.setContentText(warningContent.toString());
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void luuThayDoiBtn_Clicked(ActionEvent actionEvent) {

        if (existEmptyField())
            return;
        if (ngayTra.getValue().isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Thông báo");
            alert.setContentText("Ngày trả không được nhỏ hơn ngày hiện tại");
            alert.showAndWait();
            return;
        }
        this.khachHang.setCmnd(cmnd.getText());
        this.khachHang.setDiaChi(diaChi.getText());
        this.khachHang.setSoDienThoai(soDienThoai.getText());
        this.khachHang.setTenKhachHang(tenKhach.getText());
        KhachHangDAO.addOrUpdateKhachHang(khachHang);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Thông báo");
        alert.setContentText("Điều chỉnh thành công");
        alert.showAndWait();
    }
}
