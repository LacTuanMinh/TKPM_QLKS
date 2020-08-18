package ql.khachsan.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import ql.khachsan.DAO.LoaiPhongDAO;
import ql.khachsan.DAO.PhongDAO;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class BaoCaoThongKeController implements Initializable {
    public ComboBox<String> watchValueComboBox;
    public ComboBox<String> watchTypeComboBox;
    public Label dateLabel;
    public DatePicker datePicker;
    public Label yearLabel;
    public ComboBox<Integer> yearComboBox;
    public Label monthLabel;
    public ComboBox<Integer> monthComboBox;
    public Label quarterLabel;
    public ComboBox<Integer> quarterComboBox;
    public Button showButton;
    public TableView<Object[]> reportTableView;
    public HBox hbox;
    public Label tongDoanhThu;
    public Label tongSoLuotThue;
    public Button exportButton;

    private ObservableList<Object[]> reportData;
    private List<Object[]> reportList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        watchValueComboBox.getItems().addAll("Loại phòng", "Phòng");
        watchTypeComboBox.getItems().addAll("Tất cả", "Ngày", "Tháng", "Quý", "Năm");
        yearComboBox.getItems().addAll(2016, 2017, 2018, 2019, 2020);
        monthComboBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        quarterComboBox.getItems().addAll(1, 2, 3, 4);
        datePicker.setValue(LocalDate.now());

        watchTypeComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1 != null) {
                    dateLabel.setVisible(false);
                    datePicker.setVisible(false);
                    yearLabel.setVisible(false);
                    yearComboBox.setVisible(false);
                    monthLabel.setVisible(false);
                    monthComboBox.setVisible(false);
                    quarterLabel.setVisible(false);
                    quarterComboBox.setVisible(false);

                    showButton.setVisible(true);
                    showButton.setLayoutX(watchTypeComboBox.getLayoutX() + 125);

                    datePicker.setValue(null);
                    yearComboBox.setValue(null);
                    monthComboBox.setValue(null);
                    quarterComboBox.setValue(null);

                    if (t1.equals("Ngày")) {
                        dateLabel.setVisible(true);
                        datePicker.setVisible(true);
                        showButton.setLayoutX(datePicker.getLayoutX() + 180);
                    }
                    else if (t1.equals("Tháng")) {
                        yearLabel.setVisible(true);
                        yearComboBox.setVisible(true);
                        monthLabel.setVisible(true);
                        monthComboBox.setVisible(true);
                        showButton.setLayoutX(monthComboBox.getLayoutX() + 105);
                    }
                    else if (t1.equals("Quý")) {
                        yearLabel.setVisible(true);
                        yearComboBox.setVisible(true);
                        quarterLabel.setVisible(true);
                        quarterComboBox.setVisible(true);
                        showButton.setLayoutX(quarterComboBox.getLayoutX() + 105);
                    }
                    else if (t1.equals("Năm")) {
                        yearLabel.setVisible(true);
                        yearComboBox.setVisible(true);
                        showButton.setLayoutX(yearComboBox.getLayoutX() + 105);
                    }
                }
            }
        });
    }

    private int checkInput() {
        if (watchValueComboBox.getValue() == null) {
            return -1;
        }
        // Xem theo tháng, quý, năm nhưng chưa chọn năm
        else if ((watchTypeComboBox.getSelectionModel().getSelectedIndex() == 2 ||
                watchTypeComboBox.getSelectionModel().getSelectedIndex() == 3 ||
                watchTypeComboBox.getSelectionModel().getSelectedIndex() == 4) &&
                yearComboBox.getValue() == null) {
            return -2;
        }
        // Xem theo tháng nhưng chưa chọn tháng
        else if (watchTypeComboBox.getSelectionModel().getSelectedIndex() == 2 &&
                monthComboBox.getValue() == null) {
            return -3;
        }
        // Xem theo quý nhưng chưa chọn quý
        else if (watchTypeComboBox.getSelectionModel().getSelectedIndex() == 3 &&
                quarterComboBox.getValue() == null) {
            return -4;
        }
        // Xem theo ngày nhưng chưa chọn ngày
        else if (watchTypeComboBox.getSelectionModel().getSelectedIndex() == 1 &&
                datePicker.getValue() == null) {
            return -5;
        }
        return 0;
    }

    private void showAlert(int check) {
        if (check == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Các vùng nhập liệu không hợp lệ");
            alert.setContentText("Bạn chưa chọn phòng/loại phòng để xem báo cáo");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
        else if (check == -2) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Các vùng nhập liệu không hợp lệ");
            alert.setContentText("Bạn chưa chọn năm để xem");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
        else if (check == -3) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Các vùng nhập liệu không hợp lệ");
            alert.setContentText("Bạn chưa chọn tháng để xem");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
        else if (check == -4) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Các vùng nhập liệu không hợp lệ");
            alert.setContentText("Bạn chưa chọn quý để xem");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
        else if (check == -5) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Các vùng nhập liệu không hợp lệ");
            alert.setContentText("Bạn chưa chọn ngày để xem");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

    public void showButtonClicked(ActionEvent actionEvent) {
        int check = checkInput();
        if (check != 0) {
            showAlert(check);
        }
        else {
            reportTableView.getItems().clear();
            reportTableView.getColumns().clear();
            hbox.setVisible(true);

            int watchValue = watchValueComboBox.getSelectionModel().getSelectedIndex();
            int watchType = watchTypeComboBox.getSelectionModel().getSelectedIndex();
            int year = 0;
            int quarter = 0;
            int month = 0;
            Date date = null;

            int totalRentCount = 0;
            float totalIncome = 0;

            // Xem theo ngày
            if (watchType == 1) {
                date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            }
            // Xem theo tháng
            else if (watchType == 2) {
                year = yearComboBox.getSelectionModel().getSelectedItem();
                month = monthComboBox.getSelectionModel().getSelectedItem();
            }
            // Xem theo quý
            else if (watchType == 3) {
                year = yearComboBox.getSelectionModel().getSelectedItem();
                quarter = quarterComboBox.getSelectionModel().getSelectedItem();
            }
            else if (watchType == 4) {
                year = yearComboBox.getSelectionModel().getSelectedItem();
            }

            // Xem báo cáo doanh thu loại phòng
            if (watchValue == 0) {
                reportList = LoaiPhongDAO.getBaoCaoDoanhThu(watchType, year, quarter, month, date);
                reportData = FXCollections.observableArrayList(reportList);
                createDoanhThuLoaiPhongTableView();

                for (Object[] item : reportList) {
                    totalRentCount += Integer.parseInt((String)item[2]);
                    totalIncome += Float.parseFloat((String)item[3]);
                }
            }
            // Xem báo cáo doanh thu phòng
            else if (watchValue == 1) {
                reportList = PhongDAO.getBaoCaoDoanhThu(watchType, year, quarter, month, date);
                reportData = FXCollections.observableArrayList(reportList);
                createDoanhThuPhongTableView();

                for (Object[] item : reportList) {
                    totalRentCount += Integer.parseInt((String)item[3]);
                    totalIncome += Float.parseFloat((String)item[4]);
                }
            }

            DecimalFormat formatter = new DecimalFormat("#,###");
            tongSoLuotThue.setText("Tổng số lượt thuê: " + totalRentCount);
            tongDoanhThu.setText("Tổng doanh thu: " + formatter.format(totalIncome));
        }
    }

    private void createDoanhThuLoaiPhongTableView() {
        // Tạo lần lượt các cột cho bảng
        TableColumn<Object[], String> tenLoaiPhongCol = new TableColumn<>("Tên loại phòng");
        TableColumn<Object[], String> soLuotThueCol = new TableColumn<>("Số lượt thuê");
        TableColumn<Object[], String> doanhThuCol = new TableColumn<>("Doanh thu");

        soLuotThueCol.setStyle("-fx-alignment: CENTER;");
        doanhThuCol.setStyle("-fx-alignment: CENTER;");

        tenLoaiPhongCol.setCellValueFactory(cell -> new SimpleStringProperty((String)cell.getValue()[1]));

        soLuotThueCol.setCellValueFactory(cell -> new SimpleStringProperty((String)cell.getValue()[2]));
        soLuotThueCol.setCellFactory(param -> new TableCell<Object[], String>() {
            @Override
            protected void updateItem(String soLuotThue, boolean empty) {
                super.updateItem(soLuotThue, empty);
                if (soLuotThue == null) {
                    setGraphic(null);
                    setText("");
                    return;
                }
                setText(soLuotThue);
            }
        });

        doanhThuCol.setCellValueFactory(cell -> new SimpleStringProperty((String)cell.getValue()[3]));
        doanhThuCol.setCellFactory(param -> new TableCell<Object[], String>() {
            @Override
            protected void updateItem(String doanhThu, boolean empty) {
                super.updateItem(doanhThu, empty);
                if (doanhThu == null) {
                    setGraphic(null);
                    setText("");
                    return;
                }
                setText(doanhThu);
            }
        });

        reportTableView.getItems().addAll(reportData);

        reportTableView.getColumns().addAll(tenLoaiPhongCol, soLuotThueCol, doanhThuCol);
    }

    private void createDoanhThuPhongTableView() {
        // Tạo lần lượt các cột cho bảng
        TableColumn<Object[], String> tenPhongCol = new TableColumn<>("Tên phòng");
        TableColumn<Object[], String> tenLoaiPhongCol = new TableColumn<>("Tên loại phòng");
        TableColumn<Object[], String> soLuotThueCol = new TableColumn<>("Số lượt thuê");
        TableColumn<Object[], String> doanhThuCol = new TableColumn<>("Doanh thu");

        soLuotThueCol.setStyle("-fx-alignment: CENTER;");
        doanhThuCol.setStyle("-fx-alignment: CENTER;");

        tenPhongCol.setCellValueFactory(cell -> new SimpleStringProperty((String)cell.getValue()[1]));
        tenLoaiPhongCol.setCellValueFactory(cell -> new SimpleStringProperty((String)cell.getValue()[2]));

        soLuotThueCol.setCellValueFactory(cell -> new SimpleStringProperty((String)cell.getValue()[3]));
        soLuotThueCol.setCellFactory(param -> new TableCell<Object[], String>() {
            @Override
            protected void updateItem(String soLuotThue, boolean empty) {
                super.updateItem(soLuotThue, empty);
                if (soLuotThue == null) {
                    setGraphic(null);
                    setText("");
                    return;
                }
                setText(soLuotThue);
            }
        });

        doanhThuCol.setCellValueFactory(cell -> new SimpleStringProperty((String)cell.getValue()[4]));
        doanhThuCol.setCellFactory(param -> new TableCell<Object[], String>() {
            @Override
            protected void updateItem(String doanhThu, boolean empty) {
                super.updateItem(doanhThu, empty);
                if (doanhThu == null) {
                    setGraphic(null);
                    setText("");
                    return;
                }
                setText(doanhThu);
            }
        });

        reportTableView.getItems().addAll(reportData);

        reportTableView.getColumns().addAll(tenPhongCol, tenLoaiPhongCol, soLuotThueCol, doanhThuCol);
    }

    public void exportButtonClicked(ActionEvent actionEvent) {
        // export PDF
    }
}
