package ql.khachsan.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ql.khachsan.App;
import ql.khachsan.DAO.HoaDonThanhToanDAO;
import ql.khachsan.DAO.PhongDAO;
import ql.khachsan.models.*;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class HoaDonController implements Initializable {
    public Label kyTenKhach;
    public Label kyTenNhanvien;
    public Label tenKhach;
    public Label tenPhong;
    public Label soDienThoai;
    public Label cmnd;
    public Label loaiPhong;
    public Label ngayThue;
    public Label tongTien;
    public Label giaPhong;
    public Label soNgay;
    public Label ngayTra;
    public Button luuHoaDon;
    public AnchorPane parent;

    private PhieuDatPhong phieuDatPhong;

    public void luuHoaDonBtn_Clicked(ActionEvent actionEvent) {

        HoaDonThanhToan hoaDon = new HoaDonThanhToan(phieuDatPhong, App.nhanvien.getValue(), null);
        HoaDonThanhToanDAO.add(hoaDon);

        hoaDon.getPhieuDatPhong().getPhong().setTrangThai(1);
        PhongDAO.update(hoaDon.getPhieuDatPhong().getPhong());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Đã xuất hóa đơn");
        alert.setContentText(hoaDon.getIdHoaDon() + "");
        alert.showAndWait();

        ((Stage) luuHoaDon.getScene().getWindow()).close();
        ((Stage) ((Stage) luuHoaDon.getScene().getWindow()).getOwner()).close();

        Button btn = (Button) this.card.lookup("#" + phieuDatPhong.getPhong().getIdPhong() + "");

        AnchorPane anchorPane = new AnchorPane();
        Image img = new Image(getClass().getResourceAsStream("/assets/img/open.png"), 103, 121, true, true);
        ImageView imgView = new ImageView();
        imgView.setImage(img);
        imgView.setLayoutX(12);
        AnchorPane.setTopAnchor(imgView, 10.0);

        Label tenPhong = new Label("P." + phieuDatPhong.getPhong().getTenPhong());
        tenPhong.setPrefHeight(40);
        tenPhong.setPrefWidth(115);
        tenPhong.setLayoutX(7);
        tenPhong.setLayoutY(117);
        tenPhong.setTextAlignment(TextAlignment.CENTER);
        tenPhong.setTextFill(Color.web("#fc0000"));
        tenPhong.setFont(new Font("System Bold", 15));
        AnchorPane.setLeftAnchor(tenPhong, 4.0);
        AnchorPane.setRightAnchor(tenPhong, 55.0);

        Label loaiPhong = new Label(phieuDatPhong.getPhong().getLoaiPhong().getTenLoaiPhong());//Label("Tham gia: " + model.soNguoiDuocDuyetThamDuHoiNghi(hoiNghi_i.getId()) + "/" + hoiNghi_i.getSoNguoiMax() + " người");
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
        btn.setId(phieuDatPhong.getPhong().getIdPhong() + "");
        btn.setGraphic(anchorPane);
        btn.setPrefHeight(135);
        btn.setPrefWidth(140);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LapPhieuController controller = new LapPhieuController();
                try {
                    controller.LapPhieuWindow(card, phieuDatPhong.getPhong());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        FlowPane.setMargin(btn, new Insets(5));

//        System.out.println("To Printer!");
//        PrinterJob job = PrinterJob.createPrinterJob();
//        if (job != null) {
//            job.showPrintDialog(luuHoaDon.getScene().getWindow());
//            job.printPage(parent);
//            job.endJob();
//        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private FlowPane card;

    public void LapHoaDonWindow(FlowPane cardView, PhieuDatPhong phieuDatPhong) throws IOException {
        FXMLLoader loader = App.getFXMLLoader("hoaDon");
        Parent root = loader.load();
        HoaDonController controller = loader.getController();
        controller.setWindow(cardView, phieuDatPhong);
        Stage stage = new Stage();
        stage.setTitle("Lập hóa đơn");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(LapPhieuController.stage);
        stage.show();
    }

    public void setWindow(FlowPane cardView, PhieuDatPhong phieu) {
        this.card = cardView;
        this.phieuDatPhong = phieu;

        KhachHang kh = phieu.getKhachHang();
        NhanVien nv = phieu.getNhanVien();
        Phong p = phieu.getPhong();

        this.tenKhach.setText(kh.getTenKhachHang());
        this.cmnd.setText(kh.getCmnd());
        this.soDienThoai.setText(kh.getSoDienThoai());
        this.giaPhong.setText(p.getLoaiPhong().getGia() + "");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        this.ngayThue.setText(df.format(phieu.getNgayThue()));
        this.ngayTra.setText(df.format(phieu.getNgayTra()));
        int soNgay = (int) (1 + ChronoUnit.DAYS.between(phieu.getNgayThue().toInstant(), phieu.getNgayTra().toInstant()));

        this.tongTien.setText(LapPhieuController.tongTien(soNgay, p.getLoaiPhong().getGia()) + "");
        this.tenPhong.setText(p.getTenPhong());
        this.loaiPhong.setText(p.getLoaiPhong().getTenLoaiPhong());
        this.soNgay.setText(soNgay + "");
        this.kyTenKhach.setText(kh.getTenKhachHang());
        this.kyTenNhanvien.setText(nv.getHoTen());
    }
}
