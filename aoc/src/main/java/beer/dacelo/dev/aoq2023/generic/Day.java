package beer.dacelo.dev.aoq2023.generic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Day {
    protected File input;
    protected String filePath;
    protected List<String> solution = new ArrayList<String>();
    protected List<String> detail = new ArrayList<String>();

    public void setInput(String input) {
	this.filePath = input;
	File f = new File(input);
	this.input = f; 
    }
    

    protected List<String> getFileContents(File input) {
	List<String> fileContents = new ArrayList<String>();
	try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
		String line;
	    	while ((line = reader.readLine()) != null) {
	    	    fileContents.add(line);
	    	}
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return fileContents;
    }
    
    public String getFilePath() {
	return this.filePath;
    }
    
    public abstract void solve(int n);    
    public List<String> getSolution(int n) { return solution; }
    public List<String> getDetail(int n) { return detail; }
}
