package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextField nT;

    private static void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ошибка");
        alert.setContentText("Введите данные правильно");

        alert.showAndWait();
    }

    //Функция кнопки на начальном экране
    @FXML
    public void click(ActionEvent event) {
        try {
            int n = Integer.parseInt(nT.getCharacters().toString());
            Main.cont(n, event);
        } catch (Exception e) {
            showAlert();
        }
    }
}
