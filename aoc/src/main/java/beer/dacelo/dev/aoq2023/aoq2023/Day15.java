package beer.dacelo.dev.aoq2023.aoq2023;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import beer.dacelo.dev.aoq2023.generic.ColorUtils;
import beer.dacelo.dev.aoq2023.generic.Day;

public class Day15 extends Day {
    /**
     * Day 15: Bananalytics
     * 
     * After eating reindeer, cars, trees, buildings and the occasional elf, Sedrick
     * has now developed a sweet tooth. That is, if he had teeth. So figuratively he
     * has a sweet tooth, but literally, more of a sweet gelatinous area near his
     * head. If he had a head, which he doesn't because he's a blob. This is going
     * nowhere. Ok he seems to suddenly like sweets. Specifically ice cream!
     * Strangely, rather than just meandering over ice cream shops and engulfing
     * them, he has actually taken to the whole experience and has been buying ice
     * cream. Today he wants banana-flavored ice cream, and we need to figure out
     * where he will buy it.
     * 
     * We know he wants the freshest ice cream, so he will go to a store that sells
     * a lot of banana flavor.
     * 
     * We know he is concerned about finance, so he will go to a store that is
     * increasingly profitable.
     * 
     * We know he doesn't like to wait in line, so he will go to a store with a
     * small average order size.
     * 
     * And we know he is really weird so he will go to a store where the
     * drive-through customers prefer chocolate to strawberry.
     * 
     * We're in the ice cream business now!
     * 
     * We have one month of sales data represented in these tables:
     * 
     * PRICES (LOCATION, FLAVOR, PRICE_PER_SCOOP)
     * 
     * ORDERS (LOCATION, DAY, ORDER_NUMBER, TYPE)
     * 
     * ORDER_DETAILS (LOCATION, DAY, ORDER_NUMBER, FLAVOR, SCOOPS)
     * 
     * PRICES ORDERS ORDER_DETAILS
     * 
     * * Orders are uniquely identifiable by the combination of LOCATION, DAY, and
     * ORDER_NUMBER. (Hint: there will be multiple separate orders with the same
     * ORDER_NUMBER value!) * Each order has one row in ORDERS.csv and one or more
     * in ORDER_DETAILS.csv * There is a different price for each flavor at each
     * location (PRICES.csv) * When you know the correct price, revenue is
     * calculated as SCOOPS * PRICE
     * 
     * Question Out of all the locations where the average order was for less than 3
     * scoops, and where chocolate was more popular than strawberry at the
     * drive-through, and where the revenue for the second half of the month (days
     * 16-30) was greater than the first half, which location sold the most banana
     * ice cream?
     */

    private class Price {
	// LOCATION,FLAVOR,PRICE_PER_SCOOP
	String location, flavor;
	Double pricePerScoop;

	public Price(String input) {
	    String[] i = input.split(",");
	    this.location = i[0].trim();
	    this.flavor = i[1].trim();
	    this.pricePerScoop = Double.parseDouble(i[2]);
	}

	public String toString() {
	    return location + ", " + flavor + ": $" + pricePerScoop;
	}
    }

    private class Order {
	// LOCATION,DAY,ORDER_NUMBER,TYPE
	String location, type;
	Integer day, orderNumber;
	List<OrderDetails> orderDetails;

	public Order(String input) {
	    String[] i = input.split(",");
	    this.location = i[0].trim();
	    this.day = Integer.parseInt(i[1]);
	    this.orderNumber = Integer.parseInt(i[2]);
	    this.type = i[3].trim();
	    orderDetails = new ArrayList<OrderDetails>();
	}

	@Override
	public boolean equals(Object o) {
	    if (o == null || !o.getClass().equals(this.getClass()))
		return false;
	    Order other = (Order) o;
	    return this.location.equals(other.location) && this.day.equals(other.day)
		    && this.orderNumber.equals(other.orderNumber);
	}

	public void addOrderDetails(OrderDetails od) {
	    orderDetails.add(od);
	}

	public String toString() {
	    return location + ", " + day + ", " + orderNumber + ", " + type + ": " + orderDetails;
	}
    }

    private class OrderDetails {
	// LOCATION,DAY,ORDER_NUMBER,FLAVOR,SCOOPS
	String location, flavor;
	Integer day, orderNumber, scoops;

	public OrderDetails(String input) {
	    String[] i = input.split(",");
	    this.location = i[0].trim();
	    this.day = Integer.parseInt(i[1]);
	    this.orderNumber = Integer.parseInt(i[2]);
	    this.flavor = i[3].trim();
	    this.scoops = Integer.parseInt(i[4]);
	}

	@Override
	public boolean equals(Object o) {
	    if (o == null || !o.getClass().equals(this.getClass()))
		return false;
	    Order other = (Order) o;
	    return this.location.equals(other.location) && this.day.equals(other.day)
		    && this.orderNumber.equals(other.orderNumber);
	}

	public String toString() {
	    return location + ", " + day + ", " + orderNumber + ", " + flavor + ": " + scoops;
	}
    }

    private class Location {
	List<Order> orders;
	List<Price> prices;
	List<Double> dailyRevenue = new ArrayList<Double>();

	String name;

	public Location(String name) {
	    this.name = name;
	    this.orders = new ArrayList<Order>();
	    // this.orderDetails = new ArrayList<OrderDetails>();
	    this.prices = new ArrayList<Price>();
	}

	@Override
	public boolean equals(Object o) {
	    if (o == null || !this.getClass().equals(o.getClass()))
		return false;
	    return this.name.equals(((Location) o).name);
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((name == null) ? 0 : name.hashCode());
	    return result;
	}

	public void addOrderDetail(OrderDetails od) {
	    for (Order o : orders) {
		if (o.location.equals(od.location) && o.day.equals(od.day) && o.orderNumber.equals(od.orderNumber)) {
		    // This OrderDetail belongs to this order
		    o.addOrderDetails(od);
		}
	    }
	}

	public double getAverageOrderSize() {
	    // number of orders
	    Double count = 0.0 + orders.size();

	    // total scoops for all orders
	    Double orderSize = 0.0;
	    for (Order o : orders) {
		for (OrderDetails od : o.orderDetails) {
		    orderSize += od.scoops;
		}
	    }
	    return (orderSize / count);
	}

	public boolean isFlavorMorePopularThanOtherFlavorAtType(String flavorOne, String flavorTwo, String type) {
	    Integer countOne = 0, countTwo = 0;
	    for (Order o : orders) {
		if (type.equals(o.type)) {
		    for (OrderDetails od : o.orderDetails) {
			if (flavorOne.equals(od.flavor))
			    countOne += od.scoops;
			if (flavorTwo.equals(od.flavor))
			    countTwo += od.scoops;
		    }
		}
	    }

	    return (countOne > countTwo);
	}

	public Integer getScoops(String flavor) {
	    Integer scoops = 0;
	    for (Order o : orders) {
		for (OrderDetails od : o.orderDetails) {
		    if (flavor.equals(od.flavor))
			scoops += od.scoops;
		}
	    }
	    return scoops;
	}

	public String toString() {
	    return name + ": nPrices: " + prices.size() + ", " + orders;
	}

	public void generateRevenue() {
	    for (Order o : orders) {
		while (dailyRevenue.size() <= o.day) { // we need to go up 1 more, days start at 1
		    dailyRevenue.add(0.0);
		}
		Double revenue = dailyRevenue.get(o.day);
		for (OrderDetails od : o.orderDetails) {
		    revenue += (getPricePerScoop(od.flavor) * od.scoops);
		    dailyRevenue.set(o.day, revenue);
		}
	    }
	}

	public Double getPricePerScoop(String flavor) {
	    for (Price p : prices) {
		if (flavor.equals(p.flavor))
		    return p.pricePerScoop;
	    }
	    return 0.0;
	}

	public boolean hadGreaterSecondHalfRevenue() {
	    if (dailyRevenue.size() == 0)
		generateRevenue();
	    Double firstHalf = 0.0, secondHalf = 0.0;
	    for (int d = 1; d < dailyRevenue.size(); d++) {
		if (d <= 15)
		    firstHalf += dailyRevenue.get(d);
		else
		    secondHalf += dailyRevenue.get(d);
	    }
	    return (secondHalf > firstHalf);
	}

    }

    @Override
    public void solve(int n) {
	File pricesFile = new File(getFilePath().replace("input.txt", "PRICES.csv"));
	File ordersFile = new File(getFilePath().replace("input.txt", "ORDERS.csv"));
	File orderDetailsFile = new File(getFilePath().replace("input.txt", "ORDER_DETAILS.csv"));

	Map<String, Location> locations = new HashMap<String, Location>();

	/* I probably do not need the other three list, but we'll start organised :) */
	List<String> pricesContents = getFileContents(pricesFile);
	for (int i = 1; i < pricesContents.size(); i++) {
	    Price p = new Price(pricesContents.get(i));
	    String name = p.location;
	    Location l = locations.get(name);
	    if (l == null) {
		l = new Location(name);
	    }
	    l.prices.add(p);
	    locations.put(name, l);
	}

	List<String> ordersContents = getFileContents(ordersFile);
	for (int i = 1; i < ordersContents.size(); i++) {
	    Order o = new Order(ordersContents.get(i));
	    String name = o.location;
	    Location l = locations.get(name);
	    if (l == null) {
		l = new Location(name);
	    }
	    l.orders.add(o);
	    locations.put(name, l);
	}

	List<String> orderDetailsContents = getFileContents(orderDetailsFile);
	for (int i = 1; i < orderDetailsContents.size(); i++) {
	    OrderDetails od = new OrderDetails(orderDetailsContents.get(i));
	    String name = od.location;
	    Location l = locations.get(name);
	    if (l == null) {
		l = new Location(name);
	    }
	    l.addOrderDetail(od);
	    locations.put(name, l);
	}

	Integer bananaIcecreamCount = 0;
	Location targetLocation = null;
	for (Location l : locations.values()) {
	    Double averageOrderSize = l.getAverageOrderSize();
	    Boolean chocolateMorePopular = l.isFlavorMorePopularThanOtherFlavorAtType("chocolate", "strawberry",
		    "drive through");
	    // and where chocolate was more popular than strawberry at the drive-through
	    Boolean hadGreaterSecondHalfRevenue = l.hadGreaterSecondHalfRevenue();
	    // and where the revenue for the second half of the month (days 16-30) was
	    // greater than the first half
	    System.out.println(l.name + ", average order: " + averageOrderSize + ", chocolateMorePopular? "
		    + chocolateMorePopular + ", hadGreaterSecondHalfRevenue? " + hadGreaterSecondHalfRevenue);
	    if (averageOrderSize < 3
		    // Out of all the locations where the average order was for less than 3 scoops
		    && chocolateMorePopular && hadGreaterSecondHalfRevenue) {
		Integer bananaScoops = l.getScoops("banana");
		System.out.println(l.name + " has an average order size of: " + averageOrderSize
			+ ", all of the other stuff, and bananaScoops: " + bananaScoops);
		if (bananaScoops > bananaIcecreamCount) {
		    targetLocation = l;
		    bananaIcecreamCount = bananaScoops;
		}
	    }
	}
	/*
	 * , which location sold the most banana ice cream?
	 */
	solver.add("" + targetLocation);
    } // solve
}
