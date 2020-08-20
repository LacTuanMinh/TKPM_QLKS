package ql.khachsan.controllers;

import com.itextpdf.text.pdf.PdfPTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import javafx.stage.Modality;
import javafx.stage.Stage;

import ql.khachsan.App;
import ql.khachsan.DAO.DoanhThuDAO;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class BaoCaoThongKeController implements Initializable {
    public ComboBox<String> watchValueComboBox;
    public Label watchTypeLabel;
    public ComboBox<String> watchTypeComboBox;
    public Label dateLabel;
    public DatePicker datePicker;
    public Label yearLabel;
    public ComboBox<Integer> yearComboBox;
    public Label monthLabel;
    public ComboBox<Integer> monthComboBox;
    public Label quarterLabel;
    public ComboBox<Integer> quarterComboBox;
    public Label fromYearLabel;
    public ComboBox<Integer> fromYearComboBox;
    public Label toYearLabel;
    public ComboBox<Integer> toYearComboBox;
    public Button showButton;
    public TableView<Object[]> reportTableView;
    public HBox hbox;
    public Label tongDoanhThu;
    public Label tongSoLuotThue;
    public Button exportButton;

    private ObservableList<Object[]> reportData;
    private List<Object[]> reportList;
    private List<Object[]> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        watchValueComboBox.getItems().addAll("Theo loại phòng", "Theo phòng", "Giữa các năm",
                "Giữa các tháng trong năm", "Giữa các ngày trong tháng");
        watchTypeComboBox.getItems().addAll("Tất cả", "Ngày", "Tháng", "Quý", "Năm");
        fromYearComboBox.getItems().addAll(2017, 2018, 2019, 2020);
        toYearComboBox.getItems().addAll(2017, 2018, 2019, 2020);
        yearComboBox.getItems().addAll(2017, 2018, 2019, 2020);
        monthComboBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        quarterComboBox.getItems().addAll(1, 2, 3, 4);
        datePicker.setValue(LocalDate.now());

        watchValueComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1 != null) {
                    watchTypeLabel.setVisible(false);
                    watchTypeComboBox.setVisible(false);
                    fromYearLabel.setVisible(false);
                    fromYearComboBox.setVisible(false);
                    toYearLabel.setVisible(false);
                    toYearComboBox.setVisible(false);

                    dateLabel.setVisible(false);
                    datePicker.setVisible(false);
                    yearLabel.setVisible(false);
                    yearComboBox.setVisible(false);
                    monthLabel.setVisible(false);
                    monthComboBox.setVisible(false);
                    quarterLabel.setVisible(false);
                    quarterComboBox.setVisible(false);

                    showButton.setVisible(false);

                    yearLabel.setLayoutX(481);
                    yearComboBox.setLayoutX(542);
                    monthLabel.setLayoutX(649);
                    monthComboBox.setLayoutX(718);

                    watchTypeComboBox.setValue(null);

                    if (t1.equals("Theo loại phòng") || t1.equals("Theo phòng")) {
                        watchTypeLabel.setVisible(true);
                        watchTypeComboBox.setVisible(true);
                    }
                    else if (t1.equals("Giữa các năm")) {
                        fromYearLabel.setVisible(true);
                        fromYearComboBox.setVisible(true);
                        toYearLabel.setVisible(true);
                        toYearComboBox.setVisible(true);
                        showButton.setVisible(true);
                        showButton.setLayoutX(toYearComboBox.getLayoutX() + 105);
                    }
                    else if (t1.equals("Giữa các tháng trong năm")) {
                        yearLabel.setVisible(true);
                        yearComboBox.setVisible(true);
                        showButton.setVisible(true);
                        yearLabel.setLayoutX(watchValueComboBox.getLayoutX() + 205);
                        yearComboBox.setLayoutX(yearLabel.getLayoutX() + 65);
                        showButton.setLayoutX(yearComboBox.getLayoutX() + 105);
                    }
                    else if (t1.equals("Giữa các ngày trong tháng")) {
                        yearLabel.setVisible(true);
                        yearComboBox.setVisible(true);
                        monthLabel.setVisible(true);
                        monthComboBox.setVisible(true);
                        showButton.setVisible(true);
                        yearLabel.setLayoutX(watchValueComboBox.getLayoutX() + 205);
                        yearComboBox.setLayoutX(yearLabel.getLayoutX() + 65);
                        monthLabel.setLayoutX(yearComboBox.getLayoutX() + 105);
                        monthComboBox.setLayoutX(monthLabel.getLayoutX() + 70);
                        showButton.setLayoutX(monthComboBox.getLayoutX() + 105);
                    }
                }
            }
        });

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
        // Chưa chọn loại doanh thu để xem báo cáo
        if (watchValueComboBox.getValue() == null) {
            return -1;
        }
        // Xem theo tháng, quý, năm/Xem doanh thu theo từng tháng trong 1 năm/từng ngày trong 1 tháng nhưng chưa chọn năm
        else if ((watchTypeComboBox.getSelectionModel().getSelectedIndex() == 2 ||
                watchTypeComboBox.getSelectionModel().getSelectedIndex() == 3 ||
                watchTypeComboBox.getSelectionModel().getSelectedIndex() == 4 ||
                watchValueComboBox.getSelectionModel().getSelectedIndex() == 3 ||
                watchValueComboBox.getSelectionModel().getSelectedIndex() == 4) &&
                yearComboBox.getValue() == null) {
            return -2;
        }
        // Xem theo tháng/Xem doanh thu từng ngày trong 1 tháng nhưng chưa chọn tháng
        else if ((watchTypeComboBox.getSelectionModel().getSelectedIndex() == 2 ||
                watchValueComboBox.getSelectionModel().getSelectedIndex() == 4) &&
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
        // Chọn xem doanh thu giữa các năm nhưng chọn năm bắt đầu
        else if (watchValueComboBox.getSelectionModel().getSelectedIndex() == 2 &&
                fromYearComboBox.getValue() == null) {
            return -6;
        }
        // Chọn xem doanh thu giữa các năm nhưng chọn năm kết thúc
        else if (watchValueComboBox.getSelectionModel().getSelectedIndex() == 2 &&
                toYearComboBox.getValue() == null) {
            return -7;
        }
        // Năm bắt đầu lớn hơn năm kết thúc/Năm kết thúc nhỏ hơn năm bắt đầu
        else if (watchValueComboBox.getSelectionModel().getSelectedIndex() == 2 &&
                toYearComboBox.getValue() < fromYearComboBox.getValue()) {
            return -8;
        }
        return 0;
    }

    private void showAlert(int check) {
        if (check == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Các vùng nhập liệu không hợp lệ");
            alert.setContentText("Bạn chưa loại doanh thu để xem báo cáo");
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
        else if (check == -6) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Các vùng nhập liệu không hợp lệ");
            alert.setContentText("Bạn chưa chọn năm bắt đầu");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
        else if (check == -7) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Các vùng nhập liệu không hợp lệ");
            alert.setContentText("Bạn chưa chọn năm kết thúc");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
        else if (check == -8) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Các vùng nhập liệu không hợp lệ");
            alert.setContentText("Năm bắt đầu lớn hơn năm kết thúc/Năm kết thúc nhỏ hơn năm bắt đầu");
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
            int fromYear = 0;
            int toYear = 0;

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
            // Xem theo năm
            else if (watchType == 4) {
                year = yearComboBox.getSelectionModel().getSelectedItem();
            }

            // Xem báo cáo doanh thu loại phòng
            if (watchValue == 0) {
                reportList = DoanhThuDAO.getBaoCaoDoanhThuTheoLoaiPhong(watchType, year, quarter, month, date);
                reportData = FXCollections.observableArrayList(reportList);
                createDoanhThuLoaiPhongTableView();

                for (Object[] item : reportList) {
                    totalRentCount += Integer.parseInt((String)item[2]);
                    totalIncome += Float.parseFloat((String)item[3]);
                }
            }
            // Xem báo cáo doanh thu phòng
            else if (watchValue == 1) {
                reportList = DoanhThuDAO.getBaoCaoDoanhThuTheoPhong(watchType, year, quarter, month, date);
                reportData = FXCollections.observableArrayList(reportList);
                createDoanhThuPhongTableView();

                for (Object[] item : reportList) {
                    totalRentCount += Integer.parseInt((String)item[3]);
                    totalIncome += Float.parseFloat((String)item[4]);
                }
            }
            // Xem báo cáo doanh thu giữa các năm
            else if (watchValue == 2) {
                fromYear = fromYearComboBox.getValue();
                toYear = toYearComboBox.getValue();
                reportList = DoanhThuDAO.getDoanhThuGiuaCacNam(fromYear, toYear);
                reportData = FXCollections.observableArrayList(reportList);
                createDoanhThuGiuaCacNamTableView();

                for (Object[] item : reportList) {
                    totalRentCount += Integer.parseInt((String)item[1]);
                    totalIncome += Float.parseFloat((String)item[2]);
                }
            }
            // Xem báo cáo doanh thu giữa các tháng trong 1 năm
            else if (watchValue == 3) {
                year = yearComboBox.getValue();
                reportList = DoanhThuDAO.getDoanhThuTrong1NamTheoTungThang(year);
                reportData = FXCollections.observableArrayList(reportList);
                createDoanhThuGiuaCacThangTrong1NamTableView();

                for (Object[] item : reportList) {
                    totalRentCount += Integer.parseInt((String)item[1]);
                    totalIncome += Float.parseFloat((String)item[2]);
                }
            }
            // Xem báo cáo doanh thu giữa các ngày trong 1 tháng
            else if (watchValue == 4) {
                year = yearComboBox.getValue();
                month = monthComboBox.getValue();
                reportList = DoanhThuDAO.getDoanhThuTrong1Thang(year, month);
                reportData = FXCollections.observableArrayList(reportList);
                createDoanhThuGiuaCacNgayTrong1ThangTableView();

                for (Object[] item : reportList) {
                    totalRentCount += Integer.parseInt((String)item[1]);
                    totalIncome += Float.parseFloat((String)item[2]);
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

    private void createDoanhThuGiuaCacNamTableView() {
        // Tạo lần lượt các cột cho bảng
        TableColumn<Object[], String> namCol = new TableColumn<>("Năm");
        TableColumn<Object[], String> soLuotThueCol = new TableColumn<>("Số lượt thuê");
        TableColumn<Object[], String> doanhThuCol = new TableColumn<>("Doanh thu");

        namCol.setStyle("-fx-alignment: CENTER;");
        soLuotThueCol.setStyle("-fx-alignment: CENTER;");
        doanhThuCol.setStyle("-fx-alignment: CENTER;");

        namCol.setCellValueFactory(cell -> new SimpleStringProperty((String)cell.getValue()[0]));

        soLuotThueCol.setCellValueFactory(cell -> new SimpleStringProperty((String)cell.getValue()[1]));
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

        doanhThuCol.setCellValueFactory(cell -> new SimpleStringProperty((String)cell.getValue()[2]));
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

        reportTableView.getColumns().addAll(namCol, soLuotThueCol, doanhThuCol);
    }

    private void createDoanhThuGiuaCacThangTrong1NamTableView() {
        // Tạo lần lượt các cột cho bảng
        TableColumn<Object[], String> thangCol = new TableColumn<>("Tháng");
        TableColumn<Object[], String> soLuotThueCol = new TableColumn<>("Số lượt thuê");
        TableColumn<Object[], String> doanhThuCol = new TableColumn<>("Doanh thu");

        thangCol.setStyle("-fx-alignment: CENTER;");
        soLuotThueCol.setStyle("-fx-alignment: CENTER;");
        doanhThuCol.setStyle("-fx-alignment: CENTER;");

        thangCol.setCellValueFactory(cell -> new SimpleStringProperty((String)cell.getValue()[0]));

        soLuotThueCol.setCellValueFactory(cell -> new SimpleStringProperty((String)cell.getValue()[1]));
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

        doanhThuCol.setCellValueFactory(cell -> new SimpleStringProperty((String)cell.getValue()[2]));
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

        reportTableView.getColumns().addAll(thangCol, soLuotThueCol, doanhThuCol);
    }

    private void createDoanhThuGiuaCacNgayTrong1ThangTableView() {
        // Tạo lần lượt các cột cho bảng
        TableColumn<Object[], String> ngayCol = new TableColumn<>("Ngày");
        TableColumn<Object[], String> soLuotThueCol = new TableColumn<>("Số lượt thuê");
        TableColumn<Object[], String> doanhThuCol = new TableColumn<>("Doanh thu");

        ngayCol.setStyle("-fx-alignment: CENTER;");
        soLuotThueCol.setStyle("-fx-alignment: CENTER;");
        doanhThuCol.setStyle("-fx-alignment: CENTER;");

        ngayCol.setCellValueFactory(cell -> new SimpleStringProperty((String)cell.getValue()[0]));

        soLuotThueCol.setCellValueFactory(cell -> new SimpleStringProperty((String)cell.getValue()[1]));
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

        doanhThuCol.setCellValueFactory(cell -> new SimpleStringProperty((String)cell.getValue()[2]));
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

        reportTableView.getColumns().addAll(ngayCol, soLuotThueCol, doanhThuCol);
    }

    public void exportButtonClicked(ActionEvent actionEvent) {
        int value = watchValueComboBox.getSelectionModel().getSelectedIndex();

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        StackedBarChart<String, Number> sbc = new StackedBarChart<String, Number>(xAxis, yAxis);

        if (value == 2) {
            sbc.setTitle("Báo cáo doanh thu tổng thể");
            xAxis.setLabel("Năm");
            yAxis.setLabel("Doanh thu");

            for (Object[] item : reportList) {
                XYChart.Series series = new XYChart.Series();
                series.setName((String)item[0]);
                series.getData().add(new XYChart.Data("Năm " + item[0],
                        Float.parseFloat((String)item[2])));
                sbc.getData().add(series);
            }
        }
        else if (value == 3) {
            sbc.setTitle("Báo cáo doanh thu năm " + yearComboBox.getValue());
            xAxis.setLabel("Tháng");
            yAxis.setLabel("Doanh thu");

            for (Object[] item : reportList) {
                XYChart.Series series = new XYChart.Series();
                series.setName((String)item[0]);
                series.getData().add(new XYChart.Data("Tháng " + item[0],
                        Float.parseFloat((String)item[2])));
                sbc.getData().add(series);
            }
        }
        else if (value == 4) {
            sbc.setTitle("Báo cáo doanh thu tháng: " + monthComboBox.getValue() + "/" + yearComboBox.getValue());
            xAxis.setLabel("Ngày");
            yAxis.setLabel("Doanh thu");

            for (Object[] item : reportList) {
                XYChart.Series series = new XYChart.Series();
                series.setName((String)item[0]);
                series.getData().add(new XYChart.Data("Ngày " + item[0],
                        Float.parseFloat((String)item[2])));
                sbc.getData().add(series);
            }
        }

        sbc.setLegendVisible(false);

        Scene scene = new Scene(sbc, 1280, 700);
        Stage stage = new Stage();
        stage.setTitle("Báo cáo doanh thu");
        stage.setScene(scene);
        stage.setResizable(false);

        // Wait until this stage is complete
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.homeStage);
        stage.show();
    }
}
