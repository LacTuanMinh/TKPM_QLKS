package ql.khachsan.controllers;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import ql.khachsan.App;
import ql.khachsan.DAO.HoaDonThanhToanDAO;
import ql.khachsan.DAO.PhongDAO;
import ql.khachsan.models.*;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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

        ((Stage)luuHoaDon.getScene().getWindow()).close();
        ((Stage)((Stage)luuHoaDon.getScene().getWindow()).getOwner()).close();

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
        int soNgay = (int)(1 + ChronoUnit.DAYS.between(phieu.getNgayThue().toInstant(),
                phieu.getNgayTra().toInstant()));

        this.tongTien.setText(LapPhieuController.tongTien(soNgay, p.getLoaiPhong().getGia()) + "");
        this.tenPhong.setText(p.getTenPhong());
        this.loaiPhong.setText(p.getLoaiPhong().getTenLoaiPhong());
        this.soNgay.setText(soNgay + "");
        this.kyTenKhach.setText(kh.getTenKhachHang());
        this.kyTenNhanvien.setText(nv.getHoTen());
    }

    private void printPDF(File file) throws FileNotFoundException {
        if (file != null) {
            int soNgay = (int)(1 + ChronoUnit.DAYS.between(phieuDatPhong.getNgayThue().toInstant(),
                    phieuDatPhong.getNgayTra().toInstant()));
            float tongTien = LapPhieuController.tongTien(soNgay,
                    phieuDatPhong.getPhong().getLoaiPhong().getGia());

            String str = file.getAbsolutePath();
            PdfWriter writer = new PdfWriter(str);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            DateFormat format = new SimpleDateFormat("dd/MM/yy");
            //String path = getClass().getResource("/fonts/arial.ttf").getPath();
            //PdfFont font = PdfFontFactory.createFont(path, "UTF-8");
            Paragraph title = new Paragraph("Hóa đơn thanh toán");
            //title.setFont(font);
            title.setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);

            document.add(title);

            Paragraph p = new Paragraph("Date created: " + format.format(new Date()));
            p.setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);
            document.add(p);

            Table tb1 = new Table(2);
            tb1.setWidth(500);
            tb1.addCell("Mã số phiếu: " + phieuDatPhong.getIdPhieuDatPhong());
            tb1.addCell("Tổng: " + String.format("%.0f", tongTien));
            tb1.addCell("Người thanh toán: " + phieuDatPhong.getKhachHang().getTenKhachHang());
            tb1.addCell("Nhân viên lập hóa đơn: " + phieuDatPhong.getNhanVien().getHoTen());
            tb1.setAutoLayout();
            tb1.setHorizontalAlignment(HorizontalAlignment.CENTER);
            tb1.setBorder(Border.NO_BORDER);
            for (IElement iElement : tb1.getChildren()) {
                ((com.itextpdf.layout.element.Cell)iElement).setBorder(Border.NO_BORDER);
            }
            document.add(tb1);

            Table tb2 = new Table(6);
            tb2.addCell("Phòng");
            tb2.addCell("Loại phòng");
            tb2.addCell("Ngày đến");
            tb2.addCell("Ngày đi");
            tb2.addCell("Số ngày ở");
            tb2.addCell("Thành tiền");
            tb2.addCell(phieuDatPhong.getPhong().getTenPhong());
            tb2.addCell(phieuDatPhong.getPhong().getLoaiPhong().getTenLoaiPhong());
            tb2.addCell(format.format(phieuDatPhong.getNgayThue()));
            tb2.addCell(format.format(phieuDatPhong.getNgayTra()));
            tb2.addCell(Integer.toString(soNgay));
            tb2.addCell(String.format("%.0f", tongTien));
            tb1.setAutoLayout();
            document.add(tb2);

            document.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message Here...");
            alert.setHeaderText("You have created a PDF file");
            alert.setContentText("File location: " + file.getAbsolutePath());
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });

            // open PDF file just got created
            try {
                Desktop.getDesktop().open(file.getAbsoluteFile());
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Awesome PDF just got created.");
        }
    }

    public void xuatHoaDonButtonClicked(ActionEvent actionEvent) throws FileNotFoundException {
        Button button = (Button)actionEvent.getTarget();
        Scene scene = button.getScene();
        Window window = scene.getWindow();
        FileChooser fc = new FileChooser();

        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf", "*.PDF"));
        File file = fc.showSaveDialog(window);
        printPDF(file);
    }
}
