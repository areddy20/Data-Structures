import java.util.Iterator;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.HashMap;

/**
 * PriorityQueue class implemented via the binary heap.
 * From your textbook (Weiss)
 * @param <A> for use of generics.
 */
public class WeissPriorityQueue<A> extends WeissAbstractCollection<A> {

	/**
	 * Private static variable for default capacity.
	 */
	private static final int DEFAULT_CAPACITY = 100;

	/**
	 * Private variable for current size.
	 */
	private int currentSize; // Number of elements in heap

	/**
	 * Private variable for the heap array.
	 */
	private A[] array; // The heap array

	/**
	 * Private variable for cmp.
	 */
	private Comparator<? super A> cmp;

	/**
	 * Private variable for the index map.
	 */
	private HashMap<A, Integer> indexMap = new HashMap<>(); // JCF or your class

	/**
	 * Main method for testing.
	 * 
	 * @param args for input.
	 */
	public static void main(String[] args) {
		
		/**
		 * This is the Student class.
		 */
		class Student {
			String gnum;
			String name;

			/**
			 * Constructor for initializing Student.
			 * @param gnum for input.
			 * @param name for input.
			 */
			Student(String gnum, String name) {
				this.gnum = gnum;
				this.name = name;
			}

			/**
			 * Method that checks equality.
			 * @param o for input.
			 * @return true or false for output.
			 */
			public boolean equals(Object o) {
				if (o instanceof Student)
					return this.gnum.equals(((Student) o).gnum);
				return false;
			}

			/**
			 * Method for toString.
			 * @return string for output.
			 */
			public String toString() {
				return name + "(" + gnum + ")";
			}

			/**
			 * Method for hashCode.
			 * @return hashcode of gnum for output.
			 */
			public int hashCode() {
				return gnum.hashCode();
			}
		}

		Comparator<Student> comp = new Comparator<>() {
			public int compare(Student s1, Student s2) {
				return s1.name.compareTo(s2.name);
			}
		};

		WeissPriorityQueue<Student> q = new WeissPriorityQueue<>(comp);
		q.add(new Student("G00000000", "Robert"));
		System.out.print(q.getIndex(new Student("G00000001", "Cindi")) + " ");
		System.out.print(q.getIndex(new Student("G00000000", "Robert")) + " ");
		System.out.println();

		q.add(new Student("G00000001", "Cindi"));
		System.out.print(q.getIndex(new Student("G00000001", "Cindi")) + " ");
		System.out.print(q.getIndex(new Student("G00000000", "Robert")) + " ");
		System.out.println();

		q.remove();
		System.out.print(q.getIndex(new Student("G00000001", "Cindi")) + " ");
		System.out.print(q.getIndex(new Student("G00000000", "Robert")) + " ");
		System.out.println();
		System.out.println();

		q = new WeissPriorityQueue<>(comp);
		Student s1 = new Student("G00000000", "Robert");
		q.add(s1);
		Student s2 = new Student("G00000001", "Cindi");
		q.add(s2);

		for (Student s : q)
			System.out.print(q.getIndex(s) + " ");
		System.out.println();
		for (Student s : q)
			System.out.print(s.name + " ");
		System.out.println();

		s1.name = "Bobby";
		q.update(s1);

		for (Student s : q)
			System.out.print(q.getIndex(s) + " ");
		System.out.println();
		for (Student s : q)
			System.out.print(s.name + " ");
		System.out.println();

		s1.name = "Robert";
		q.update(s1);

		for (Student s : q)
			System.out.print(q.getIndex(s) + " ");
		System.out.println();
		for (Student s : q)
			System.out.print(s.name + " ");
		System.out.println();

	}

	/**
	 * Method for getting the index of item x in the heap.
	 * 
	 * @param x for input.
	 * @return index for -1 for output.
	 */
	public int getIndex(A x) {

		return (this.indexMap.getOrDefault(x, -1));

	}

	/**
	 * Method that updates or checks whether the location of x still satisfies the
	 * heap order.
	 * 
	 * @param x for input.
	 * @return true or false for output.
	 */
	public boolean update(A x) {

		int nodeIndex = getIndex(x);

		if (nodeIndex < 0) {

			return (false);

		}

		if (nodeIndex <= 1 || compare(x, array[nodeIndex / 2]) >= 0) {

			percolateDown(nodeIndex);

			return (true);
		}

		array[0] = x;

		while (nodeIndex > 1 && compare(x, array[nodeIndex / 2]) < 0) {

			indexMap.put(array[nodeIndex / 2], nodeIndex);

			array[nodeIndex] = array[nodeIndex / 2];

			nodeIndex /= 2;

		}

		array[nodeIndex] = x;

		indexMap.put(x, nodeIndex);

		return (true);

	}

	/**
	 * Construct an empty PriorityQueue.
	 */
	@SuppressWarnings("unchecked")
	public WeissPriorityQueue() {
		currentSize = 0;
		cmp = null;
		array = (A[]) new Object[DEFAULT_CAPACITY + 1];

	}

	/**
	 * Construct an empty PriorityQueue with a specified comparator.
	 * @param c for input.
	 */
	@SuppressWarnings("unchecked")
	public WeissPriorityQueue(Comparator<? super A> c) {
		currentSize = 0;
		cmp = c;
		array = (A[]) new Object[DEFAULT_CAPACITY + 1];

	}

	/**
	 * Construct a PriorityQueue from another Collection.
	 * @param coll for input.
	 */
	@SuppressWarnings("unchecked")
	public WeissPriorityQueue(WeissCollection<? extends A> coll) {
		cmp = null;
		currentSize = coll.size();
		array = (A[]) new Object[(currentSize + 2) * 11 / 10];

		int i = 1;
		for (A item : coll)
			array[i++] = item;
		buildHeap();
	}

	/**
	 * Compares lhs and rhs using comparator if
	 * provided by cmp, or the default comparator.
	 * @param lhs for input.
	 * @param rhs for input.
	 * @return comparator value for output.
	 */
	@SuppressWarnings("unchecked")
	private int compare(A lhs, A rhs) {
		if (cmp == null)
			return ((Comparable) lhs).compareTo(rhs);
		else
			return cmp.compare(lhs, rhs);
	}

	/**
	 * Adds an item to this PriorityQueue.
	 * 
	 * @param x any object.
	 * @return true.
	 */
	public boolean add(A x) {
		if (currentSize + 1 == array.length)
			doubleArray();

		// Percolate up
		int hole = ++currentSize;
		array[0] = x;

		for (; compare(x, array[hole / 2]) < 0; hole /= 2) {
			this.indexMap.put(array[hole / 2], hole);
			array[hole] = array[hole / 2];
		}

		array[hole] = x;
		this.indexMap.put(x, hole);
		return true;
	}

	/**
	 * Returns the number of items in this PriorityQueue.
	 * 
	 * @return the number of items in this PriorityQueue.
	 */
	public int size() {
		return currentSize;
	}

	/**
	 * Make this PriorityQueue empty.
	 */
	public void clear() {
		currentSize = 0;
	}

	/**
	 * Returns an iterator over the elements in this PriorityQueue.
	 * The iterator does not view the elements in any particular order.
	 * @return iterator for output.
	 */
	public Iterator<A> iterator() {
		return new Iterator<A>() {
			int current = 0;

			public boolean hasNext() {
				return current != size();
			}

			@SuppressWarnings("unchecked")
			public A next() {
				if (hasNext())
					return array[++current];
				else
					throw new NoSuchElementException();
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	/**
	 * Returns the smallest item in the priority queue.
	 * 
	 * @return the smallest item.
	 * @throws NoSuchElementException if empty.
	 */
	public A element() {
		if (isEmpty())
			throw new NoSuchElementException();
		return array[1];
	}

	/**
	 * Removes the smallest item in the priority queue.
	 * 
	 * @return the smallest item.
	 * @throws NoSuchElementException if empty.
	 */
	public A remove() {
		A minItem = element();
		this.indexMap.remove(array[1]);
		array[1] = array[currentSize--];
		percolateDown(1);

		return minItem;
	}

	/**
	 * Establish heap order property from an arbitrary
	 * arrangement of items. Runs in linear time.
	 */
	private void buildHeap() {
		for (int i = currentSize / 2; i > 0; i--)
			percolateDown(i);
	}

	/**
	 * Internal method to percolate down in the heap.
	 * 
	 * @param hole the index at which the percolate begins.
	 */
	private void percolateDown(int hole) {
		int child;
		A tmp = array[hole];

		for (; hole * 2 <= currentSize; hole = child) {
			child = hole * 2;
			if (child != currentSize &&
					compare(array[child + 1], array[child]) < 0)
				child++;
			if (compare(array[child], tmp) < 0) {
				this.indexMap.put(array[child], hole);
				array[hole] = array[child];
			} else
				break;
		}
		array[hole] = tmp;
		this.indexMap.put(tmp, hole);
	}

	/**
	 * Internal method to extend array.
	 */
	@SuppressWarnings("unchecked")
	private void doubleArray() {
		A[] newArray;

		newArray = (A[]) new Object[array.length * 2];
		for (int i = 0; i < array.length; i++)
			newArray[i] = array[i];
		array = newArray;
	}
}
