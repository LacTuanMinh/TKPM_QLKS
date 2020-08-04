package ql.khachsan.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import ql.khachsan.App;
import ql.khachsan.DAO.PhongDAO;
import ql.khachsan.models.Phong;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public FlowPane cardView;

    @FXML
    public void homeWindow() throws IOException {
        FXMLLoader loader = App.getFXMLLoader("home");
        Parent root = loader.load();
        Scene scene = new Scene(root);
        App.homeStage.setTitle("Trang chủ");
        App.homeStage.setScene(scene);
        App.homeStage.setResizable(false);
        App.homeStage.show();
    }

    List<Phong> list = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list = PhongDAO.getAllPhong();
        for (int i = 0; i < list.size(); i++) {
            Phong phong_i = list.get(i);
            AnchorPane anchorPane = new AnchorPane();

            Image img = null;
            if (phong_i.getTrangThai() == 1)
                img = new Image(getClass().getResourceAsStream("/assets/img/open.png"), 103, 121, true, true);
            else if (phong_i.getTrangThai() == 2)
                img = new Image(getClass().getResourceAsStream("/assets/img/closed.png"), 103, 121, true, true);
            else img = new Image(getClass().getResourceAsStream("/assets/img/repairing.png"), 103, 121, true, true);

            ImageView imgView = new ImageView();
//            double w = 0;
//            double h = 0;
//            double ratioX = imgView.getFitWidth() / img.getWidth();
//            double ratioY = imgView.getFitHeight() / img.getHeight();
//            double reducCoeff = 0;
//            if (ratioX >= ratioY) {
//                reducCoeff = ratioY;
//            } else {
//                reducCoeff = ratioX;
//            }
//            w = img.getWidth() * reducCoeff;
//            h = img.getHeight() * reducCoeff;
//            imgView.setX((imgView.getFitWidth() - w) / 2);
//            imgView.setY((imgView.getFitHeight() - h) / 2);
            imgView.setImage(img);
            imgView.setLayoutX(12);

            AnchorPane.setTopAnchor(imgView, 10.0);

            Label tenPhong = new Label("P." + phong_i.getTenPhong());
            tenPhong.setPrefHeight(40);
            tenPhong.setPrefWidth(115);
            tenPhong.setLayoutX(7);
            tenPhong.setLayoutY(117);
            tenPhong.setTextAlignment(TextAlignment.CENTER);
            tenPhong.setTextFill(Color.web("#fc0000"));
            tenPhong.setFont(new Font("System Bold", 15));
            AnchorPane.setLeftAnchor(tenPhong, 4.0);
            AnchorPane.setRightAnchor(tenPhong, 55.0);

            Label loaiPhong = new Label("Loại: " + phong_i.getLoaiPhong().getTenLoaiPhong());//Label("Tham gia: " + model.soNguoiDuocDuyetThamDuHoiNghi(hoiNghi_i.getId()) + "/" + hoiNghi_i.getSoNguoiMax() + " người");
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
            Button btn = new Button();
            btn.setGraphic(anchorPane);
            btn.setPrefHeight(135);
            btn.setPrefWidth(140);
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
//                    if (phong_i.getTrangThai() == 1) {
                        LapPhieuController controller = new LapPhieuController();
                        try {
                            controller.LapPhieuWindow(phong_i);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                    }
                }
            });
            FlowPane.setMargin(btn, new Insets(5));
            cardView.getChildren().add(btn);
        }
    }
}