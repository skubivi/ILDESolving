package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextField nT;

    //Функция кнопки на начальном экране
    @FXML
    public void click(ActionEvent event) {
        try {
            int n = Integer.parseInt(nT.getCharacters().toString());
            Main.cont(n, event);
        } catch (Exception e) {

        }
    }
}
