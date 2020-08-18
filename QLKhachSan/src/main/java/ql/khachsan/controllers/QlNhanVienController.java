package ql.khachsan.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ql.khachsan.DAO.LoaiNhanVienDAO;
import ql.khachsan.DAO.NhanVienDAO;
import ql.khachsan.DAO.PhongDAO;
import ql.khachsan.models.LoaiNhanVien;
import ql.khachsan.models.NhanVien;
import ql.khachsan.utils.PasswordUtils;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public TextField cmnd;

    private ObservableList<LoaiNhanVien> dsChucVuData;
    private List<LoaiNhanVien> dsChucVu;

    private ObservableList<NhanVien> dsNhanVienData;
    private List<NhanVien> dsNhanVien;

    private NhanVien nhanVienDangXem = null;

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    private void seeDetailButtonClicked(NhanVien nhanVien) {
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

    private void updateView() {
        dsNhanVien = NhanVienDAO.getAllNhanVien();
        dsNhanVienData = FXCollections.observableArrayList(dsNhanVien);
        nhanVienTableView.getItems().addAll(dsNhanVienData);
    }

    private void resetValues() {
        hoTen.setText("");
        ngaySinh.setText("");
        soDienThoai.setText("");
        diaChi.setText("");
        luongThang.setText("");
        ngayBatDauDiLam.setText("");
        queQuan.setText("");
        cmnd.setText("");

        cmnd.setDisable(false);
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
        TableColumn<NhanVien, String> cmndCol = new TableColumn<>("CMND");
        hoTenCol.setMinWidth(100);
        cmndCol.setMinWidth(100);
        ngaySinhCol.setMinWidth(80);
        chucVuCol.setMinWidth(100);
        ngayBatDauDiLamCol.setMinWidth(120);
        soDienThoaiCol.setMinWidth(100);
        gioiTinhCol.setMinWidth(70);
        diaChiCol.setMinWidth(150);
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
        cmndCol.setStyle("-fx-alignment: CENTER;");

        hoTenCol.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        soDienThoaiCol.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        gioiTinhCol.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        diaChiCol.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        queQuanCol.setCellValueFactory(new PropertyValueFactory<>("queQuan"));
        cmndCol.setCellValueFactory(new PropertyValueFactory<>("cmnd"));
        ngaySinhCol.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        ngaySinhCol.setCellFactory(param -> new TableCell<NhanVien, Date>() {
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
                    cmnd.setDisable(true);

                    addButton.setDisable(true);
                    updateButton.setDisable(false);
                });
            }
        });

        nhanVienTableView.getItems().addAll(dsNhanVienData);

        nhanVienTableView.getColumns().addAll(hoTenCol,cmndCol, ngaySinhCol, chucVuCol, luongThangCol,
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


    public void cancelButtonClicked(ActionEvent actionEvent) {
        resetValues();
    }

    private int checkInput() {
        String regex = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
        Pattern pattern = Pattern.compile(regex);
        // Ô nhập liệu rỗng
        if (hoTen.getText().equals("") || ngaySinh.getText().equals("") ||
                luongThang.getText().equals("") || queQuan.getText().equals("") ||
                diaChi.getText().equals("") || ngayBatDauDiLam.getText().equals("") ||
                soDienThoai.getText().equals("") || cmnd.getText().equals("")) {
            return -1;
        }
        else if (chucVuComboBox.getValue() == null) {
            return -2;
        }
        else if (!luongThang.getText().matches("[0-9]*")) {
            return -3;
        }
        else if (!pattern.matcher(ngaySinh.getText()).matches()) {
            return -4;
        }
        else if (!pattern.matcher(ngayBatDauDiLam.getText()).matches()) {
            return -5;
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
        else if (check == -4) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ô nhập liệu ngày sinh không hợp hệ");
            alert.setHeaderText("Ngày sinh phải theo định dạng: dd/MM/yyyy");
            alert.setContentText("Ví dụ: 24/12/1999");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
        else if (check == -5) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ô nhập liệu ngày bắt đầu đi làm không hợp hệ");
            alert.setHeaderText("Ngày bắt đầu đi làm phải theo định dạng: dd/MM/yyyy");
            alert.setContentText("Ví dụ: 24/12/1999");
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

            nhanVien.setHoTen(hoTen.getText());
            nhanVien.setNgaySinh(format.parse(ngaySinh.getText()));
            nhanVien.setSoDienThoai(soDienThoai.getText());
            nhanVien.setDiaChi(diaChi.getText());
            nhanVien.setLuongThang(Float.parseFloat(luongThang.getText()));
            nhanVien.setNgayBatDauDiLam(format.parse(ngayBatDauDiLam.getText()));
            nhanVien.setQueQuan(queQuan.getText());
            nhanVien.setCmnd(cmnd.getText());
            // Tên tài khoản và mật khẩu mặc định là CMND
            // Ví dụ: 123456789 thì tên tài khoản và mật khẩu là: 123456789
            nhanVien.setTenTaiKhoan(cmnd.getText());
            nhanVien.setMatKhau(PasswordUtils.hash(cmnd.getText()));

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

            updateView();
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

            updateView();
            resetValues();
        }
    }
}
