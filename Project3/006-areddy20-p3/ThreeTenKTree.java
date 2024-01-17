/**
 * This is the ThreeTenKTree class.
 * 
 * @param <E> for use of generics.
 * @author Aditi Reddy.
 */
public class ThreeTenKTree<E> {

	/**
	 * Private variable for the underlying array for k-ary tree storage.
	 */
	private E[] storage;

	/**
	 * Private variable for the hash table.
	 */
	private ThreeTenHashTable<E, Integer> indexMap;

	/**
	 * Private variable for branching.
	 */
	private int branchK;

	/**
	 * Private variable for height of tree.
	 */
	private int treeHeight;

	/**
	 * Private variable for the tree levels.
	 */
	private ThreeTenHashTable<Integer, Integer> treeLevels;

	/**
	 * Method for initializing tree storage as an array of given length and
	 * branching factor as k.
	 * Hash table gets initialized to default table length.
	 * 
	 * @param length for input.
	 * @param k      for input.
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenKTree(int length, int k) {

		this.storage = (E[]) new Object[length];

		this.branchK = k;

		this.treeLevels = new ThreeTenHashTable<>();

		this.treeHeight = -1;

		this.indexMap = new ThreeTenHashTable<>();

	}

	/**
	 * method that reports the branch factor.
	 * 
	 * @return branch factor for output.
	 */
	public int getBranch() {

		return (this.branchK);

	}

	/**
	 * Method that reports the number of non-null nodes in tree.
	 * 
	 * @return size of the index map for output.
	 */
	public int size() {

		return (this.indexMap.size());
	}

	/**
	 * Method that reports the length of storage which is the # of nodes of a
	 * perfect tree of current height.
	 * 
	 * @return length of storage as output.
	 */
	public int capacity() {

		return (this.storage.length);

	}

	/**
	 * Method that reports the height of the tree.
	 * 
	 * @return tree height for output.
	 */
	public int height() {

		return (this.treeHeight);

	}

	/**
	 * Method that sets value at index in tree storage.
	 * Leaf node is removed if value is null.
	 * There is no change to tree of index is invalid or has no node.
	 * 
	 * @param index for input.
	 * @param value for input.
	 * @return true or false of output.
	 */
	@SuppressWarnings("unchecked")
	public boolean set(int index, E value) {

		if (value == null) {

			if (index >= 0 && index < this.capacity()) {

				if (this.storage[index] == null) {

					return (false);

				}

				E node = storage[index];

				if (isLeaf(node)) {

					storage[index] = null;

					indexMap.remove(node);

					return (true);

				}

				return (false);

			}

			else {

				return (false);

			}

		}

		if (value != null) {

			if (this.has(value) == true) {

				return (false);

			}

			if (index >= 0 && index < this.capacity() && this.storage[index] != null) {

				indexMap.remove(this.storage[index]);

				this.storage[index] = value;

				this.indexMap.put(value, index);

				return (true);

			}

			else {

				if (index == 0) {

					this.storage[index] = value;

					this.treeLevels.put(0, 1);

					this.treeHeight = 0;

					this.indexMap.put(value, index);

					return (true);

				}

				else {

					int parentInd = (index - 1) / this.branchK;

					if (index > 0 && parentInd < this.capacity() && this.storage[parentInd] != null) {

						int height = 0;

						int count = 1;

						int tmpIndex = index;

						while (tmpIndex > 0) {

							height += 1;

							tmpIndex = (tmpIndex - 1) / this.branchK;

							count *= this.branchK;

						}

						int level = 0;

						if (treeLevels.get(height) != null) {

							level = treeLevels.get(height);

						}

						if (level == 0 && height == treeHeight + 1) {

							treeHeight++;

						}

						treeLevels.put(height, level + 1);

						if (index >= this.capacity()) {

							E[] newTempStorage = (E[]) new Object[this.storage.length + count];

							for (int i = 0; i < storage.length; i++) {

								newTempStorage[i] = this.storage[i];

							}

							storage = newTempStorage;

						}

						this.storage[index] = value;

						this.indexMap.put(value, index);

						return (true);

					}

					else {

						return (false);

					}

				}

			}

		}

		return (true);

	}

	/**
	 * Method that gets value at node index.
	 * If index is invalid or index has no node, null is returned.
	 * 
	 * @param index for input.
	 * @return value for output.
	 */
	public E get(int index) {

		return (this.storage[index]);

	}

	/**
	 * Method for toString that reports all non null nodes in tree storage.
	 * 
	 * @return string for output.
	 */
	public String toStringLevelOrder() {

		StringBuilder sb = new StringBuilder();

		for (E kayTree : storage) {

			if (kayTree == null) {
				continue;
			}

			if (sb.toString().isEmpty() == false) {

				sb.append(" ");

			}

			sb.append(kayTree);
		}

		return (sb.toString());

	}

	/**
	 * Method that does the same as before method but each level is its own line.
	 * 
	 * @return string for output.
	 */
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		int nextLevel = 1;

		int levelSize = 1;

		for (int i = 0; i < this.capacity(); i++) {

			if (i > 0) {

				if (i == nextLevel) {

					levelSize *= branchK;

					nextLevel += levelSize;

					sb.append("\n");

				}

				else {

					sb.append(" ");

				}

			}

			E kayTree = storage[i];

			if (kayTree == null) {

				sb.append("null");

			}

			else {

				sb.append(kayTree.toString());

			}

		}

		return (sb.toString());

	}

	/**
	 * Method that finds node from given value and reports the ancestors of it as a
	 * string.
	 * If value is not present, null is returned.
	 * 
	 * @param value for input.
	 * @return string or null for output.
	 */
	public String getAncestors(E value) {

		if (value == null) {

			return (null);

		}

		Integer index = indexMap.get(value);

		if (index == null) {

			return (null);

		}

		if (index == 0) {

			return (value.toString());

		}

		StringBuilder sb = new StringBuilder();

		sb.append(value);

		int current = (index - 1) / branchK;

		while (current > 0) {

			sb.insert(0, storage[current].toString() + "-->");

			current = (current - 1) / branchK;

		}

		sb.insert(0, storage[0].toString() + "-->");

		return (sb.toString());

	}

	/**
	 * Method that finds node of given value and reports children of that node as a
	 * string.
	 * If value is not present, null is returned.
	 * If node is a leaf, empty string is returned.
	 * 
	 * @param value for input.
	 * @return string or null for output.
	 */
	public String getChildren(E value) {

		if (value == null) {

			return (null);

		}

		StringBuilder sb = new StringBuilder();

		Integer i = indexMap.get(value);

		if (i == null) {

			return (null);

		}

		for (int j = 0; j < branchK; j++) {

			int childIndex = j + i * branchK + 1;

			if (childIndex >= capacity()) {

				continue;

			}

			if (storage[childIndex] != null) {

				if (!sb.toString().isEmpty()) {

					sb.append(" ");

				}

				sb.append(storage[childIndex].toString());

			}

		}

		return (sb.toString());

	}

	/**
	 * Method that determines if value is in tree or not.
	 * 
	 * @param value for input.
	 * @return true or false for output.
	 */
	public boolean has(E value) {

		if (this.indexMap.get(value) != null) {

			return (true);

		}

		return (false);

	}

	/**
	 * Method that determines if value is in a leaf node of tree or not.
	 * 
	 * @param value for input.
	 * @return true or false for output.
	 */
	public boolean isLeaf(E value) {

		Integer index = indexMap.get(value);

		if (index == null) {

			return (false);

		}

		boolean childPresent = false;

		for (int i = 0; i < branchK; i++) {

			int childIndex = branchK * index + i + 1;

			if (childIndex >= capacity()) {

				break;

			}

			if (this.storage[childIndex] != null) {

				childPresent = true;

				break;

			}

		}

		if (childPresent == true) {

			return (false);

		}

		return (true);

	}

	/**
	 * Method that removes value from tree if value is in a leaf node.
	 * 
	 * @param value for input.
	 * @return true or false for output.
	 */
	public boolean remove(E value) {

		Integer index = indexMap.get(value);

		if (index == null) {

			return (false);

		}

		return (set(index, null));

	}

	/**
	 * Static method that constructs the corresponding irst-child-next-sibling tree.
	 * 
	 * @param <E>   for use of generics.
	 * @param ktree for input.
	 * @return root node for output.
	 */
	public static <E> FcnsTreeNode<E> createFcnsTree(ThreeTenKTree<E> ktree) {

		return constructFcnsTree(ktree, 0);
	}

	/**
	 * Private method that helps with before method.
	 * 
	 * @param <E>   for use of generics.
	 * @param ktree for input.
	 * @param index for input.
	 * @return node or null for output.
	 */
	private static <E> FcnsTreeNode<E> constructFcnsTree(ThreeTenKTree<E> ktree, int index) {

		if (index >= ktree.capacity() || ktree.get(index) == null) {

			return (null);

		}

		FcnsTreeNode<E> fcnsNode = new FcnsTreeNode<>(ktree.get(index));

		FcnsTreeNode<E> tempChild = null;

		FcnsTreeNode<E> currentChild = null;

		for (int i = 0; i < ktree.getBranch(); i++) {

			FcnsTreeNode<E> childNode = constructFcnsTree(ktree, index * ktree.getBranch() + i + 1);

			if (childNode == null) {

				continue;

			}

			if (currentChild == null) {

				tempChild = childNode;

				currentChild = tempChild;

			}

			else {

				currentChild.setSibling(childNode);

				currentChild = childNode;

			}

		}

		fcnsNode.setChild(tempChild);

		return (fcnsNode);
	}

	/**
	 * Main method for testing.
	 * 
	 * @param args for input.
	 */
	public static void main(String[] args) {

		ThreeTenKTree<Integer> t;
		t = new ThreeTenKTree<>(7, 2);

		if (t.set(0, 0) && t.set(2, 2) && t.set(6, 6) && t.set(5, 5) && t.height() == 2
				&& t.size() == 4 && t.getBranch() == 2 && t.capacity() == 7) {
			System.out.println("Yay1");
		}

		if (t.get(0) == 0 && t.get(3) == null
				&& t.toString().equals("0\nnull 2\nnull null 5 6")
				&& t.toStringLevelOrder().equals("0 2 5 6")) {
			System.out.println("Yay2");
		}

		FcnsTreeNode<Integer> node1 = new FcnsTreeNode<>(0);
		FcnsTreeNode<Integer> node2 = new FcnsTreeNode<>(2);
		FcnsTreeNode<Integer> node3 = new FcnsTreeNode<>(5);
		FcnsTreeNode<Integer> node4 = new FcnsTreeNode<>(6);

		node1.setChild(node2);
		node2.setChild(node3);
		node3.setSibling(node4);

		FcnsTreeNode<Integer> myNode = ThreeTenKTree.createFcnsTree(t);

		if (myNode.toStringLevelOrder().equals("0 2 5 6")
				&& myNode.toStringPostOrder().equals("6 5 2 0")
				&& myNode.equals(node1)) {
			System.out.println("Yay3");

		}

		if (myNode.toStringKTreePreOrder().equals("0 2 5 6")
				&& myNode.toStringKTreePostOrder().equals("5 6 2 0")
				&& myNode.toStringKTreeLevelOrder().equals("0 2 5 6")) {
			System.out.println("Yay4");

		}

		if (t.has(6) && !t.has(10) && t.isLeaf(5) && !t.isLeaf(0) && !t.isLeaf(9) &&
				t.getAncestors(6).equals("0-->2-->6") && t.getChildren(2).equals("5 6")) {
			System.out.println("Yay5");
		}

		if (t.set(1, 1) && t.set(11, 11)
				&& !t.set(7, 7)
				&& !t.set(1, 11)
				&& t.height() == 3 && t.capacity() == 15 &&
				t.toString().equals("0\n1 2\nnull null 5 6\nnull null null null 11 null null null")) {
			System.out.println("Yay6");

		}

		if (!t.set(20, null)
				&& !t.set(5, null)
				&& t.set(6, null) && !t.has(6)
				&& t.toString().equals("0\n1 2\nnull null 5 null\nnull null null null 11 null null null")) {
			System.out.println("Yay7");

		}

		if (!t.remove(12) && !t.remove(0) && t.remove(1) && !t.has(1)) {
			System.out.println("Yay8");

		}
	}

}