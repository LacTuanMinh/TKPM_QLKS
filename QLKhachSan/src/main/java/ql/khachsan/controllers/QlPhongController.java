package ql.khachsan.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import ql.khachsan.DAO.LoaiPhongDAO;
import ql.khachsan.DAO.PhongDAO;
import ql.khachsan.models.LoaiPhong;
import ql.khachsan.models.Phong;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class QlPhongController implements Initializable {
    public TableView<Phong> phongTableView;
    public TextField tenPhong;
    public ComboBox<LoaiPhong> loaiPhongComboBox;
    public ComboBox<String> trangThaiComboBox;
    public Button addButton;
    public Button deleteButton;
    public Button updateButton;
    public Button cancelButton;

    private ObservableList<Phong> dsPhongData;
    private List<Phong> dsPhong;

    private ObservableList<LoaiPhong> dsLoaiPhongData;
    private List<LoaiPhong> dsLoaiPhong;

    private Phong phongDangXem = null;

    private void seeDetailButtonClicked(Phong phong) {
        phongDangXem = phong;

        tenPhong.setText(phong.getTenPhong());
        loaiPhongComboBox.setValue(phong.getLoaiPhong());

        tenPhong.setDisable(false);
        loaiPhongComboBox.setDisable(false);
        trangThaiComboBox.setDisable(false);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);

        String trangThai = "";
        if (phong.getTrangThai() == 1) {
            trangThai = "Trống";
        }
        else if (phong.getTrangThai() == 2) {
            trangThai = "Có khách";
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
            tenPhong.setDisable(true);
            loaiPhongComboBox.setDisable(true);
            trangThaiComboBox.setDisable(true);

        }
        else if (phong.getTrangThai() == 3) {
            trangThai = "Không sử dụng được";
        }

        trangThaiComboBox.setValue(trangThai);
    }

    private void updateView() {
        phongTableView.getItems().clear();
        dsPhong = PhongDAO.getAllPhong();
        dsPhongData = FXCollections.observableArrayList(dsPhong);
        phongTableView.getItems().addAll(dsPhongData);
    }

    private void createTableView() {
        dsPhongData = FXCollections.observableArrayList(dsPhong);

        // Tạo lần lượt các cột cho bảng
        TableColumn<Phong, String> tenPhongCol = new TableColumn<>("Tên phòng");
        TableColumn<Phong, LoaiPhong> loaiPhongCol = new TableColumn<>("Loại phòng");
        TableColumn<Phong, Integer> trangThaiCol = new TableColumn<>("Trạng thái");
        TableColumn<Phong, Phong> seeDetailCol = new TableColumn<>("Xem");

        tenPhongCol.setMinWidth(100);
        loaiPhongCol.setMinWidth(100);
        trangThaiCol.setMinWidth(120);
        seeDetailCol.setMinWidth(50);

        loaiPhongCol.setStyle("-fx-alignment: CENTER;");
        trangThaiCol.setStyle("-fx-alignment: CENTER;");
        seeDetailCol.setStyle("-fx-alignment: CENTER;");

        tenPhongCol.setCellValueFactory(new PropertyValueFactory<>("tenPhong"));

        loaiPhongCol.setCellValueFactory(new PropertyValueFactory<>("loaiPhong"));
        loaiPhongCol.setCellFactory(param -> new TableCell<Phong, LoaiPhong>() {
            @Override
            protected void updateItem(LoaiPhong loaiPhong, boolean empty) {
                super.updateItem(loaiPhong, empty);
                if (loaiPhong == null) {
                    setGraphic(null);
                    return;
                }
                setText(loaiPhong.getTenLoaiPhong());
            }
        });

        trangThaiCol.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
        trangThaiCol.setCellFactory(param -> new TableCell<Phong, Integer>() {
            @Override
            protected void updateItem(Integer trangThai, boolean empty) {
                super.updateItem(trangThai, empty);
                if (trangThai == null) {
                    setGraphic(null);
                    return;
                }
                if (trangThai == 1) {
                    setText("Trống");
                }
                else if (trangThai == 2) {
                    setText("Có khách");
                }
                else if (trangThai == 3) {
                    setText("Không sử dụng được");
                }
            }
        });

        seeDetailCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        seeDetailCol.setCellFactory(param -> new TableCell<Phong, Phong>() {
            private final Button button = new Button("Xem");
            @Override
            protected void updateItem(Phong phong, boolean empty) {
                super.updateItem(phong, empty);
                if (phong == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> {
                    seeDetailButtonClicked(phong);

                    addButton.setDisable(true);
                });
            }
        });

        phongTableView.getItems().addAll(dsPhongData);

        phongTableView.getColumns().addAll(tenPhongCol, loaiPhongCol, trangThaiCol, seeDetailCol);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dsPhong = PhongDAO.getAllPhong();

        dsLoaiPhong = LoaiPhongDAO.getAllLoaiPhong();
        dsLoaiPhongData = FXCollections.observableArrayList(dsLoaiPhong);
        loaiPhongComboBox.getItems().addAll(dsLoaiPhongData);

        trangThaiComboBox.getItems().addAll("Trống", "Có khách", "Không sử dụng được");

        createTableView();
    }

    private void resetValues() {
        tenPhong.setText("");
        loaiPhongComboBox.setValue(null);
        trangThaiComboBox.setValue(null);

        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        addButton.setDisable(false);

        phongDangXem = null;
    }

    private int checkInput() {
        List<String> tenPhongList = PhongDAO.getAllTenPhong();
        if (phongDangXem != null) {
            tenPhongList.remove(phongDangXem.getTenPhong());
        }
        // Ô nhập liệu rỗng
        if (tenPhong.getText().equals("")) {
            return -1;
        }
        else if (loaiPhongComboBox.getValue() == null) {
            return -2;
        }
        else if (trangThaiComboBox.getValue() == null) {
            return -3;
        }
        else if (tenPhongList.contains(tenPhong.getText())) {
            return -4;
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
            alert.setTitle("Các vùng nhập liệu không hợp lệ");
            alert.setContentText("Bạn chưa chọn loại phòng cho phòng");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
        else if (check == -3) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Các vùng nhập liệu không hợp lệ");
            alert.setContentText("Bạn chưa chọn trạng thái cho phòng");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
        else if (check == -4) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tên phòng không hợp lệ");
            alert.setContentText("Đã tồn tại tên phòng này, hãy chọn tên khác");
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
            Phong phong = new Phong();

            phong.setTenPhong(tenPhong.getText());
            phong.setLoaiPhong(loaiPhongComboBox.getValue());

            int trangThai = 0;
            if (trangThaiComboBox.getValue().equals("Trống")) {
                trangThai = 1;
            }
            else if (trangThaiComboBox.getValue().equals("Có khách")) {
                trangThai = 2;
            }
            else if (trangThaiComboBox.getValue().equals("Không sử dụng được")) {
                trangThai = 3;
            }
            phong.setTrangThai(trangThai);

            PhongDAO.add(phong);

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
        if (phongDangXem != null) {
            PhongDAO.delete(phongDangXem);

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
            phongDangXem.setTenPhong(tenPhong.getText());
            phongDangXem.setLoaiPhong(loaiPhongComboBox.getValue());

            int trangThai = 0;
            if (trangThaiComboBox.getValue().equals("Trống")) {
                trangThai = 1;
            }
            else if (trangThaiComboBox.getValue().equals("Có khách")) {
                trangThai = 2;
            }
            else if (trangThaiComboBox.getValue().equals("Không sử dụng được")) {
                trangThai = 3;
            }
            phongDangXem.setTrangThai(trangThai);

            PhongDAO.update(phongDangXem);

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
