import java.util.Iterator;

/**
 * This is the ThreeTenDLList class.
 * 
 * @param <T> for use of generics.
 * @author Aditi Reddy.
 */

public class ThreeTenDLList<T> implements Iterable<T> {

	/**
	 * Private variable for the beginning of the list.
	 */
	private Node<T> head; // beginning of list

	/**
	 * Private variable for the end of the list.
	 */
	private Node<T> tail;

	/**
	 * Private variable for the total items.
	 */
	private int numItems;

	/**
	 * Constructor that initializes the list to be an empty one.
	 */
	public ThreeTenDLList() {

		this.head = null;

		this.tail = null;

		this.numItems = 0;

	}

	/**
	 * Method that reports the number of items.
	 * 
	 * @return numItems for output.
	 */
	public int numItems() {

		return (this.numItems);

	}

	/**
	 * Method that returns the first value from the beginning of the list.
	 * if list is empty, null is returned.
	 * 
	 * @return head of data or null for output.
	 */
	public T getFirst() {

		if (this.numItems == 0) {

			return (null);

		}

		else {

			return (this.head.getData());

		}

	}

	/**
	 * Method that inserts a new node with value at the begining of the list.
	 * 
	 * @param value for input.
	 */
	public void addFirst(T value) {

		Node<T> newNode = new Node<>(value);

		newNode.setNext(head);

		if (this.numItems == 0) {

			this.tail = newNode;

		}

		else {

			head.setPrev(newNode);

		}

		this.head = newNode;

		this.numItems += 1;

	}

	/**
	 * Method that removes and returns the first value in the list.
	 * If list is empty, then null is returned.
	 * 
	 * @return retValue or null for output.
	 */
	public T removeFirst() {

		if (this.numItems == 0) {

			return (null);

		}

		T retValue = this.head.getData();

		this.head = this.head.getNext();

		if (this.numItems == 1) {

			this.tail = null;

		}

		else {

			this.head.setPrev(null);

		}

		this.numItems -= 1;

		return (retValue);

	}

	/**
	 * Method that returns the last value from the end of the list.
	 * If list is empty, then null is returned.
	 * 
	 * @return the tail of data or null for output.
	 */
	public T getLast() {

		if (this.numItems == 0) {

			return (null);

		}

		else {

			return (this.tail.getData());

		}

	}

	/**
	 * Method that inserts a new value at the end of the list.
	 * 
	 * @param value for input.
	 */
	public void addLast(T value) {

		Node<T> newNode = new Node<>(value);

		newNode.setPrev(tail);

		if (this.numItems == 0) {

			this.head = newNode;

		}

		else {

			this.tail.setNext(newNode);

		}

		this.tail = newNode;

		this.numItems += 1;

	}

	/**
	 * Method that removes and return the last value from the end of the list.
	 * If list is empty, then null is returned.
	 * 
	 * @return retValue or null for output.
	 */
	public T removeLast() {

		if (this.numItems == 0) {
			return (null);
		}

		T retValue = this.tail.getData();

		this.tail = this.tail.getPrev();

		if (this.numItems == 1) {

			this.head = null;

		}

		else {

			this.tail.setNext(null);

		}

		numItems -= 1;

		return (retValue);

	}

	/**
	 * Method that removes and returns the first occurence of value.
	 * If value is not present, null is returned.
	 * 
	 * @param value for input.
	 * @return value or null for output.
	 */
	public T remove(T value) {

		if (this.numItems == 0) {

			return (null);

		}

		if (this.head.getData().equals(value)) {

			return (removeFirst());

		}

		Node<T> curNode = this.head;

		while (curNode.getNext() != null) {

			if (curNode.getNext().getData().equals(value)) {

				T removedNode = curNode.getNext().getData();

				if (curNode.getNext().getNext() != null) {

					curNode.getNext().getNext().setPrev(curNode);

				}

				else {

					this.tail = curNode;

				}

				curNode.setNext(curNode.getNext().getNext());

				numItems -= 1;

				return (removedNode);

			}

			curNode = curNode.getNext();

		}

		return (null);

	}

	/**
	 * Method that return a string representing the values in the list starting from
	 * index to end.
	 * If start is invalid, empty string is returned.
	 * 
	 * @param start for input.
	 * @return string or empty string for output.
	 */
	public String listToString(int start) {

		int index = 0;

		Node<T> curNode = this.head;

		StringBuilder stringValueList = new StringBuilder();

		if (start < 0) {

			return (stringValueList.toString());

		}

		if (curNode == null) {

			return (stringValueList.toString());

		}

		do

		{

			if (stringValueList.toString().isEmpty() == false) {

				stringValueList.append(" ");

			}

			if (index >= start) {

				String value = curNode.getData().toString();

				stringValueList.append(value);

			}

			curNode = curNode.getNext();

			index++;

		} while ((curNode != null));

		return (stringValueList.toString());

	}

	/**
	 * Method that does the same as listToString, but backwards.
	 * 
	 * @return string or empty string for output.
	 */
	public String listToStringBackward() {

		StringBuilder stringValueList = new StringBuilder();

		Node<T> curNode = this.tail;

		while (curNode != null) {

			if (curNode != tail) {

				stringValueList.append(" ");

			}

			String value = curNode.getData().toString();

			stringValueList.append(value);

			curNode = curNode.getPrev();

		}

		return (stringValueList.toString());

	}

	/**
	 * Method that return an iterator that traverses from head to tail.
	 * 
	 * @return iterator for output.
	 */
	public Iterator<T> iterator() {

		return new Iterator<T>() {

			Node<T> curNode = head;

			@Override
			public boolean hasNext() {

				if (curNode == null) {

					return (false);

				}

				else {

					return (true);

				}

			}

			@Override
			public T next() {

				if (curNode == null) {

					throw new NullPointerException("No More Items");

				}

				T itemValue = curNode.getData();

				curNode = curNode.getNext();

				return (itemValue);

			}

		};

	}

	/**
	 * Method that does the same as iterator, but backwards.
	 * 
	 * @return iterator for output.
	 */
	public Iterator<T> backwardIterator() {

		return new Iterator<T>() {

			Node<T> curNode = tail;

			@Override
			public boolean hasNext() {

				if (curNode == null) {

					return (false);

				}

				else {

					return (true);

				}

			}

			@Override
			public T next() {

				if (curNode == null) {

					throw new NullPointerException("No More Items");

				}

				T itemValue = curNode.getData();

				curNode = curNode.getPrev();

				return (itemValue);

			}

		};

	}

	// ******************************************************
	// ******* BELOW THIS LINE IS PROVIDED code *******
	// ******* Do NOT edit code! *******
	// ******* Remember to add JavaDoc *******
	// ******************************************************

	/**
	 * Method for ListToString.
	 * 
	 * @return string for output.
	 */
	public String listToString() {

		return (listToString(0));

	}

	// ******************************************************
	// ******* BELOW THIS LINE IS TESTING CODE *******
	// ******* Edit it as much as you'd like! *******
	// ******* Remember to add JavaDoc *******
	// ******************************************************

	/**
	 * Main method for testing.
	 * 
	 * @param args for input.
	 */
	public static void main(String[] args) {

		ThreeTenDLList<Integer> list = new ThreeTenDLList<>();
		list.addFirst(100);
		list.addFirst(200);
		list.addFirst(300);
		list.addFirst(400);

		// System.out.println(list.listToString());
		// System.out.println(list.listToStringBackward());

		if (list.getFirst() == 400 && list.getLast() == 100 &&
				list.listToString().equals("400 300 200 100")) {
			System.out.println("Yay1");
		}

		list.addLast(500);
		if (list.listToString().equals("400 300 200 100 500")) {
			System.out.println("Yay2");
		}

		ThreeTenDLList<String> states = new ThreeTenDLList<>();
		states.addLast("VA");
		states.addLast("MD");
		states.addLast("NJ");
		states.addLast("WV");
		states.addLast("WA");

		String name1 = states.removeFirst();
		String name2 = states.removeLast();
		if (name1.equals("VA") && name2.equals("WA") &&
				states.listToString().equals("MD NJ WV") &&
				states.listToStringBackward().equals("WV NJ MD")) {
			System.out.println("Yay3");
		}

		ThreeTenDLList<Integer> nums = new ThreeTenDLList<>();
		nums.addLast(10);
		nums.addLast(20);
		nums.addLast(10);
		nums.addLast(30);
		nums.addLast(10);
		nums.addLast(40);
		if (nums.remove(10) == 10 && nums.listToString().equals("20 10 30 10 40")
				&& nums.remove(10) == 10 && nums.listToString().equals("20 30 10 40")
				&& nums.remove(50) == null && nums.remove(40) == 40
				&& nums.listToString().equals("20 30 10")
				&& nums.listToStringBackward().equals("10 30 20")) {
			System.out.println("Yay4");
		}

		int total = 0;
		for (Integer num : nums) {
			total += num;
		}
		if (total == 60) {
			System.out.println("Yay5");
		}

		Iterator<String> iter = states.iterator();
		if (iter.hasNext() && iter.next().equals("MD") &&
				iter.next().equals("NJ") && iter.next().equals("WV")
				&& !iter.hasNext()) {
			System.out.println("Yay6");
		}

		/**
		 * This is the SomeType class.
		 */
		class SomeType {

			/**
			 * Private variable for value.
			 */
			private String value;

			/**
			 * Method for SomeType.
			 * 
			 * @param value for input.
			 */
			public SomeType(String value) {
				this.value = value;
			}

			/**
			 * Method for equals.
			 * 
			 * @param o for input.
			 * @return true or false for output.
			 */
			public boolean equals(Object o) {
				if (!(o instanceof SomeType))
					return false;

				// both null
				if (((SomeType) o).value == null && this.value == null)
					return true;

				// both empty string
				if (((SomeType) o).value.length() == 0 && this.value.length() == 0)
					return true;

				// compare only the leading chars
				return ((SomeType) o).value.charAt(0) == this.value.charAt(0);
			}

			/**
			 * Method for toString.
			 * @return value for output.
			 */
			public String toString() {
				return value;
			}
		}

		SomeType item1 = new SomeType("Apple");
		SomeType item2 = new SomeType("Alligator");
		SomeType item3 = new SomeType("Bee");
		SomeType item4 = new SomeType("Alder");

		ThreeTenDLList<SomeType> items = new ThreeTenDLList<>();
		items.addLast(item1);
		items.addLast(item2);
		items.addLast(item3);

		SomeType deleted = items.remove(item4);
		if (deleted.toString().equals("Apple")) {
			System.out.println("Yay7");
		}

	}
}