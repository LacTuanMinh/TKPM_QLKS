package ql.khachsan.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import ql.khachsan.models.DAO.LoaiPhongDAO;
import ql.khachsan.models.entities.LoaiPhong;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class QlLoaiPhongController implements Initializable {
    public TextField tenLoaiPhong;
    public TextField soNguoiToiDa;
    public TextField gia;
    public Button addButton;
    public Button deleteButton;
    public Button updateButton;
    public Button cancelButton;
    public TableView<LoaiPhong> loaiPhongTableView;

    private ObservableList<LoaiPhong> dsLoaiPhongData;
    private List<LoaiPhong> dsLoaiPhong;

    private LoaiPhong loaiPhongDangXem = null;

    private void seeDetailButtonClicked(LoaiPhong loaiPhong) {
        loaiPhongDangXem = loaiPhong;

        tenLoaiPhong.setText(loaiPhong.getTenLoaiPhong());
        soNguoiToiDa.setText(Integer.toString(loaiPhong.getSoNguoiToiDa()));
        gia.setText(String.format("%.0f", loaiPhong.getGia()));
    }

    private void createTableView() {
        dsLoaiPhongData = FXCollections.observableArrayList(dsLoaiPhong);

        // Tạo lần lượt các cột cho bảng
        TableColumn<LoaiPhong, String> tenLoaiPhongCol = new TableColumn<>("Tên loại phòng");
        TableColumn<LoaiPhong, Integer> soNguoiToiDaCol = new TableColumn<>("Số người tối đa");
        TableColumn<LoaiPhong, Float> giaCol = new TableColumn<>("Giá");
        TableColumn<LoaiPhong, LoaiPhong> seeDetailCol = new TableColumn<>("Xem");

        tenLoaiPhongCol.setMinWidth(150);
        soNguoiToiDaCol.setMinWidth(100);
        giaCol.setMinWidth(100);
        seeDetailCol.setMinWidth(50);

        soNguoiToiDaCol.setStyle("-fx-alignment: CENTER;");
        giaCol.setStyle("-fx-alignment: CENTER;");
        seeDetailCol.setStyle("-fx-alignment: CENTER;");

        tenLoaiPhongCol.setCellValueFactory(new PropertyValueFactory<>("tenLoaiPhong"));
        soNguoiToiDaCol.setCellValueFactory(new PropertyValueFactory<>("soNguoiToiDa"));

        giaCol.setCellValueFactory(new PropertyValueFactory<>("gia"));
        giaCol.setCellFactory(param -> new TableCell<LoaiPhong, Float>() {
            @Override
            protected void updateItem(Float gia, boolean empty) {
                super.updateItem(gia, empty);
                if (gia == null) {
                    setGraphic(null);
                    return;
                }
                setText(String.format("%.0f", gia));
            }
        });

        seeDetailCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        seeDetailCol.setCellFactory(param -> new TableCell<LoaiPhong, LoaiPhong>() {
            private final Button button = new Button("Xem");
            @Override
            protected void updateItem(LoaiPhong loaiPhong, boolean empty) {
                super.updateItem(loaiPhong, empty);
                if (loaiPhong == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> {
                    seeDetailButtonClicked(loaiPhong);

                    addButton.setDisable(true);
                    updateButton.setDisable(false);
                    deleteButton.setDisable(false);
                });
            }
        });

        loaiPhongTableView.getItems().addAll(dsLoaiPhongData);

        loaiPhongTableView.getColumns().addAll(tenLoaiPhongCol, soNguoiToiDaCol, giaCol, seeDetailCol);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dsLoaiPhong = LoaiPhongDAO.getAllLoaiPhong();

        createTableView();
    }

    private void updateView() {
        loaiPhongTableView.getItems().clear();
        dsLoaiPhong = LoaiPhongDAO.getAllLoaiPhong();
        dsLoaiPhongData = FXCollections.observableArrayList(dsLoaiPhong);
        loaiPhongTableView.setItems(dsLoaiPhongData);
        loaiPhongTableView.refresh();
    }

    private void resetValues() {
        tenLoaiPhong.setText("");
        soNguoiToiDa.setText("");
        gia.setText("");

        addButton.setDisable(false);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);

        loaiPhongDangXem = null;
    }

    private int checkInput() {
        // Ô nhập liệu rỗng
        if (tenLoaiPhong.getText().equals("") || soNguoiToiDa.getText().equals("") ||
                gia.getText().equals("")) {
            return -1;
        }
        else if (!soNguoiToiDa.getText().matches("[0-9]*")) {
            return -2;
        }
        else if (!gia.getText().matches("[0-9]*")) {
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
            alert.setTitle("Ô nhập liệu số người tối đa không hợp hệ");
            alert.setContentText("Số người tối đa phải là 1 con số");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
        else if (check == -3) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ô nhập liệu giá loại phòng không hợp hệ");
            alert.setContentText("Giá loại phòng phải là 1 con số");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

    public void addButtonClicked(ActionEvent actionEvent) {
        int check = checkInput();
        if (check != 0) {
            showAlert(check);
        }
        else {
            LoaiPhong loaiPhong = new LoaiPhong();

            loaiPhong.setTenLoaiPhong(tenLoaiPhong.getText());
            loaiPhong.setSoNguoiToiDa(Integer.parseInt(soNguoiToiDa.getText()));
            loaiPhong.setGia(Float.parseFloat(gia.getText()));

            LoaiPhongDAO.add(loaiPhong);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Chúc mừng!!!");
            alert.setContentText("Đã thêm mới thành công");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });

            updateView();
            resetValues();
        }
    }

    public void deleteButtonClicked(ActionEvent actionEvent) {
        if (loaiPhongDangXem != null) {
            LoaiPhongDAO.delete(loaiPhongDangXem);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Chúc mừng!!!");
            alert.setContentText("Đã xóa thành công");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });

            updateView();
            resetValues();
        }
    }

    public void updateButtonClicked(ActionEvent actionEvent) {
        int check = checkInput();
        if (check != 0) {
            showAlert(check);
        }
        else {
            loaiPhongDangXem.setTenLoaiPhong(tenLoaiPhong.getText());
            loaiPhongDangXem.setSoNguoiToiDa(Integer.parseInt(soNguoiToiDa.getText()));
            loaiPhongDangXem.setGia(Float.parseFloat(gia.getText()));

            LoaiPhongDAO.update(loaiPhongDangXem);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Chúc mừng!!!");
            alert.setContentText("Đã chỉnh sửa thành công");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });

            updateView();
            resetValues();
        }
    }

    public void cancelButtonClicked(ActionEvent actionEvent) {
        resetValues();
    }
}
