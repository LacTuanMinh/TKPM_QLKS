package ql.khachsan.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ql.khachsan.DAO.LoaiNhanVienDAO;
import ql.khachsan.DAO.NhanVienDAO;
import ql.khachsan.models.LoaiNhanVien;
import ql.khachsan.models.NhanVien;
import ql.khachsan.utils.PasswordUtils;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class QlNhanVienController implements Initializable {
    public TextField hoTen;
    public TextField ngaySinh;
    public TextField soDienThoai;
    public TextField diaChi;
    public TextField luongThang;
    public TextField ngayBatDauDiLam;
    public TextField queQuan;
    public ComboBox<LoaiNhanVien> chucVuComboBox = new ComboBox<>();
    public Button cancelButton;
    public Button addButton;
    public Button updateButton;
    public Button deleteButton;
    public TableView<NhanVien> nhanVienTableView = new TableView<>();
    public RadioButton maleButton;
    public RadioButton femaleButton;

    private ObservableList<LoaiNhanVien> dsChucVuData;
    private List<LoaiNhanVien> dsChucVu;

    private ObservableList<NhanVien> dsNhanVienData;
    private List<NhanVien> dsNhanVien;

    private NhanVien nhanVienDangXem = null;

    private void seeDetailButtonClicked(NhanVien nhanVien) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        nhanVienDangXem = nhanVien;

        if (nhanVien.getGioiTinh().equals("Nam")) {
            maleButton.setSelected(true);
            femaleButton.setSelected(false);
        }
        else {
            maleButton.setSelected(false);
            femaleButton.setSelected(true);
        }

        hoTen.setText(nhanVien.getHoTen());
        ngaySinh.setText(format.format(nhanVien.getNgaySinh()));
        soDienThoai.setText(nhanVien.getSoDienThoai());
        diaChi.setText(nhanVien.getDiaChi());
        luongThang.setText(String.format("%.0f", nhanVien.getLuongThang()));
        ngayBatDauDiLam.setText(format.format(nhanVien.getNgayBatDauDiLam()));
        queQuan.setText(nhanVien.getQueQuan());
        chucVuComboBox.getSelectionModel().select(nhanVien.getLoaiNhanVien().getIdLoaiNhanVien() - 1);
    }

    private void resetValues() {
        hoTen.setText("");
        ngaySinh.setText("");
        soDienThoai.setText("");
        diaChi.setText("");
        luongThang.setText("");
        ngayBatDauDiLam.setText("");
        queQuan.setText("");

        hoTen.setDisable(false);
        ngaySinh.setDisable(false);
        soDienThoai.setDisable(false);
        diaChi.setDisable(false);
        ngayBatDauDiLam.setDisable(false);
        queQuan.setDisable(false);
        maleButton.setDisable(false);
        femaleButton.setDisable(false);

        chucVuComboBox.setValue(null);
        maleButton.setSelected(true);
        femaleButton.setSelected(false);
        addButton.setDisable(false);
        updateButton.setDisable(true);

        nhanVienDangXem = null;
    }

    private void createTableView() {
        dsNhanVienData = FXCollections.observableArrayList(dsNhanVien);

        // Tạo lần lượt các cột cho bảng
        TableColumn<NhanVien, String> hoTenCol = new TableColumn<>("Họ tên");
        TableColumn<NhanVien, Date> ngaySinhCol = new TableColumn<>("Ngày sinh");
        TableColumn<NhanVien, LoaiNhanVien> chucVuCol = new TableColumn<>("Chức vụ");
        TableColumn<NhanVien, Float> luongThangCol = new TableColumn<>("Lương tháng");
        TableColumn<NhanVien, Date> ngayBatDauDiLamCol = new TableColumn<>("Ngày bắt đầu đi làm");
        TableColumn<NhanVien, String> soDienThoaiCol = new TableColumn<>("Số điện thoại");
        TableColumn<NhanVien, String> gioiTinhCol = new TableColumn<>("Giới tính");
        TableColumn<NhanVien, String> diaChiCol = new TableColumn<>("Địa chỉ");
        TableColumn<NhanVien, String> queQuanCol = new TableColumn<>("Quê quán");
        TableColumn<NhanVien, NhanVien> seeDetailCol = new TableColumn<>("Xem");

        hoTenCol.setMinWidth(150);
        ngaySinhCol.setMinWidth(80);
        chucVuCol.setMinWidth(100);
        ngayBatDauDiLamCol.setMinWidth(120);
        soDienThoaiCol.setMinWidth(100);
        gioiTinhCol.setMinWidth(70);
        diaChiCol.setMinWidth(200);
        queQuanCol.setMinWidth(100);
        luongThangCol.setMinWidth(90);
        seeDetailCol.setMinWidth(50);

        ngaySinhCol.setStyle("-fx-alignment: CENTER;");
        ngayBatDauDiLamCol.setStyle("-fx-alignment: CENTER;");
        soDienThoaiCol.setStyle("-fx-alignment: CENTER;");
        gioiTinhCol.setStyle("-fx-alignment: CENTER;");
        luongThangCol.setStyle("-fx-alignment: CENTER;");
        chucVuCol.setStyle("-fx-alignment: CENTER;");
        seeDetailCol.setStyle("-fx-alignment: CENTER;");

        hoTenCol.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        soDienThoaiCol.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        gioiTinhCol.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        diaChiCol.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        queQuanCol.setCellValueFactory(new PropertyValueFactory<>("queQuan"));

        ngaySinhCol.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        ngaySinhCol.setCellFactory(param -> new TableCell<NhanVien, Date>() {
            private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            @Override
            protected void updateItem(Date date, boolean empty) {
                super.updateItem(date, empty);
                if (date == null) {
                    setGraphic(null);
                    return;
                }
                setText(format.format(date));
            }
        });

        ngayBatDauDiLamCol.setCellValueFactory(new PropertyValueFactory<>("ngayBatDauDiLam"));
        ngayBatDauDiLamCol.setCellFactory(param -> new TableCell<NhanVien, Date>() {
            private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            @Override
            protected void updateItem(Date date, boolean empty) {
                super.updateItem(date, empty);
                if (date == null) {
                    setGraphic(null);
                    return;
                }
                setText(format.format(date));
            }
        });

        luongThangCol.setCellValueFactory(new PropertyValueFactory<>("luongThang"));
        luongThangCol.setCellFactory(param -> new TableCell<NhanVien, Float>() {
            @Override
            protected void updateItem(Float luongThang, boolean empty) {
                super.updateItem(luongThang, empty);
                if (luongThang == null) {
                    setGraphic(null);
                    return;
                }
                setText(String.format("%.0f", luongThang));
            }
        });

        chucVuCol.setCellValueFactory(new PropertyValueFactory<>("loaiNhanVien"));
        chucVuCol.setCellFactory(param -> new TableCell<NhanVien, LoaiNhanVien>() {
            @Override
            protected void updateItem(LoaiNhanVien loaiNhanVien, boolean empty) {
                super.updateItem(loaiNhanVien, empty);
                if (loaiNhanVien == null) {
                    setGraphic(null);
                    return;
                }
                setText(loaiNhanVien.getTenLoaiNhanVien());
            }
        });

        seeDetailCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        seeDetailCol.setCellFactory(param -> new TableCell<NhanVien, NhanVien>() {
            private final Button button = new Button("Xem");
            @Override
            protected void updateItem(NhanVien nhanVien, boolean empty) {
                super.updateItem(nhanVien, empty);
                if (nhanVien == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> {
                    seeDetailButtonClicked(nhanVien);

                    hoTen.setDisable(true);
                    ngaySinh.setDisable(true);
                    soDienThoai.setDisable(true);
                    diaChi.setDisable(true);
                    ngayBatDauDiLam.setDisable(true);
                    queQuan.setDisable(true);
                    maleButton.setDisable(true);
                    femaleButton.setDisable(true);

                    addButton.setDisable(true);
                    updateButton.setDisable(false);
                });
            }
        });


        nhanVienTableView.getItems().addAll(dsNhanVienData);

        nhanVienTableView.getColumns().addAll(hoTenCol, ngaySinhCol, chucVuCol, luongThangCol,
                ngayBatDauDiLamCol, soDienThoaiCol, gioiTinhCol, diaChiCol, queQuanCol, seeDetailCol);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dsNhanVien = NhanVienDAO.getAllNhanVien();

        dsChucVu = LoaiNhanVienDAO.getAllLoaiNhanVien();
        dsChucVuData = FXCollections.observableArrayList(dsChucVu);
        chucVuComboBox.getItems().addAll(dsChucVuData);

        ToggleGroup group = new ToggleGroup();
        maleButton.setToggleGroup(group);
        femaleButton.setToggleGroup(group);
        maleButton.setSelected(true);

        createTableView();
    }


    public void cancelButtonClicked(ActionEvent actionEvent) {
        resetValues();
    }

    private int checkInput() {
        // Ô nhập liệu rỗng
        if (hoTen.getText().equals("") || ngaySinh.getText().equals("") ||
                luongThang.getText().equals("") || queQuan.getText().equals("") ||
                diaChi.getText().equals("") || ngayBatDauDiLam.getText().equals("") ||
                soDienThoai.getText().equals("")) {
            return -1;
        }
        else if (chucVuComboBox.getValue() == null) {
            return -2;
        }
        else if (!luongThang.getText().matches("[0-9]*")) {
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
            alert.setTitle("Các vùng nhập liệu không hợp lệ");
            alert.setContentText("Bạn chưa chọn chức vụ cho nhân viên");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
        else if (check == -3) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ô nhập liệu lương tháng không hợp hệ");
            alert.setContentText("Lương tháng phải là 1 con số");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

    public void addButtonClicked(ActionEvent actionEvent) throws ParseException {
        int check = checkInput();
        if (check != 0) {
            showAlert(check);
        }
        else {
            NhanVien nhanVien = new NhanVien();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            nhanVien.setHoTen(hoTen.getText());
            nhanVien.setNgaySinh(format.parse(ngaySinh.getText()));
            nhanVien.setSoDienThoai(soDienThoai.getText());
            nhanVien.setDiaChi(diaChi.getText());
            nhanVien.setLuongThang(Float.parseFloat(luongThang.getText()));
            nhanVien.setNgayBatDauDiLam(format.parse(ngayBatDauDiLam.getText()));
            nhanVien.setQueQuan(queQuan.getText());

            // Tên tài khoản và mật khẩu mặc định là: ddMMyyyy
            // Ví dụ: ngày sinh: 30/04/1999 thì tên tài khoản và mật khẩu là: 30041999
            nhanVien.setTenTaiKhoan(ngaySinh.getText().replaceAll("/", "").toLowerCase());
            nhanVien.setMatKhau(PasswordUtils.hash(ngaySinh.getText().replaceAll("/", "")));

            LoaiNhanVien loaiNhanVien = chucVuComboBox.getSelectionModel().getSelectedItem();
            nhanVien.setLoaiNhanVien(loaiNhanVien);

            if (maleButton.isSelected()) {
                nhanVien.setGioiTinh("Nam");
            }
            else {
                nhanVien.setGioiTinh("Nữ");
            }

            NhanVienDAO.add(nhanVien);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Chúc mừng!!!");
            alert.setContentText("Đã thêm mới thành công 1 nhân viên");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });

            resetValues();
        }
    }

    public void deleteButtonClicked(ActionEvent actionEvent) {
        if (nhanVienDangXem != null) {
            NhanVienDAO.delete(nhanVienDangXem);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Chúc mừng!!!");
            alert.setContentText("Đã xóa thành công");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });

            resetValues();
        }
    }

    public void updateButtonClicked(ActionEvent actionEvent) {
        int check = checkInput();
        if (check != 0) {
            showAlert(check);
        }
        else {
            LoaiNhanVien loaiNhanVien = chucVuComboBox.getSelectionModel().getSelectedItem();

            NhanVienDAO.updateLuongAndChucVu(nhanVienDangXem.getIdNhanVien(),
                    Float.parseFloat(luongThang.getText()), loaiNhanVien);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Chúc mừng!!!");
            alert.setContentText("Đã chỉnh sửa thành công");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });

            resetValues();
        }
    }
}