/**
 * This is the ThreeTenHashTable class.
 * 
 * @param <K> for use of generics.
 * @param <V> for use of generics.
 * @author Aditi Reddy.
 */
public class ThreeTenHashTable<K, V> {

	/**
	 * Private class for TableEntry.
	 * 
	 * @param <K> for use of generics.
	 * @param <V> for use of generics.
	 */
	private class TableEntry<K, V> {

		/**
		 * Private variable for key.
		 */
		private K key;

		/**
		 * Private variable for value.
		 */
		private V value;

		/**
		 * Method for initializing TableEntry.
		 * 
		 * @param key   for input.
		 * @param value for input.
		 */
		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * Method for getting key.
		 * 
		 * @return key for output.
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Method foe getting value.
		 * 
		 * @return value for output.
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Method for toString.
		 * 
		 * @return string for output.
		 */
		public String toString() {
			return key.toString() + ":" + value.toString();
		}
	}

	/**
	 * Private variable for the underlying array for hash table storage.
	 */
	private TableEntry<K, V>[] storage;

	/**
	 * Static variable for the default table length.
	 */
	public static int defaultTableLength = 10;

	/**
	 * Method for toString.
	 * 
	 * @return string for output.
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < storage.length; i++) {
			if (storage[i] != null && !isTombstone(i)) {
				s.append(storage[i] + "\n");
			}
		}
		return s.toString().trim();
	}

	/**
	 * Method for debugging toString.
	 * 
	 * @return string for output.
	 */
	public String toStringDebug() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < storage.length; i++) {
			if (!isTombstone(i)) {
				s.append("[" + i + "]: " + storage[i] + "\n");
			} else {
				s.append("[" + i + "]: tombstone\n");
			}

		}
		return s.toString().trim();
	}

	/**
	 * Private variable for checking.
	 */
	private boolean[] tombstones;

	/**
	 * Private variable for size.
	 */
	private int numOfElements;

	/**
	 * Method to create a hash table with capacity as initCapacity.
	 * 
	 * @param initCapacity for input.
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenHashTable(int initCapacity) {

		this.storage = new TableEntry[initCapacity];

		this.tombstones = new boolean[initCapacity];

		this.numOfElements = 0;

	}

	/**
	 * Method that creates a hash table with storage as defaultTableLength.
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenHashTable() {

		this.storage = new TableEntry[defaultTableLength];

		this.tombstones = new boolean[defaultTableLength];

		this.numOfElements = 0;

	}

	/**
	 * Method that shows how big the storage is.
	 * 
	 * @return storage length for output.
	 */
	public int capacity() {

		return (this.storage.length);

	}

	/**
	 * Method that shows the number of elements in the table.
	 * 
	 * @return number of elements which is the size as output.
	 */
	public int size() {

		return (this.numOfElements);

	}

	/**
	 * Method that places val at the location determined by key.
	 * Uses linear probing for collisions.
	 * 
	 * @param key for input.
	 * @param val for input.
	 * @return true or false for output.
	 */
	public boolean put(K key, V val) {

		if (key == null || val == null) {

			return (false);

		}

		int hashKey = (key.hashCode() % this.storage.length);

		for (;;) {

			if ((storage[hashKey] == null && tombstones[hashKey] == false)
					|| (tombstones[hashKey] == true && this.storage[(hashKey + 1) % this.storage.length] == null
							&& tombstones[(hashKey + 1) % this.storage.length] == false)) {

				this.storage[hashKey] = new TableEntry<>(key, val);

				tombstones[hashKey] = false;

				numOfElements += 1;

				break;

			}

			if (tombstones[hashKey] == false && this.storage[hashKey].key.equals(key)) {

				this.storage[hashKey].value = val;

				break;

			}

			hashKey = (hashKey + 1) % this.storage.length;

		}

		if ((float) numOfElements / capacity() >= 0.8) {

			rehash(2 * capacity());

		}

		return (true);

	}

	/**
	 * Method for getting the value that the key maps to from table.
	 * 
	 * @param key for input.
	 * @return value or null for output.
	 */
	public V get(K key) {

		if (key == null) {

			return (null);

		}

		int hashKey = (key.hashCode() % this.storage.length);

		while (this.storage[hashKey] != null || tombstones[hashKey] == true) {

			if (tombstones[hashKey] == false && this.storage[hashKey].key.equals(key)) {

				return (this.storage[hashKey].value);
			}

			hashKey = (hashKey + 1) % this.storage.length;
		}

		return (null);

	}

	/**
	 * Method that helps with printing.
	 * 
	 * @param loc for input.
	 * @return true or false for output.
	 */
	public boolean isTombstone(int loc) {

		return (this.tombstones[loc]);

	}

	/**
	 * Method that increases or decreases the capacity of the storage to be newCap.
	 * All key value pairs also get rehashed in this process.
	 * If the new capacity >= 0.8, then no rehash is done
	 * 
	 * @param newCap for input.
	 * @return true or false for output.
	 */
	@SuppressWarnings("unchecked")
	public boolean rehash(int newCap) {

		if ((float) numOfElements / newCap >= 0.8) {

			return (false);

		}

		TableEntry<K, V>[] tempTable = this.storage;

		this.storage = new TableEntry[newCap];

		this.tombstones = new boolean[newCap];

		this.numOfElements = 0;

		for (int i = 0; i < tempTable.length; i++) {

			TableEntry<K, V> tableEntry = tempTable[i];

			if (tableEntry != null) {

				put(tableEntry.key, tableEntry.value);

			}

		}

		return (true);

	}

	/**
	 * Method that removes given key and value pair from table.
	 * If key is not present, null is returned.
	 * 
	 * @param key for input.
	 * @return value or null for input.
	 */
	public V remove(K key) {

		if (key == null) {

			return (null);

		}

		int hashKey = (key.hashCode() % this.storage.length);

		while (tombstones[hashKey] == true || this.storage[hashKey] != null) {

			if (!tombstones[hashKey] && this.storage[hashKey].key.equals(key)) {

				tombstones[hashKey] = true;

				V keyVal = this.storage[hashKey].value;

				this.storage[hashKey] = null;

				this.numOfElements -= 1;

				return (keyVal);
			}

			hashKey = (hashKey + 1) % this.storage.length;
		}

		return (null);
	}

	/**
	 * Main method for testing.
	 * 
	 * @param args for input.
	 */
	public static void main(String[] args) {

		ThreeTenHashTable<Integer, Character> ht1 = new ThreeTenHashTable<>(5);

		if (ht1.capacity() == 5 && ht1.size() == 0 && ht1.put(1, 'A')
				&& ht1.put(2, 'B') && ht1.size() == 2 && ht1.get(1).equals('A')
				&& ht1.get(2).equals('B')) {
			System.out.println("Yay 1");
		}

		if (ht1.put(1, 'Z') && ht1.size() == 2 && ht1.get(1).equals('Z')
				&& ht1.toString().equals("1:Z\n2:B")
				&& ht1.toStringDebug().equals("[0]: null\n[1]: 1:Z\n[2]: 2:B\n[3]: null\n[4]: null")) {
			System.out.println("Yay 2");
		}

		if (ht1.put(7, 'S') && ht1.get(7).equals('S')
				&& ht1.toStringDebug().equals("[0]: null\n[1]: 1:Z\n[2]: 2:B\n[3]: 7:S\n[4]: null")) {
			System.out.println("Yay 3");
		}

		if (ht1.put(5, 'F') && ht1.capacity() == 10
				&& ht1.toString().equals("1:Z\n2:B\n5:F\n7:S")
				&& ht1.toStringDebug().equals(
						"[0]: null\n[1]: 1:Z\n[2]: 2:B\n[3]: null\n[4]: null\n[5]: 5:F\n[6]: null\n[7]: 7:S\n[8]: null\n[9]: null")) {
			System.out.println("Yay 4");
		}

		if (ht1.remove(2).equals('B') && ht1.remove(5).equals('F') && ht1.put(11, 'E') &&
				ht1.toStringDebug().equals(
						"[0]: null\n[1]: 1:Z\n[2]: 11:E\n[3]: null\n[4]: null\n[5]: tombstone\n[6]: null\n[7]: 7:S\n[8]: null\n[9]: null")) {
			System.out.println("Yay 5");
		}

		if (!ht1.rehash(2) && !ht1.rehash(3) && ht1.rehash(6) && ht1.capacity() == 6 &&
				ht1.toString().equals("1:Z\n7:S\n11:E") &&
				ht1.toStringDebug().equals("[0]: null\n[1]: 1:Z\n[2]: 7:S\n[3]: null\n[4]: null\n[5]: 11:E")) {
			System.out.println("Yay 6");
		}

	}

}