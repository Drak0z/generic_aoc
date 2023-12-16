package beer.dacelo.dev.aoq2023.generic;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.time.Duration;
import java.util.Arrays;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
	if (n == 2)
	    return true;
	if (n % 2 == 0)
	    return false;
	for (int i = 3; i <= Math.sqrt(n); i += 2) {
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
	if (n == 2)
	    return true;
	if (n % 2 == 0)
	    return false;
	for (Long i = 3L; i <= Math.sqrt(n); i += 2) {
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

	if (n == 2)
	    return true;
	if (n % 2 == 0)
	    return false;
	for (Double i = 3.0; i <= Math.sqrt(n); i += 2) {
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
	    return false;
	if (n.equals(new BigInteger("2")))
	    return true;
	if ((n.remainder(new BigInteger("2"))).equals(BigInteger.ZERO))
	    return false;
	for (BigInteger i = new BigInteger("3"); i.compareTo(n.sqrt()) <= 0; i = i.add(new BigInteger("2"))) {
	    if ((n.remainder(i)).equals(BigInteger.ZERO))
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
	    return false; // we're not a whole number
	if (n.compareTo(BigDecimal.ONE) <= 0)
	    return false;
	if (n.equals(new BigDecimal(2)))
	    return true;
	if ((n.remainder(new BigDecimal(2))).equals(BigDecimal.ZERO))
	    return false;
	for (BigDecimal i = new BigDecimal(3); i.compareTo(n.sqrt(new MathContext(10))) <= 0; i = i
		.add(new BigDecimal(2))) {
	    if ((n.remainder(i)).equals(BigDecimal.ZERO))
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
	answerTextArea.setPrefRowCount(1 + content.length() - content.replace(System.lineSeparator(), "").length());
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

    private static Long gcd(Long x, Long y) {
	return (y == 0) ? x : gcd(y, x % y);
    }

    public static Long gcd(Long... numbers) {
	return Arrays.stream(numbers).reduce(0L, (x, y) -> gcd(x, y));
    }

    public static Long lcm(Long... numbers) {
	return Arrays.stream(numbers).reduce(1L, (x, y) -> x * (y / gcd(x, y)));
    }

    public static Double getSIMultiplier(String siPrefix) {
	Double multiplier = 1.0; // apply SI here
	switch (siPrefix) {
	case "q":
	    multiplier = Math.pow(10, -30);
	    break; // quecto
	case "r":
	    multiplier = Math.pow(10, -27);
	    break; // ronto
	case "y":
	    multiplier = Math.pow(10, -24);
	    break; // yocto
	case "z":
	    multiplier = Math.pow(10, -21);
	    break; // zepto
	case "a":
	    multiplier = Math.pow(10, -18);
	    break; // atto
	case "f":
	    multiplier = Math.pow(10, -15);
	    break; // femto
	case "p":
	    multiplier = Math.pow(10, -12);
	    break; // pico
	case "n":
	    multiplier = Math.pow(10, -9);
	    break; // nano
	case "μ":
	    multiplier = Math.pow(10, -6);
	    break; // micro
	case "m":
	    multiplier = Math.pow(10, -3);
	    break; // milli
	case "c":
	    multiplier = Math.pow(10, -2);
	    break; // centi
	case "d":
	    multiplier = Math.pow(10, -1);
	    break; // deci
	case "":
	    multiplier = Math.pow(10, 0);
	    break; // as is
	case "da":
	    multiplier = Math.pow(10, 1);
	    break; // deca
	case "h":
	    multiplier = Math.pow(10, 2);
	    break; // hecto
	case "k":
	    multiplier = Math.pow(10, 3);
	    break; // kilo
	case "M":
	    multiplier = Math.pow(10, 6);
	    break; // mega
	case "G":
	    multiplier = Math.pow(10, 9);
	    break; // giga
	case "T":
	    multiplier = Math.pow(10, 12);
	    break; // tera
	case "P":
	    multiplier = Math.pow(10, 15);
	    break; // peta
	case "E":
	    multiplier = Math.pow(10, 18);
	    break; // exa
	case "Z":
	    multiplier = Math.pow(10, 21);
	    break; // zetta
	case "Y":
	    multiplier = Math.pow(10, 24);
	    break; // yotta
	case "R":
	    multiplier = Math.pow(10, 27);
	    break; // ronna
	case "Q":
	    multiplier = Math.pow(10, 30);
	    break; // quetta
	default:
	    System.err.println("PANIC! " + siPrefix);
	}
	return multiplier;
    }

    private static long startTime, endTime, elapsedTime;

    public static void startTimer() {
	startTime = System.nanoTime();
    }

    public static void endTimer() {
	endTime = System.nanoTime();
	elapsedTime = endTime - startTime;
    }

    public static long getElapsedTime() {
	return elapsedTime;
    }

    public static String getDuration() {
	Duration duration = Duration.ofNanos(elapsedTime);
	StringBuilder sb = new StringBuilder();
	if (duration.toDays() > 0) sb.append(String.format("%02 Day ", duration.toDays()));
	if (duration.toHours() > 0)  sb.append(String.format("%02 Hour ", duration.toHours() % 24));
	if (duration.toHours() > 0)  sb.append(String.format("%02 Minutes ", duration.toMinutes() % 60));
	if (duration.toHours() > 0)  sb.append(String.format("%02 Seconds ", duration.toSeconds() % 60));
	if (duration.toHours() > 0)  sb.append(String.format("%02 Millisecond ", duration.toMillis() % 1000));
	if (duration.toHours() > 0)  sb.append(String.format("%02 Nanosecond ", duration.toNanos() % 1000000L));
	
	return sb.toString().trim();
    }
}
