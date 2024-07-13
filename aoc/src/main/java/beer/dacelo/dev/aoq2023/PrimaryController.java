package beer.dacelo.dev.aoq2023;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrimaryController {

    @FXML Button aoc2022button;
    @FXML Button aoq2023button;
    @FXML Button soq2024button;

    @FXML
    private void switchToAoC2022() throws IOException {
        App.setRoot("aoc2022");
    }
    @FXML
    private void switchToAoC2023() throws IOException {
        App.setRoot("aoc2023");
    }
    @FXML
    private void switchToAoQ2023() throws IOException {
        App.setRoot("aoq2023");
    }
    @FXML
    private void switchToSoQ2024() throws IOException {
        App.setRoot("soq2024");
    }
}
