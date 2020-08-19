package ql.khachsan.controllers;

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
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ql.khachsan.App;
import ql.khachsan.DAO.PhongDAO;
import ql.khachsan.models.Phong;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public FlowPane roomCardView;
    public ScrollPane scrollPane;
    public AnchorPane container;
    public HBox quanLy;
    public AnchorPane navBar;
    public ComboBox<String> filterCombobox;
    public Label filterLabel;
    public TextField searchField;
    public Button refreshBtn;
    public Button searchPhongBtn;

    List<Phong> list = null;

    public void homeWindow() throws IOException {
        FXMLLoader loader = App.getFXMLLoader("home");
        Parent root = loader.load();
        Scene scene = new Scene(root);
        App.homeStage.setTitle("Trang chủ");
        App.homeStage.setScene(scene);
        App.homeStage.setResizable(false);
        App.homeStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list = PhongDAO.getAllPhong();
        container.getChildren().removeAll(scrollPane, quanLy);
        int loaiNhanVien = App.nhanVien.getValue().getLoaiNhanVien().getIdLoaiNhanVien();
        if (loaiNhanVien == 1) {
            displayAllRoomCard();
            container.getChildren().add(scrollPane);
            searchField.setVisible(true);
            searchPhongBtn.setVisible(true);
            refreshBtn.setVisible(true);
            filterCombobox.setVisible(true);
            filterLabel.setVisible(true);
            filterCombobox.getItems().addAll("Trống", "Có khách", "Không dùng", "Tất cả");

            filterCombobox.showingProperty().addListener((obs, wasShowing, isShowing) -> {
                if (!isShowing) {
                    String item = (String) filterCombobox.getSelectionModel().getSelectedItem();
                    if (item != null) {
                        if (item.equals("Trống")) {
                            displayRoomCardDependingOnStatus(1);
                            System.out.println("ccmm");
                        }
                        if (item.equals("Có khách")) {
                            displayRoomCardDependingOnStatus(2);
                        }
                        if (item.equals("Không dùng")) {
                            displayRoomCardDependingOnStatus(3);
                        }
                        if (item.equals("Tất cả")) {
                            displayAllRoomCard();
                        }
                    }
                }
            });

        }
        if (loaiNhanVien == 2 || loaiNhanVien == 3) {
            container.getChildren().add(quanLy);
            displayStatisticCard();
            displayQuanLyNhanVienCard();
            if (loaiNhanVien == 2) {
                displayQuanLyPhongCard();
                displayQuanLyLoaiPhongCard();
            }
            if (loaiNhanVien == 3) {
                displayThayDoiQuyDinhCard();
            }
        }
    }

    public void displayStatisticCard() {
        AnchorPane anchorPane = new AnchorPane();

        Image img = new Image(getClass().getResourceAsStream("/assets/img/chart.png"), 133, 151, true, true);
        ImageView imgView = new ImageView();
        imgView.setImage(img);
        imgView.setLayoutX(8);
        AnchorPane.setTopAnchor(imgView, 10.0);

        Label quanLyNhanVien = new Label("Báo cáo thống kê");
        quanLyNhanVien.setPrefHeight(40);
        quanLyNhanVien.setPrefWidth(200);
        quanLyNhanVien.setLayoutX(7);
        quanLyNhanVien.setLayoutY(117);
        quanLyNhanVien.setAlignment(Pos.CENTER);
        quanLyNhanVien.setTextFill(Color.web("#fc0000"));
        quanLyNhanVien.setFont(new Font("System Bold", 15));
        AnchorPane.setLeftAnchor(quanLyNhanVien, 4.0);
        AnchorPane.setRightAnchor(quanLyNhanVien, 4.0);

        anchorPane.getChildren().addAll(imgView, quanLyNhanVien);
        Button btn = new Button();
        btn.setId("setting");
        btn.setGraphic(anchorPane);
        btn.setPrefHeight(155);
        btn.setPrefWidth(160);
        btn.setOnAction(actionEvent -> {
            FXMLLoader loader = null;
            try {
                loader = App.getFXMLLoader("baoCaoThongKe");
                Parent root = loader.load();
                Scene scene = new Scene(root, 920, 620);
                Stage stage = new Stage();
                stage.setTitle("Báo cáo thống kê");
                stage.setScene(scene);
                stage.setResizable(false);

                // Wait until this stage is complete
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(App.homeStage);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        quanLy.getChildren().add(btn);
    }

    public void displayThayDoiQuyDinhCard() {
        AnchorPane anchorPane = new AnchorPane();

        Image img = new Image(getClass().getResourceAsStream("/assets/img/setting.png"), 113, 125, true, true);
        ImageView imgView = new ImageView();
        imgView.setImage(img);
        imgView.setLayoutX(15);
        AnchorPane.setTopAnchor(imgView, 10.0);

        Label quanLyNhanVien = new Label("Thay đổi quy định");
        quanLyNhanVien.setPrefHeight(40);
        quanLyNhanVien.setPrefWidth(200);
        quanLyNhanVien.setLayoutX(10);
        quanLyNhanVien.setLayoutY(117);
        quanLyNhanVien.setAlignment(Pos.CENTER);
        quanLyNhanVien.setTextFill(Color.web("#fc0000"));
        quanLyNhanVien.setFont(new Font("System Bold", 15));
        AnchorPane.setLeftAnchor(quanLyNhanVien, 4.0);
        AnchorPane.setRightAnchor(quanLyNhanVien, 4.0);

        anchorPane.getChildren().addAll(imgView, quanLyNhanVien);
        Button btn = new Button();
        btn.setId("setting");
        btn.setGraphic(anchorPane);
        btn.setPrefHeight(155);
        btn.setPrefWidth(160);
        btn.setOnAction(actionEvent -> {
            ThayDoiQuyDinhController controller = new ThayDoiQuyDinhController();
            try {
                controller.thayDoiQuyDinhWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        quanLy.getChildren().add(btn);
    }

    public void displayQuanLyNhanVienCard() {
        AnchorPane anchorPane = new AnchorPane();

        Image img = new Image(getClass().getResourceAsStream("/assets/img/staff.png"), 133, 151, true, true);
        ImageView imgView = new ImageView();
        imgView.setImage(img);
        imgView.setLayoutX(8);
        AnchorPane.setTopAnchor(imgView, 10.0);

        Label quanLyNhanVien = new Label("Quản lý nhân viên");
        quanLyNhanVien.setPrefHeight(40);
        quanLyNhanVien.setPrefWidth(200);
        quanLyNhanVien.setLayoutX(7);
        quanLyNhanVien.setLayoutY(117);
        quanLyNhanVien.setAlignment(Pos.CENTER);
        quanLyNhanVien.setTextFill(Color.web("#fc0000"));
        quanLyNhanVien.setFont(new Font("System Bold", 15));
        AnchorPane.setLeftAnchor(quanLyNhanVien, 4.0);
        AnchorPane.setRightAnchor(quanLyNhanVien, 4.0);

        anchorPane.getChildren().addAll(imgView, quanLyNhanVien);
        Button btn = new Button();
        btn.setId("qlNhanVien");
        btn.setGraphic(anchorPane);
        btn.setPrefHeight(155);
        btn.setPrefWidth(160);
        btn.setOnAction(actionEvent -> {
            FXMLLoader loader = null;
            try {
                loader = App.getFXMLLoader("qlNhanVien");
                Parent root = loader.load();
                Scene scene = new Scene(root, 1120, 620);
                Stage stage = new Stage();
                stage.setTitle("Quản lý nhân viên");
                stage.setScene(scene);
                stage.setResizable(false);

                // Wait until this stage is complete
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(App.homeStage);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        quanLy.getChildren().add(btn);
    }

    public void displayQuanLyPhongCard() {
        AnchorPane anchorPane = new AnchorPane();

        Image img = new Image(getClass().getResourceAsStream("/assets/img/room.png"), 95, 125, true, true);
        ImageView imgView = new ImageView();
        imgView.setImage(img);
        imgView.setLayoutX(19);
        AnchorPane.setTopAnchor(imgView, 10.0);

        Label quanLyNhanVien = new Label("Quản lý phòng");
        quanLyNhanVien.setPrefHeight(40);
        quanLyNhanVien.setPrefWidth(200);
        quanLyNhanVien.setLayoutX(7);
        quanLyNhanVien.setLayoutY(117);
        quanLyNhanVien.setAlignment(Pos.CENTER);
        quanLyNhanVien.setTextFill(Color.web("#fc0000"));
        quanLyNhanVien.setFont(new Font("System Bold", 15));
        AnchorPane.setLeftAnchor(quanLyNhanVien, 4.0);
        AnchorPane.setRightAnchor(quanLyNhanVien, 4.0);

        anchorPane.getChildren().addAll(imgView, quanLyNhanVien);
        Button btn = new Button();
        btn.setId("qlPhong");
        btn.setGraphic(anchorPane);
        btn.setPrefHeight(155);
        btn.setPrefWidth(160);
        btn.setOnAction(actionEvent -> {
            FXMLLoader loader = null;
            try {
                loader = App.getFXMLLoader("qlPhong");
                Parent root = loader.load();
                Scene scene = new Scene(root, 420, 520);
                Stage stage = new Stage();
                stage.setTitle("Quản lý phòng");
                stage.setScene(scene);
                stage.setResizable(false);

                // Wait until this stage is complete
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(App.homeStage);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        quanLy.getChildren().add(btn);
    }

    public void displayQuanLyLoaiPhongCard() {
        AnchorPane anchorPane = new AnchorPane();

        Image img = new Image(getClass().getResourceAsStream("/assets/img/type.jpg"), 115, 145, true, true);
        ImageView imgView = new ImageView();
        imgView.setImage(img);
        imgView.setLayoutX(12);
        AnchorPane.setTopAnchor(imgView, 10.0);

        Label quanLyNhanVien = new Label("Quản lý loại phòng");
        quanLyNhanVien.setPrefHeight(40);
        quanLyNhanVien.setPrefWidth(200);
        quanLyNhanVien.setLayoutX(7);
        quanLyNhanVien.setLayoutY(117);
        quanLyNhanVien.setAlignment(Pos.CENTER);
        quanLyNhanVien.setTextFill(Color.web("#fc0000"));
        quanLyNhanVien.setFont(new Font("System Bold", 15));
        AnchorPane.setLeftAnchor(quanLyNhanVien, 4.0);
        AnchorPane.setRightAnchor(quanLyNhanVien, 4.0);

        anchorPane.getChildren().addAll(imgView, quanLyNhanVien);
        Button btn = new Button();
        btn.setId("qlPhong");
        btn.setGraphic(anchorPane);
        btn.setPrefHeight(155);
        btn.setPrefWidth(160);
        btn.setOnAction(actionEvent -> {
            FXMLLoader loader = null;
            try {
                loader = App.getFXMLLoader("qlLoaiPhong");
                Parent root = loader.load();
                Scene scene = new Scene(root, 420, 520);
                Stage stage = new Stage();
                stage.setTitle("Quản lý loại phòng");
                stage.setScene(scene);
                stage.setResizable(false);

                // Wait until this stage is complete
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(App.homeStage);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        quanLy.getChildren().add(btn);
    }

    public void displayAllRoomCard() {
        roomCardView.getChildren().clear();
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
            imgView.setImage(img);
            imgView.setLayoutX(12);

            AnchorPane.setTopAnchor(imgView, 10.0);

            Label tenPhong = new Label("P." + phong_i.getTenPhong());
            tenPhong.setAlignment(Pos.CENTER);
            tenPhong.setPrefHeight(40);
            tenPhong.setPrefWidth(115);
            tenPhong.setLayoutX(7);
            tenPhong.setLayoutY(117);
            tenPhong.setTextFill(Color.web("#fc0000"));
            tenPhong.setFont(new Font("System Bold", 15));

            Label loaiPhong = new Label(phong_i.getLoaiPhong().getTenLoaiPhong() + " (" + phong_i.getLoaiPhong().getSoNguoiToiDa() + ")");
            loaiPhong.setAlignment(Pos.CENTER);
            loaiPhong.setPrefHeight(25);
            loaiPhong.setPrefWidth(139);
            loaiPhong.setLayoutX(20);
            loaiPhong.setLayoutY(155);
            loaiPhong.setFont(new Font(11));
            AnchorPane.setLeftAnchor(loaiPhong, 4.0);
            AnchorPane.setRightAnchor(loaiPhong, 4.0);

            anchorPane.getChildren().addAll(imgView, tenPhong, loaiPhong);
            Button btn = new Button();
            btn.setId(phong_i.getIdPhong() + "");
            btn.setGraphic(anchorPane);
            btn.setPrefHeight(135);
            btn.setPrefWidth(140);
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (phong_i.getTrangThai() != 3) {
                        LapPhieuController controller = new LapPhieuController();
                        try {
                            controller.LapPhieuWindow(roomCardView, phong_i);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            FlowPane.setMargin(btn, new Insets(5));
            roomCardView.getChildren().add(btn);
        }
    }

    public void displayRoomCardDependingOnStatus(int status) {
        roomCardView.getChildren().clear();
        for (int i = 0; i < list.size(); i++) {
            Phong phong_i = list.get(i);
            if (phong_i.getTrangThai() != status)
                continue;

            AnchorPane anchorPane = new AnchorPane();

            Image img = null;
            if (phong_i.getTrangThai() == 1)
                img = new Image(getClass().getResourceAsStream("/assets/img/open.png"), 103, 121, true, true);
            else if (phong_i.getTrangThai() == 2)
                img = new Image(getClass().getResourceAsStream("/assets/img/closed.png"), 103, 121, true, true);
            else img = new Image(getClass().getResourceAsStream("/assets/img/repairing.png"), 103, 121, true, true);

            ImageView imgView = new ImageView();
            imgView.setImage(img);
            imgView.setLayoutX(12);

            AnchorPane.setTopAnchor(imgView, 10.0);

            Label tenPhong = new Label("P." + phong_i.getTenPhong());
            tenPhong.setAlignment(Pos.CENTER);
            tenPhong.setPrefHeight(40);
            tenPhong.setPrefWidth(115);
            tenPhong.setLayoutX(7);
            tenPhong.setLayoutY(117);
            tenPhong.setTextFill(Color.web("#fc0000"));
            tenPhong.setFont(new Font("System Bold", 15));

            Label loaiPhong = new Label(phong_i.getLoaiPhong().getTenLoaiPhong() + " (" + phong_i.getLoaiPhong().getSoNguoiToiDa() + ")");
            loaiPhong.setAlignment(Pos.CENTER);
            loaiPhong.setPrefHeight(25);
            loaiPhong.setPrefWidth(139);
            loaiPhong.setLayoutX(20);
            loaiPhong.setLayoutY(155);
            loaiPhong.setFont(new Font(11));
            AnchorPane.setLeftAnchor(loaiPhong, 4.0);
            AnchorPane.setRightAnchor(loaiPhong, 4.0);

            anchorPane.getChildren().addAll(imgView, tenPhong, loaiPhong);
            Button btn = new Button();
            btn.setId(phong_i.getIdPhong() + "");
            btn.setGraphic(anchorPane);
            btn.setPrefHeight(135);
            btn.setPrefWidth(140);
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (phong_i.getTrangThai() != 3) {
                        LapPhieuController controller = new LapPhieuController();
                        try {
                            controller.LapPhieuWindow(roomCardView, phong_i);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            FlowPane.setMargin(btn, new Insets(5));
            roomCardView.getChildren().add(btn);
        }
    }

    public void displayRoomCardDependingOnRoomName(String name) {
        roomCardView.getChildren().clear();
        for (int i = 0; i < list.size(); i++) {
            Phong phong_i = list.get(i);
            if (!phong_i.getTenPhong().toLowerCase().contains(name.toLowerCase()))
                continue;

            AnchorPane anchorPane = new AnchorPane();

            Image img = null;
            if (phong_i.getTrangThai() == 1)
                img = new Image(getClass().getResourceAsStream("/assets/img/open.png"), 103, 121, true, true);
            else if (phong_i.getTrangThai() == 2)
                img = new Image(getClass().getResourceAsStream("/assets/img/closed.png"), 103, 121, true, true);
            else img = new Image(getClass().getResourceAsStream("/assets/img/repairing.png"), 103, 121, true, true);

            ImageView imgView = new ImageView();
            imgView.setImage(img);
            imgView.setLayoutX(12);

            AnchorPane.setTopAnchor(imgView, 10.0);

            Label tenPhong = new Label("P." + phong_i.getTenPhong());
            tenPhong.setAlignment(Pos.CENTER);
            tenPhong.setPrefHeight(40);
            tenPhong.setPrefWidth(115);
            tenPhong.setLayoutX(7);
            tenPhong.setLayoutY(117);
            tenPhong.setTextFill(Color.web("#fc0000"));
            tenPhong.setFont(new Font("System Bold", 15));

            Label loaiPhong = new Label(phong_i.getLoaiPhong().getTenLoaiPhong() + " (" + phong_i.getLoaiPhong().getSoNguoiToiDa() + ")");
            loaiPhong.setAlignment(Pos.CENTER);
            loaiPhong.setPrefHeight(25);
            loaiPhong.setPrefWidth(139);
            loaiPhong.setLayoutX(20);
            loaiPhong.setLayoutY(155);
            loaiPhong.setFont(new Font(11));
            AnchorPane.setLeftAnchor(loaiPhong, 4.0);
            AnchorPane.setRightAnchor(loaiPhong, 4.0);

            anchorPane.getChildren().addAll(imgView, tenPhong, loaiPhong);
            Button btn = new Button();
            btn.setId(phong_i.getIdPhong() + "");
            btn.setGraphic(anchorPane);
            btn.setPrefHeight(135);
            btn.setPrefWidth(140);
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (phong_i.getTrangThai() != 3) {
                        LapPhieuController controller = new LapPhieuController();
                        try {
                            controller.LapPhieuWindow(roomCardView, phong_i);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            FlowPane.setMargin(btn, new Insets(5));
            roomCardView.getChildren().add(btn);
        }
    }


    public void searchBtn_Clicked(ActionEvent actionEvent) {
        if (searchField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Vui lòng nhập nội dung cần tìm vào ô tìm kiếm");
            alert.showAndWait();
            return;
        }
        displayRoomCardDependingOnRoomName(searchField.getText());
    }

    public void refreshBtn_Clicked(ActionEvent actionEvent) {
        displayAllRoomCard();
    }
}