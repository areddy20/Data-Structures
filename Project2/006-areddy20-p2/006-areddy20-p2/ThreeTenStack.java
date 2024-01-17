import java.util.Iterator;

/**
 * This is the ThreeTenStack class.
 * 
 * @param <T> for use of generics.
 * @author Aditi Reddy.
 */

public class ThreeTenStack<T> {

	/**
	 * Private variable for the storage.
	 */
	private ThreeTenDLList<T> elements;

	/**
	 * Constructor that initializes the ThreeTenStack to being an empty stack.
	 */
	public ThreeTenStack() {

		this.elements = new ThreeTenDLList<>();

	}

	/**
	 * Method that pushes an item onto the ThreeTenStack.
	 * 
	 * @param item for input.
	 */
	public void push(T item) {

		this.elements.addFirst(item);

	}

	/**
	 * Method that pops an item off the ThreeTenStack.
	 * If no items remain, null is returned.
	 * 
	 * @return item that is popped or null for output.
	 */
	public T pop() {

		return (this.elements.removeFirst());

	}

	/**
	 * Method that returns the top of the ThreeTenStack.
	 * If no items remain, return null.
	 * 
	 * @return the top or null for output.
	 */
	public T peek() {

		return (this.elements.getFirst());

	}

	/**
	 * Method that creates a string of the ThreeTenStack.
	 * Each item is separated by a space from bottom to top.
	 * 
	 * @return string for output.
	 */
	public String toString() {

		return (this.elements.listToStringBackward());

	}

	/**
	 * Method that checks if ThreeTenStack is empty.
	 * 
	 * @return true or false for output.
	 */
	public boolean isEmpty() {

		if (this.elements.numItems() == 0) {

			return (true);

		}

		else {

			return (false);

		}

	}

	/**
	 * Method that returns a new stack with all items from this stack in reverse.
	 * 
	 * @return the stack in reverse for output.
	 */
	public ThreeTenStack<T> reverseStack() {

		ThreeTenStack<T> newReverseStack = new ThreeTenStack<>();

		Iterator<T> iterator = elements.iterator();

		while (iterator.hasNext()) {

			newReverseStack.push(iterator.next());

		}

		return (newReverseStack);

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
		ThreeTenStack<String> s = new ThreeTenStack<>();
		s.push("student");
		s.push("help");

		if (!s.isEmpty() && s.peek().equals("help") && s.pop().equals("help")
				&& s.peek().equals("student")) {
			System.out.println("Yay1");
		}

		s.push("support");
		s.push("and");
		s.push("advocacy");
		s.push("center");
		if (s.toString().equals("student support and advocacy center")
				&& !s.isEmpty()) {
			System.out.println("Yay2");
		}

		ThreeTenStack<String> back = s.reverseStack();
		// System.out.println(back);
		// uncomment to check details

		s.pop();
		s.pop();
		s.pop();
		// System.out.println(s);
		if (s.toString().equals("student support") && s.pop().equals("support")
				&& s.pop().equals("student") && s.isEmpty() && s.pop() == null
				&& back.toString().equals("center advocacy and support student")) {
			System.out.println("Yay3");
		}

	}
}