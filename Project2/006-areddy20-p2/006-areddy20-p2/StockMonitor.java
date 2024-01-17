import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * This is the StockMonitor class.
 * 
 * @author Aditi Reddy.
 */
public class StockMonitor {

	/**
	 * Method that opens the file given in a scanner and create a list of prices.
	 * List includes all integer values and is in the order from head to tail.
	 * If the file is empty, an empty list is returned.
	 * 
	 * @param filename for input.
	 * @return list, empty list, or excpetion for output.
	 * @throws IOException for errors.
	 */
	public ThreeTenDLList<Integer> fileToPriceList(String filename) throws IOException {

		ThreeTenDLList<Integer> threeTenTDlist = new ThreeTenDLList<>();

		if (filename == null) {

			return (threeTenTDlist);

		}

		Scanner scanner = new Scanner(new File(filename));

		while (scanner.hasNextInt()) {

			threeTenTDlist.addLast(scanner.nextInt());

		}

		return (threeTenTDlist);

	}

	/**
	 * Method that determines and return the span of the given day and price using
	 * recordStack.
	 * measured span and pair of days and prices are added to the back and top.
	 * priceSpanMap is updated as well.
	 * 
	 * @param day   for input.
	 * @param price for input.
	 * @return the span of day and price for output.
	 */
	public int stepProcess(int day, int price) {

		int spanMeasurement;

		if (day == 0) {

			StackItem stackItem = new StackItem(day, price);

			recordStack.push(stackItem);

			spanMeasurement = 1;

		}

		else {

			while (!recordStack.isEmpty()) {

				StackItem topStock = recordStack.peek();

				if (topStock.getPrice() > price) {

					break;

				}

				recordStack.pop();

			}

			if (recordStack.isEmpty()) {

				StackItem stackItem = new StackItem(day, price);

				recordStack.push(stackItem);

				spanMeasurement = (day + 1);
			}

			else {

				spanMeasurement = (day - recordStack.peek().getDay());

				StackItem stackItem = new StackItem(day, price);

				recordStack.push(stackItem);

			}

		}

		if (spanMeasurement > reportMaxSpan(price)) {

			priceSpanMap.put(price, spanMeasurement);

		}

		spanList.addLast(spanMeasurement);

		return (spanMeasurement);

	}

	/**
	 * Method that uses priceMap to find out and return the current max span for the
	 * given price.
	 * If priceMap doesn't contain the given price, 0 is returned.
	 * 
	 * @param price for input.
	 * @return the current max of span for output.
	 */
	public int reportMaxSpan(int price) {

		Integer maximumSpan = priceSpanMap.get(price);

		if (maximumSpan == null) {

			return (0);

		}

		else {

			return (maximumSpan);

		}

	}

	/**
	 * Method that returns a string to report the max span of each price in
	 * priceList.
	 * If priceList is empty, an empty string is returned.
	 * 
	 * @return string of max span or 0 for output.
	 */
	public String spanRecordToString() {

		StringBuilder maxSpanString = new StringBuilder();

		for (int price : priceList) {

			Integer maximumSpan = priceSpanMap.delete(price);

			if (maximumSpan != null) {

				if (maxSpanString.toString().isEmpty() == false) {

					maxSpanString.append(" ");

				}

				maxSpanString.append(price).append(":").append(maximumSpan);

			}

		}

		return (maxSpanString.toString());

	}

	/**
	 * Tester method without arguments.
	 */
	public static void testMain() {
		// edit this as much as you want, if you use main without any arguments,
		// this is the method that will be run instead of the program
		System.out.println("You need to put test code in testMain() to run StockMonitor with no parameters.");
	}

	// ADD PRIVATE METHODS HERE IF NEEDED!
	// YOU CANNOT ADD MORE DATA MEMBERS

	// ******************************************************
	// ******* BELOW THIS LINE IS PROVIDED code *******
	// ******* Do NOT edit code! *******
	// ******* Remember to add JavaDoc *******
	// ******************************************************

	/**
	 * Private class for StackItem.
	 */
	private class StackItem {

		/**
		 * Private variable for day.
		 */
		private int day;

		/**
		 * Private variable for price.
		 */
		private int price;

		/**
		 * Constructor for StackItem.
		 * 
		 * @param day   for input.
		 * @param price for input.
		 */
		public StackItem(int day, int price) {
			this.day = day;
			this.price = price;
		}

		/**
		 * Method to get the day.
		 * 
		 * @return day for output.
		 */
		public int getDay() {
			return day;
		}

		/**
		 * Method to get the price.
		 * 
		 * @return price for output.
		 */
		public int getPrice() {
			return price;
		}

		/**
		 * Method for toString.
		 * 
		 * @return string for output.
		 */
		public String toString() {
			return "<" + day + "," + price + ">";
		}

	}

	/**
	 * Private variable for the priceList.
	 */
	private ThreeTenDLList<Integer> priceList;

	/**
	 * Private variable for the spanList.
	 */
	private ThreeTenDLList<Integer> spanList;

	/**
	 * Private variable for the recordStack.
	 */
	private ThreeTenStack<StackItem> recordStack;

	/**
	 * Private variable for priceSpanMap.
	 */
	private ThreeTenHashMap<Integer, Integer> priceSpanMap;

	/**
	 * Main method for non editing testing.
	 * 
	 * @param args for input.
	 */
	public static void main(String[] args) {

		if (args.length == 0) {
			testMain();
			return;
		}

		if (args.length == 1 || (args.length == 2 && args[1].equals("-d"))) {

			try {
				(new StockMonitor()).runProgram(args[0],
						args.length == 2 && args[1].equals("-d"));
			} catch (IOException e) {
				System.out.println(e.toString());
				e.printStackTrace();
			}

		}

		else {
			System.out.println("Usage: java StockMonitor [filename] [-d]");
		}

	}

	/**
	 * Method for runProgram.
	 * 
	 * @param filename for input.
	 * @param debug    for input.
	 * @throws IOException for errors.
	 */
	public void runProgram(String filename, boolean debug) throws IOException {

		priceList = fileToPriceList(filename);

		spanList = new ThreeTenDLList<>();

		recordStack = new ThreeTenStack<>();

		priceSpanMap = new ThreeTenHashMap<>();

		System.out.println("Prices: \t" + priceList.listToString());

		int day = 0;
		if (!debug) {
			for (int price : priceList) {
				stepProcess(day, price);
				day++;
			}
		} else {
			Scanner s = new Scanner(System.in);
			int span;
			for (int price : priceList) {
				System.out.println("\n######### Step " + day + " ###############\n");
				System.out.println("----------Step Output----------");
				span = stepProcess(day, price);
				System.out.format("Day = %d, Price = %d, Span = %d\n", day, price, span);
				System.out.println("--------------------------------");
				System.out.println("--Record Stack (bottom to top)--");
				System.out.println(recordStack);
				System.out.println("-------------Spans-------------");
				System.out.println(spanList.listToString());
				if (priceList.numItems() > 0) {
					System.out.println("----------Prices Remaining----");
					System.out.println(priceList.listToString(day + 1));
				}
				// System.out.println("Current max spans:\t"+ spanRecordToString());
				// uncomment for debugging needs
				System.out.println("\nPress Enter to Continue");
				s.nextLine();
				day++;
			}
			System.out.println("Prices:\t\t" + priceList.listToString());

		}
		System.out.println("Spans:\t\t" + spanList.listToString());
		System.out.println("Max spans:\t" + spanRecordToString());

	}

}
