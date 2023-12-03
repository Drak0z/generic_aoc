package beer.dacelo.dev.aoq2023;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import beer.dacelo.dev.aoq2023.generic.Day;
import beer.dacelo.dev.aoq2023.generic.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public abstract class GenericController {
    Map<Integer, String> headers = new HashMap<Integer, String>();
    Map<Integer, Map<String, String>> headerParts = new HashMap<Integer, Map<String, String>>();
    Map<Integer, Map<String, String>> files = new HashMap<Integer, Map<String, String>>();
    Map<String, String> genericFileMap;
    Map<String, String> genericHeaderPartsMap;

    public GenericController() {
	genericFileMap = Map.of("test", "test_input.txt", "test2", "test_input.txt", "part1", "input.txt", "part2",
		"input.txt", "solve",
		"input.txt");
	genericHeaderPartsMap = Map.of("test", "Test Input", "test2", "Test Input 2");

	for (int d = 0; d <= 31; d++) {
	    // let's make sure we have enough variables set for every day of December :-)
	    headers.put(d, "Day " + d + ": Uninitialised");
	    headerParts.put(d, new HashMap<>(Map.of("part1", "Part 1: Uninitialised", "part2", "Part 2: Uninitialised",
		    "solve", "Solve: Uninitialised")));
	    files.put(d, genericFileMap);
	    headerParts.get(d).putAll(genericHeaderPartsMap);
	}

    }

    abstract String getPathPrefix();

    @FXML
    private void switchToPrimary() throws IOException {
	App.setRoot("primary");
    }

    @FXML
    public void genericQuestion(ActionEvent event) {
	int day = Integer.parseInt((String) ((Button) event.getTarget()).getUserData());
	genericQuestion(event, day);
    }

    public void genericQuestion(ActionEvent event, int day) {
	Day question = null;
	try {
	    String className = ("Day" + day).replace("-", "_");
	    question = (Day) Class.forName(getModule() + "." + className).getDeclaredConstructor().newInstance();
	} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
		| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	String partButtonText = ((Button) event.getTarget()).getText();

	int n = 0;
	switch (partButtonText) {
	case "test":
	case "part1":
	    n = 1;
	    break;
	case "test2":
	case "part2":
	    n = 2;
	    break;
	}

	System.out.println("Solving: " + question.getClass());

	String path = getPathPrefix() + question.getClass().getSimpleName().toLowerCase() + "\\"
		+ files.get(day).get(partButtonText);
	String title = "Solved " + question.getClass().getSimpleName();
	String header = headers.get(day) + " " + headerParts.get(day).get(partButtonText);
	question.setInput(path);
	question.solve(n);
	String content = (String.join(System.getProperty("line.separator"), question.getSolution(n))).trim();
	String detail = (String.join(System.getProperty("line.separator"), question.getDetail(n))).trim();
	Util.PopupWindow(title, header, content, detail);
    }

    protected abstract String getModule();
}
