package ql.khachsan.controllers;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

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
    public Label ngay;
    public Label nam;
    public Label thang;

    private FlowPane card;

    private Phong phong;

    private PhieuDatPhong phieuDatPhong;

    private DecimalFormat formatter = new DecimalFormat("#,###");

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public void luuHoaDonBtn_Clicked(ActionEvent actionEvent) throws IOException, DocumentException {
        AtomicBoolean wanToDo = new AtomicBoolean(false);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Xác nhận");
        alert.setContentText("Bạn chắc chắn muốn xuất hóa đơn");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                wanToDo.set(true);
            } else wanToDo.set(false);
        });

        if (!wanToDo.get())
            return;

        // thêm hóa đơn
        HoaDonThanhToan hoaDon = new HoaDonThanhToan(phieuDatPhong, App.nhanVien.getValue(), null);
        HoaDonThanhToanDAO.add(hoaDon);
        // cập nhật tình trạng phòng
        phong.setTrangThai(1);
        hoaDon.getPhieuDatPhong().getPhong().setTrangThai(1);
        PhongDAO.update(hoaDon.getPhieuDatPhong().getPhong());
        // xuất pdf
        xuatHoaDonButtonClicked(actionEvent);

        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setHeaderText("Đã xuất hóa đơn");
        alert2.setContentText(hoaDon.getIdHoaDon() + "");
        alert2.showAndWait();

        // dóng 2 cửa sổ : phiếu & hóa đơn
        ((Stage) luuHoaDon.getScene().getWindow()).close();
        ((Stage) ((Stage) luuHoaDon.getScene().getWindow()).getOwner()).close();

        Button btn = (Button) this.card.lookup("#" + phieuDatPhong.getPhong().getIdPhong() + "");

        //reset graphic of home view
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
        tenPhong.setAlignment(Pos.CENTER);
        tenPhong.setTextFill(Color.web("#fc0000"));
        tenPhong.setFont(new Font("System Bold", 15));
        AnchorPane.setLeftAnchor(tenPhong, 4.0);
        AnchorPane.setRightAnchor(tenPhong, 4.0);

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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void LapHoaDonWindow(FlowPane cardView, PhieuDatPhong phieuDatPhong, Phong phong) throws IOException {
        FXMLLoader loader = App.getFXMLLoader("hoaDon");
        Parent root = loader.load();
        HoaDonController controller = loader.getController();
        controller.setWindow(cardView, phieuDatPhong, phong);
        Stage stage = new Stage();
        stage.setTitle("Lập hóa đơn");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(LapPhieuController.stage);
        stage.show();
    }

    public void setWindow(FlowPane cardView, PhieuDatPhong phieu, Phong phong) {
        this.card = cardView;
        this.phieuDatPhong = phieu;
        this.phong = phong;

        KhachHang kh = phieu.getKhachHang();
        NhanVien nv = phieu.getNhanVien();
        Phong p = phieu.getPhong();

        this.tenKhach.setText(kh.getTenKhachHang());
        this.cmnd.setText(kh.getCmnd());
        this.soDienThoai.setText(kh.getSoDienThoai());
        this.giaPhong.setText(formatter.format(p.getLoaiPhong().getGia()));
        this.ngayThue.setText(format.format(phieu.getNgayThue()));
        this.ngayTra.setText(format.format(phieu.getNgayTra()));
        int soNgay = (int) (1 + ChronoUnit.DAYS.between(phieu.getNgayThue().toInstant(),
                phieu.getNgayTra().toInstant()));

        this.tongTien.setText(formatter.format(phieu.getTongTien()));
        this.tenPhong.setText(p.getTenPhong());
        this.loaiPhong.setText(p.getLoaiPhong().getTenLoaiPhong());
        this.soNgay.setText(soNgay + "");
        this.kyTenKhach.setText(kh.getTenKhachHang());
        this.kyTenNhanvien.setText(nv.getHoTen());

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        this.ngay.setText(String.format("%2d", day));
        this.thang.setText(String.format("%2d", month + 1));
        this.nam.setText(String.format("%2d", year));
    }

    private void printPDF(File file) throws IOException, DocumentException {
        if (file != null) {
            int soNgay = (int) (1 + ChronoUnit.DAYS.between(phieuDatPhong.getNgayThue().toInstant(),
                    phieuDatPhong.getNgayTra().toInstant()));
            float tongTien = phieuDatPhong.getTongTien();

            String str = file.getAbsolutePath();
            com.itextpdf.text.Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(str));

            document.open();

            String path = getClass().getResource("/fonts/times.ttf").getPath();
            BaseFont bf = BaseFont.createFont(path, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            com.itextpdf.text.Font bigFont = new com.itextpdf.text.Font(bf, 20);
            com.itextpdf.text.Font smallFont = new com.itextpdf.text.Font(bf, 14);

            Paragraph title = new Paragraph("Hóa đơn thanh toán", bigFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph p = new Paragraph("Ngày lập hóa đơn: " +
                    format.format(new Date()) + "\n", smallFont);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);

            PdfPTable tb1 = new PdfPTable(2);
            tb1.setTotalWidth(800);

            PdfPCell cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);

            cell.setPhrase(new Phrase("Tên khách hàng: " +
                    phieuDatPhong.getKhachHang().getTenKhachHang(), smallFont));
            tb1.addCell(cell);

            cell.setPhrase(new Phrase("Ngày thuê: " +
                    format.format(phieuDatPhong.getNgayThue()), smallFont));
            tb1.addCell(cell);

            cell.setPhrase(new Phrase("CMND: " +
                    phieuDatPhong.getKhachHang().getCmnd(), smallFont));
            tb1.addCell(cell);

            cell.setPhrase(new Phrase("Ngày trả: " +
                    format.format(phieuDatPhong.getNgayTra()), smallFont));
            tb1.addCell(cell);

            cell.setPhrase(new Phrase("Số điện thoại: " +
                    phieuDatPhong.getKhachHang().getSoDienThoai(), smallFont));
            tb1.addCell(cell);

            cell.setPhrase(new Phrase("Số ngày thuê: " +
                    soNgay + " ngày", smallFont));
            tb1.addCell(cell);

            cell.setPhrase(new Phrase("Tên phòng: " +
                    phieuDatPhong.getPhong().getTenPhong(), smallFont));
            tb1.addCell(cell);

            cell.setPhrase(new Phrase("Giá phòng: " + formatter.format(phieuDatPhong.getPhong()
                    .getLoaiPhong().getGia()) + " VNĐ/ngày", smallFont));
            tb1.addCell(cell);

            cell.setPhrase(new Phrase("Loại phòng: " +
                    phieuDatPhong.getPhong().getLoaiPhong().getTenLoaiPhong(), smallFont));
            tb1.addCell(cell);

            cell.setPhrase(new Phrase("Tổng tiền: " +
                    formatter.format(tongTien) + " VNĐ", smallFont));
            tb1.addCell(cell);

            tb1.setHorizontalAlignment(Element.ALIGN_CENTER);

            document.add(tb1);

            PdfPTable tb2 = new PdfPTable(2);
            tb2.setTotalWidth(500);

            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPhrase(new Phrase("\nKhách", smallFont));
            tb2.addCell(cell);

            cell.setPhrase(new Phrase("\nThu ngân", smallFont));
            tb2.addCell(cell);

            cell.setPhrase(new Phrase("(Ký tên)\n\n\n\n\n", smallFont));
            tb2.addCell(cell);
            tb2.addCell(cell);

            cell.setPhrase(new Phrase(phieuDatPhong
                    .getKhachHang().getTenKhachHang(), smallFont));
            tb2.addCell(cell);

            cell.setPhrase(new Phrase(phieuDatPhong.getNhanVien().getHoTen(), smallFont));
            tb2.addCell(cell);

            tb2.setHorizontalAlignment(Element.ALIGN_CENTER);

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

    public void xuatHoaDonButtonClicked(ActionEvent actionEvent) throws IOException, DocumentException {
        Button button = (Button) actionEvent.getTarget();
        Scene scene = button.getScene();
        Window window = scene.getWindow();
        FileChooser fc = new FileChooser();

        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf", "*.PDF"));
        File file = fc.showSaveDialog(window);
        printPDF(file);
    }
}
