package beer.dacelo.dev.aoq2023.generic;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Util {
    // Keep the integer and long separate, because ints are significantly lighter to process
    public static Boolean isPrime(Integer n) {
	if (n <= 1) return false;
	for (int i = 2; i <= Math.sqrt(n); i++) {
	    if (n % i == 0) return false;
	}
	return true;
    }
    
    public static Boolean isPrime(Long n) {
	if (n <= 1) return false;
	for (Long i = 2L; i <= Math.sqrt(n); i++) {
	    if (n % i == 0) return false;
	}
	return true;
    }
    
    public static void PopupWindow(String title, String header, String content, String detail) {
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setTitle(title);
	alert.setHeaderText(header);
	TextArea answerTextArea = new TextArea(content);
	answerTextArea.setFont(Font.font("Consolas", FontWeight.THIN, 12));
	answerTextArea.setPrefRowCount(1);
	answerTextArea.setMaxWidth(Double.MAX_VALUE);
	answerTextArea.setMaxHeight(Double.MAX_VALUE);
	GridPane.setVgrow(answerTextArea, Priority.ALWAYS);
	GridPane.setHgrow(answerTextArea, Priority.ALWAYS);
	alert.getDialogPane().setContent(answerTextArea);

	if (detail.length() > 0) {
	    Label lbl = new Label("Detailed output:");
	    TextArea detailTextArea = new TextArea(detail);
	    detailTextArea.setFont(Font.font("Consolas", FontWeight.THIN, 12));
	    detailTextArea.setMaxWidth(Double.MAX_VALUE);
	    detailTextArea.setMaxHeight(Double.MAX_VALUE);
	    GridPane.setVgrow(detailTextArea, Priority.ALWAYS);
	    GridPane.setHgrow(detailTextArea, Priority.ALWAYS);

	    GridPane expContent = new GridPane();
	    expContent.setMaxWidth(Double.MAX_VALUE);
	    expContent.add(lbl, 0, 0);
	    expContent.add(detailTextArea, 0, 1);
	    alert.getDialogPane().setExpandableContent(expContent);
	}

	alert.showAndWait();
    }
}
