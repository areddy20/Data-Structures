/**
 * This is the FcnsTreeNode class.
 * 
 * @param <E> for use of generics.
 * @author Aditi Reddy.
 */
public class FcnsTreeNode<E> {

	/**
	 * Private variable for value.
	 */
	private E value;

	/**
	 * Private variable for the first child.
	 */
	private FcnsTreeNode<E> firstChild;

	/**
	 * Private variable for the next sibling.
	 */
	private FcnsTreeNode<E> nextSibling;

	/**
	 * Method for initialzing the Fcns tree node.
	 * 
	 * @param value for input.
	 */
	public FcnsTreeNode(E value) {
		this.value = value;
		firstChild = null;
		nextSibling = null;
	}

	/**
	 * Method for getting value.
	 * 
	 * @return value for output.
	 */
	public E getValue() {
		return this.value;
	}

	/**
	 * Method for setting child.
	 * 
	 * @param node for input.
	 */
	public void setChild(FcnsTreeNode<E> node) {
		this.firstChild = node;

	}

	/**
	 * Method for setting sibling.
	 * 
	 * @param node for input.
	 */
	public void setSibling(FcnsTreeNode<E> node) {
		this.nextSibling = node;
	}

	/**
	 * Method for getting the child.
	 * 
	 * @return first child for output.
	 */
	public FcnsTreeNode<E> getChild() {
		return this.firstChild;
	}

	/**
	 * Method for getting the sibling.
	 * 
	 * @return next sibling for output.
	 */
	public FcnsTreeNode<E> getSibling() {
		return this.nextSibling;
	}

	/**
	 * Method for toString.
	 * 
	 * @return string for output.
	 */
	@Override
	public String toString() {
		return value.toString();
	}

	/**
	 * Method for comparing two nodes.
	 * 
	 * @param another for input.
	 * @return true or false for output.
	 */
	public boolean equals(FcnsTreeNode<E> another) {
		if (another == null)
			return false;

		if (!this.value.equals(another.getValue()))
			return false;

		if (this.firstChild == null) {
			if (another.firstChild != null)
				return false;
		} else if (!this.firstChild.equals(another.getChild()))
			return false;

		if (this.nextSibling == null) {
			if (another.nextSibling != null)
				return false;
		} else if (!this.nextSibling.equals(another.getSibling()))
			return false;

		return true;

	}

	/**
	 * Method that reports the number of nodes in the tree rooted at this node.
	 * 
	 * @return number of nodes for output.
	 */
	public int size() {

		int numOfNodes = 1;

		FcnsTreeNode<E> childFcns = firstChild;

		while (childFcns != null) {

			numOfNodes = numOfNodes + childFcns.size();

			childFcns = childFcns.nextSibling;

		}

		return (numOfNodes);
	}

	/**
	 * Methpd that reports the the minimum k value.
	 * 
	 * @return 0 or min value for output.
	 */
	public int minK() {

		if (this.firstChild == null) {

			return (0);

		}

		FcnsTreeNode<E> childFcns = this.firstChild;

		int minKvalue = 0;

		while (childFcns != null) {

			childFcns = childFcns.nextSibling;

			minKvalue++;

		}

		return (minKvalue);

	}

	/**
	 * Method for toString that includes all nodes in level order.
	 * 
	 * @return string for output.
	 */
	public String toStringLevelOrder() {

		Queue<FcnsTreeNode<E>> queue = new Queue<>();

		queue.enqueue(this);

		StringBuilder sb = new StringBuilder();

		while (queue.isEmpty() == false) {

			if (sb.toString().isEmpty() == false) {

				sb.append(" ");
			}

			FcnsTreeNode<E> fcnsNode = queue.dequeue();

			if (fcnsNode == null) {

				break;

			}

			if (fcnsNode.firstChild != null) {

				queue.enqueue(fcnsNode.firstChild);

			}

			if (fcnsNode.nextSibling != null) {

				queue.enqueue(fcnsNode.nextSibling);

			}

			sb.append(fcnsNode.value);

		}

		return (sb.toString());

	}

	/**
	 * Method that does the same as before method but in post order.
	 * 
	 * @return string as output.
	 */
	public String toStringPostOrder() {

		StringBuilder sb = new StringBuilder();

		toStringPostOrder(this, sb);

		return sb.toString();

	}

	/**
	 * Private method that helps with post order toString method.
	 * 
	 * @param fcnsNode for input.
	 * @param sb       for input.
	 */
	private void toStringPostOrder(FcnsTreeNode<E> fcnsNode, StringBuilder sb) {

		if (fcnsNode == null) {

			return;

		}

		toStringPostOrder(fcnsNode.firstChild, sb);

		toStringPostOrder(fcnsNode.nextSibling, sb);

		if (sb.toString().isEmpty() == false) {

			sb.append(" ");

		}

		sb.append(fcnsNode.value);

	}

	/**
	 * Method that does same as before but in pre order.
	 * 
	 * @return string for output.
	 */
	public String toStringKTreePreOrder() {

		StringBuilder sb = new StringBuilder();

		toStringKTreePreOrder(this, sb);

		return sb.toString();
	}

	/**
	 * Private method that helps with pre order toString method.
	 * 
	 * @param fcnsNode for input.
	 * @param sb       for input.
	 */
	private void toStringKTreePreOrder(FcnsTreeNode<E> fcnsNode, StringBuilder sb) {

		if (fcnsNode == null) {

			return;

		}

		if (sb.toString().isEmpty() == false) {

			sb.append(" ");

		}

		sb.append(fcnsNode.value);

		FcnsTreeNode<E> currentNode = fcnsNode.firstChild;

		while (currentNode != null) {

			toStringKTreePreOrder(currentNode, sb);

			currentNode = currentNode.nextSibling;

		}

	}

	/**
	 * Method for post order toString.
	 * 
	 * @return string for output.
	 */
	public String toStringKTreePostOrder() {

		StringBuilder sb = new StringBuilder();

		toStringKTreePostOrder(this, sb);

		return (sb.toString());

	}

	/**
	 * Private method that helps with post order toString method.
	 * 
	 * @param fcnsNode for input.
	 * @param sb       for input.
	 */
	private void toStringKTreePostOrder(FcnsTreeNode<E> fcnsNode, StringBuilder sb) {

		if (fcnsNode == null) {

			return;

		}

		FcnsTreeNode<E> currentNode = fcnsNode.firstChild;

		while (currentNode != null) {

			toStringKTreePostOrder(currentNode, sb);

			currentNode = currentNode.nextSibling;

		}

		if (sb.toString().isEmpty() == false) {

			sb.append(" ");

		}

		sb.append(fcnsNode.value);

	}

	/**
	 * Method for level order for toString.
	 * 
	 * @return string for output.
	 */
	public String toStringKTreeLevelOrder() {

		Queue<FcnsTreeNode<E>> queue = new Queue<>();

		queue.enqueue(this);

		StringBuilder sb = new StringBuilder();

		while (queue.isEmpty() == false) {

			if (sb.toString().isEmpty() == false) {

				sb.append(" ");

			}

			FcnsTreeNode<E> fcnsNode = queue.dequeue();

			if (fcnsNode == null) {

				break;

			}

			sb.append(fcnsNode.value);

			FcnsTreeNode<E> currentNode = fcnsNode.firstChild;

			while (currentNode != null) {

				queue.enqueue(currentNode);

				currentNode = currentNode.nextSibling;

			}

		}

		return (sb.toString());

	}

	/**
	 * Main method for testing.
	 * 
	 * @param args for input.
	 */
	public static void main(String[] args) {

		FcnsTreeNode<String> node1 = new FcnsTreeNode<>("A");
		FcnsTreeNode<String> node2 = new FcnsTreeNode<>("B");
		FcnsTreeNode<String> node3 = new FcnsTreeNode<>("C");
		FcnsTreeNode<String> node4 = new FcnsTreeNode<>("D");
		FcnsTreeNode<String> node5 = new FcnsTreeNode<>("F");
		FcnsTreeNode<String> node6 = new FcnsTreeNode<>("G");

		node1.setChild(node2);
		node2.setChild(node5);
		node2.setSibling(node3);
		node5.setSibling(node6);
		node3.setSibling(node4);

		if (node1.size() == 6 && node1.minK() == 3) {
			System.out.println("Yay1");
		}

		if (node1.toStringLevelOrder().equals("A B F C G D") &&
				node1.toStringPostOrder().equals("G F D C B A")) {
			System.out.println("Yay2");
		}

		if (node1.toStringKTreeLevelOrder().equals("A B C D F G") &&
				node1.toStringKTreePreOrder().equals("A B F G C D") &&
				node1.toStringKTreePostOrder().equals("F G B C D A")) {
			System.out.println("Yay3");
		}

	}

	/**
	 * Private class for Queue.
	 * 
	 * @param <T> for use of generics.
	 */
	private class Queue<T> {

		/**
		 * Private variable for the front.
		 */
		private Node<T> front;

		/**
		 * Private variable for the back.
		 */
		private Node<T> back;

		/**
		 * Private class for Node.
		 * 
		 * @param <T> for use of generics.
		 */
		private class Node<T> {

			/**
			 * Private variable for value.
			 */
			private T value;

			/**
			 * Private variable for next.
			 */
			private Node<T> next;

			/**
			 * Method to initialize Node.
			 * 
			 * @param x for input.
			 */
			public Node(T x) {
				value = x;
				next = null;
			}
		}

		/**
		 * Empty constructor for Queue.
		 */
		public Queue() {

		}

		/**
		 * Method for checking if front is empty.
		 * 
		 * @return true or false for output.
		 */
		public boolean isEmpty() {

			return front == null;

		}

		/**
		 * Method for enqueue.
		 * 
		 * @param x for input.
		 */
		public void enqueue(T x) {
			Node<T> node = new Node<>(x);
			if (back == null) {
				front = node;
				back = node;
			} else {
				back.next = node;
				back = node;
			}
		}

		/**
		 * Method for dequeue.
		 * 
		 * @return result or null for output.
		 */
		public T dequeue() {
			if (front == null) {
				return null;
			}
			T result = front.value;
			front = front.next;
			if (front == null) {
				back = null;
			}
			return result;
		}

	}

}
