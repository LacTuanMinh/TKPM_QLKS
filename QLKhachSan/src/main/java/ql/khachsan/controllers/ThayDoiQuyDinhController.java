package ql.khachsan.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ql.khachsan.App;
import ql.khachsan.models.DAO.ThamSoDAO;
import ql.khachsan.models.entities.ThamSo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ThayDoiQuyDinhController implements Initializable {
    public RadioButton enable;
    public RadioButton disable;
    public TextField phanTram10Ngay;
    public TextField phanTram20Ngay;
    public Button luuThayDoiBtn;
    public static Stage stage = new Stage();
    private ThamSo thamSo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        thamSo = ThamSoDAO.getThamSo();
        final ToggleGroup group = new ToggleGroup();

        enable.setToggleGroup(group);
        disable.setToggleGroup(group);

        disable.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean isNowSelected) {
                if (isNowSelected) {
                    phanTram10Ngay.setDisable(true);
                    phanTram10Ngay.setText("");
                    phanTram20Ngay.setDisable(true);
                    phanTram20Ngay.setText("");
                }
            }
        });

        enable.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean isNowSelected) {
                if (isNowSelected) {
                    phanTram10Ngay.setDisable(false);
                    phanTram10Ngay.setText(thamSo.getPhanTram10Ngay() + "");
                    phanTram20Ngay.setDisable(false);
                    phanTram20Ngay.setText(thamSo.getPhanTram20Ngay() + "");
                }
            }
        });

        if (thamSo.getIsDisable()) {
            disable.setSelected(true);
            phanTram10Ngay.setDisable(true);
            phanTram10Ngay.setText("");
            phanTram20Ngay.setDisable(true);
            phanTram20Ngay.setText("");
        } else {
            enable.setSelected(true);
            phanTram10Ngay.setDisable(false);
            phanTram10Ngay.setText(thamSo.getPhanTram10Ngay() + "");
            phanTram20Ngay.setDisable(false);
            phanTram20Ngay.setText(thamSo.getPhanTram20Ngay() + "");
        }
    }

    public void thayDoiQuyDinhWindow() throws IOException {
        FXMLLoader loader = App.getFXMLLoader("thayDoiQuyDinh");
        Parent root = loader.load();
//        LapPhieuController controller = loader.getController();
//        controller.setWindow(phong);
//        controller.cardView = cardView;
        stage.setTitle("Thiết lập quy định");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        if (stage.getModality() == Modality.NONE)
            stage.initModality(Modality.APPLICATION_MODAL);
        if (stage.getOwner() == null)
            stage.initOwner(App.homeStage);
        stage.show();
    }

    public void luuThayDoiBtn(ActionEvent actionEvent) {
        if (disable.isSelected()) {
            thamSo.setIsDisable(true);
            ThamSoDAO.update(thamSo);
            showAlert("Bạn đã tạm ngưng chiết khấu trong tính tổng tiền");
        } else if (enable.isSelected()) {
//            if()
            Float a;
            Float b;
            try {
                a = Float.parseFloat(this.phanTram10Ngay.getText());
                b = Float.parseFloat(this.phanTram20Ngay.getText());
            } catch (NumberFormatException e) {

                showAlert("Dữ liệu nhập sai định dạng hoặc dữ liệu trống");
                return;
            }
            if (a < 0 || a > 100 || b > 100 || b < 0) {
                showAlert("Dữ liệu phải nhỏ hơn bằng 100% và lớn hơn bằng 0%");
                return;
            }
            thamSo.setIsDisable(false);
            thamSo.setPhanTram10Ngay(a);
            thamSo.setPhanTram20Ngay(b);
            ThamSoDAO.update(thamSo);
            showAlert("Thay đổi thành công");
        }
    }

    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
