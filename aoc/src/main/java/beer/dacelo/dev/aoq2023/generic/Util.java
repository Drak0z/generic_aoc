package beer.dacelo.dev.aoq2023.generic;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Util {
    /**
     * Function to return whether a given number is a prime number
     * 
     * @param n number to check
     * @return true/false the number is prime
     */
    public static Boolean isPrime(Integer n) {
	if (n <= 1)
	    return false;
	for (int i = 2; i <= Math.sqrt(n); i++) {
	    if (n % i == 0)
		return false;
	}
	return true;
    } // isPrime(Integer n)

    /**
     * Function to return whether a given number is a prime number
     * 
     * @param n number to check
     * @return true/false the number is prime
     */
    public static Boolean isPrime(Long n) {
	if (n <= 1)
	    return false;
	for (Long i = 2L; i <= Math.sqrt(n); i++) {
	    if (n % i == 0)
		return false;
	}
	return true;
    } // isPrime(Long n)

    /**
     * Function to return whether a given number is a prime number
     * 
     * @param n number to check
     * @return true/false the number is prime
     */
    public static Boolean isPrime(Double n) {
	if (n <= 1)
	    return false;
	if (n % 1 != 0)
	    return false;
	for (Double i = 2.0; i <= Math.sqrt(n); i++) {
	    if (n % i == 0)
		return false;
	}
	return true;
    } // isPrime(Double n)

    /**
     * Function to return whether a given number is a prime number
     * 
     * @param n number to check
     * @return true/false the number is prime
     */
    public static Boolean isPrime(BigInteger n) {
	if (n.compareTo(BigInteger.ONE) <= 0)
	    return false; // we're not a whole number
	for (BigInteger i = new BigInteger("2"); i.compareTo(n.sqrt()) <= 0; i = i.add(BigInteger.ONE)) {
	    if ((n.remainder(i)).compareTo(BigInteger.ZERO) == 0)
		return false;
	}
	return true;
    } // isPrime(BigInteger n)

    /**
     * Function to return whether a given number is a prime number
     * 
     * @param n number to check
     * @return true/false the number is prime
     */
    public static Boolean isPrime(BigDecimal n) {
	if ((n.remainder(BigDecimal.ONE)).compareTo(BigDecimal.ZERO) != 0)
	    return false;
	if (n.compareTo(BigDecimal.ONE) <= 0)
	    return false;
	for (BigDecimal i = new BigDecimal(2); i.compareTo(n.sqrt(new MathContext(10))) <= 0; i = i
		.add(BigDecimal.ONE)) {
	    if ((n.remainder(i)).compareTo(BigDecimal.ZERO) == 0)
		return false;
	}
	return true;
    } // isPrime(BigDecimal n)

    /**
     * Generic helper function to create a standard JavaFX Pop-up window
     * 
     * @param title   Title of the pop-up window
     * @param header  Header (= lead text) of the pop-up window
     * @param content Content (= copy/paste-able answer) in the pop-up window
     * @param detail  Detail in an expanding text-field/box
     */
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
	} // if

	alert.showAndWait();
    } // PopupWindow
}
