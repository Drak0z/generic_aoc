package beer.dacelo.dev.aoq2023.soq2024;

import java.util.ArrayList;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;

public class Day1 extends Day {
	/*-
	Our supercomputer, trying to come across as more human, has decided that it needs a name.
	It has chosen one for himself and is wanting to share it with all of us. It has let us know that it will only responded to any inquiries if this name is used. 
	Feeling that we all don’t appreciate knitting and crocheting enough it has decided that the only way to give us this name is to reveal it in a long diatribe on knitting and crocheting. 
	Also that in order for us to truly appreciate these arts that the paragraph be revealed via a typical knitting pattern.
	
	In order to read out the paragraph you need to extract the letters and read them. Do this as follows:
	
	MsY9 jN&AlM;E$ xI<SX ;F RoESDq
	iHkOsWd wAsRcE5 4YeO2Uw dTgOsD2A?Yx
	Id fALMe 2D*O@IqN@GE FGWO!OFDL
	
	In the first row, you keep only the odd number characters.
	In the second row you keep only the even number characters.
	In the third row you keep only the odd number characters.
	This pattern in the larger message just repeats (digital knitting!).
	
	The deciphered message is:
	
	MY NAME IS FRED
	HOW ARE YOU TODAY
	I AM DOING GOOD
	
	We have been left this rather large message to unknit. My Glorius Name
	*/

	@Override
	public void solve(int n) {
		StringBuilder myGloriousNameSb = new StringBuilder();
		int lineNumber = 0;
		for (String line : getFileContents(input)) {
			lineNumber++;
			for (int i = 0; i < line.length(); i++) {
				if (i % 2 != lineNumber % 2) myGloriousNameSb.append(line.charAt(i));
			}
			myGloriousNameSb.append(System.lineSeparator());
		}
		System.out.println(myGloriousNameSb.toString());
		solver.add(myGloriousNameSb.toString());
	}

	@Override
	public List<String> getSolution(int n) {
		solution = new ArrayList<String>();
		for (int i = 0; i < solver.size(); i++) {
			solution.add(solver.get(i).toString());
		}
		return solution;
	}
};