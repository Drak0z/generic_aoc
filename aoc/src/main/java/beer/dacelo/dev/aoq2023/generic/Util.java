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
    public static void PopupWindow(String title, String header, String content, String detail) {
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setTitle(title);
	alert.setHeaderText(header);
	alert.setContentText(content);
	

	if (detail.length() > 0 ) {
        	Label lbl = new Label("Detailed output:");
        	TextArea textArea = new TextArea(detail);
        	textArea.setFont(Font.font("Consolas", FontWeight.THIN, 12));
        	textArea.setMaxWidth(Double.MAX_VALUE);
        	textArea.setMaxHeight(Double.MAX_VALUE);
        	GridPane.setVgrow(textArea,  Priority.ALWAYS);
        	GridPane.setHgrow(textArea,  Priority.ALWAYS);
        	
        	GridPane expContent = new GridPane();
        	expContent.setMaxWidth(Double.MAX_VALUE);
        	expContent.add(lbl, 0,0);
        	expContent.add(textArea, 0, 1);
        	alert.getDialogPane().setExpandableContent(expContent);
	}
	
	alert.showAndWait();
    }
}
