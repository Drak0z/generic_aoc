package beer.dacelo.dev.aoq2023.aoq2023;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beer.dacelo.dev.aoq2023.generic.Day;
import beer.dacelo.dev.aoq2023.generic.Util;

public class Day2 extends Day {
    /**
     * Ok, everyone! You're never going to believe this, but we've found a
     * correlation between Sedrick's feeding times and prime numbers! I know! What
     * are the odds? I'm not sure, but expressed as a percent, I would bet dollars
     * to doughnuts it's prime as well!
     * 
     * It turns out Sedrick only eats on the minutes of the day that are prime
     * numbers.
     * 
     * 12:00 AM is minute 1 1 is not a prime number so he would not eat then 12:01
     * AM is minute 2 2 is a prime number so he would eat then 12:02 AM is minute 3
     * 3 is prime so he would eat then Given this pattern, how many times in a day
     * will Sedrick eat?
     */
    List<Integer> solver = new ArrayList<Integer>();
    
    private String toTime(int m) {
	String date24h = String.format("%02d", (m/60)) + ":" + String.format("%02d", (m%60));
	SimpleDateFormat formatAmPm = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat format24H = new SimpleDateFormat("HH:mm");
        Date date = null;
	try {
	    date = format24H.parse(date24h);
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
        return "(24H: " + format24H.format(date) + ", AM/PM: " + formatAmPm.format(date) + ")";
    }
    

    @Override
    public void solve(int n) {
	Integer result = 0;
	Integer minutesInADay = 60*24;
	for (Integer m = 0; m < minutesInADay; m++) {
	    Boolean isPrime = Util.isPrime(m+1);
	    if (isPrime) result ++;
	    detail.add(toTime(m) + " : Minute: " + (m+1) + " is Prime? " + isPrime + "." + (isPrime ? " We eat!": "") + " Result: " + result);
	}	
	solver.add(result);
    }

    @Override
    public List<String> getSolution(int n) {
	solution = new ArrayList<String>();
	solution.add(Integer.toString(solver.get(0)));
	return solution;
    }
}
