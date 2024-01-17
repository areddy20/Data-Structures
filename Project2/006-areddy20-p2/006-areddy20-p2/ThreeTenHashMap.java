import java.util.Iterator;

/**
 * This is the ThreeTenHashMap class.
 * 
 * @param <K> for use of generics.
 * @param <V> for use of generics.
 * @author Aditi Reddy.
 */
public class ThreeTenHashMap<K, V> {

	/**
	 * Private class for Pair.
	 * 
	 * @param <K> for use of generics.
	 * @param <V> for use of generics.
	 */
	private class Pair<K, V> {
		/**
		 * Private variable for key.
		 */
		private K key;

		/**
		 * Private variable for value.
		 */
		private V value;

		/**
		 * Constructor for Pair.
		 * 
		 * @param key   for input.
		 * @param value for input.
		 */
		public Pair(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * Method for getting the key.
		 * 
		 * @return key for output.
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Method for getting the value.
		 * 
		 * @return value for output.
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Method for setting the key.
		 * 
		 * @param key for input.
		 */
		public void setKey(K key) {
			this.key = key;
		}

		/**
		 * Method for setting the value.
		 * 
		 * @param value for input.
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Method for toString.
		 */
		@Override
		public String toString() {
			return "<" + key.toString() + "," + value.toString() + ">";
		}

		/**
		 * Method for checking if keys are equal.
		 * 
		 * @param o for input.
		 * @return true or false for output.
		 */
		@Override
		@SuppressWarnings("unchecked")
		public boolean equals(Object o) {

			if (o instanceof Pair) {
				Pair<K, V> pair = (Pair<K, V>) o;
				return pair.key.equals(key);
			}
			return false;
		}

		/**
		 * Method for hashCode.
		 */
		@Override
		public int hashCode() {

			return key.hashCode();
		}
	}

	/**
	 * Private variable for buckets.
	 */
	private ThreeTenDLList<Pair<K, V>>[] buckets;

	/**
	 * Static variable for default capacity.
	 */
	final static private int DEFAULT_CAPACITY = 11;

	/**
	 * Private variable for the size.
	 */
	private int size;

	/**
	 * Method for ThreeTenHashMap.
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenHashMap() {
		buckets = (ThreeTenDLList<Pair<K, V>>[]) new ThreeTenDLList[DEFAULT_CAPACITY];
		size = 0;
	}

	/**
	 * Method for the size.
	 * 
	 * @return size for output.
	 */
	public int size() {
		return size;
	}

	/**
	 * Private method for capacity.
	 * 
	 * @return length of buckets for output.
	 */
	private int capacity() {
		return buckets.length;
	}

	/**
	 * Private method for getHash.
	 * 
	 * @param key for input
	 * @return hash code for key for output.
	 */
	private int getHash(K key) {
		return Math.abs(key.hashCode());
	}

	/**
	 * Method for toString.
	 * 
	 * @return a string for output.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < buckets.length; i++) {
			ThreeTenDLList<Pair<K, V>> list = buckets[i];
			sb.append("[");
			if (list != null) {
				sb.append(list.listToString());
			}
			sb.append("]");
			if (i != buckets.length - 1)
				sb.append(",");

		}
		return "{" + sb.toString() + "}";
	}

	// ******************************************************
	// ******* BELOW THIS LINE IS YOUR CODE *******
	// ******************************************************

	// ADD PRIVATE METHODS HERE IF NEEDED!
	// YOU CANNOT ADD MORE DATA MEMBERS

	/**
	 * Method for mapping the key to the value in the hashmap.
	 * 
	 * @param key   for input.
	 * @param value for input.
	 */
	public void put(K key, V value) {

		int index = (this.getHash(key) % this.capacity());

		ThreeTenDLList<Pair<K, V>> bucket = buckets[index];

		if (bucket != null) {

			for (Pair<K, V> pair : bucket) {

				if (pair.key.equals(key)) {

					pair.key = key;

					pair.value = value;

					return;

				}

			}

			bucket.addLast(new Pair<>(key, value));

		}

		else {

			this.buckets[index] = new ThreeTenDLList<>();

			Pair<K, V> newPair = new Pair<>(key, value);

			this.buckets[index].addLast(newPair);

			this.size = this.size + 1;

		}

	}

	/**
	 * Method for returning the current mapping of key.
	 * If key is not there or null, then null is returned.
	 * 
	 * @param key for input.
	 * @return the mapping of key ot null for output.
	 */
	public V get(K key) {

		if (key == null) {

			return (null);

		}

		int index = this.getHash(key) % this.capacity();

		ThreeTenDLList<Pair<K, V>> bucket = buckets[index];

		if (bucket != null) {

			for (Pair<K, V> pair : bucket) {

				if (pair.key.equals(key)) {

					return (pair.value);

				}

			}

		}

		return (null);

	}

	/**
	 * Method for returning the current mapping of key from hashmap and deleting it.
	 * If key is not present or null, null is returned.
	 * 
	 * @param key for input.
	 * @return mapping of key or null for output.
	 */
	public V delete(K key) {

		if (key == null) {

			return (null);
		}

		int index = (this.getHash(key) % this.capacity());

		ThreeTenDLList<Pair<K, V>> bucket = buckets[index];

		Pair<K, V> deletedPair = new Pair<>(null, null);

		if (bucket != null) {

			Pair<K, V> newPair = new Pair<>(key, null);

			deletedPair = bucket.remove(newPair);

		}

		if (deletedPair == null) {

			return (null);

		}

		else {

			this.size -= 1;

			return (deletedPair.value);

		}

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
	public static void main(String args[]) {
		ThreeTenHashMap<String, String> map = new ThreeTenHashMap<>();

		map.put("apple", "red");
		map.put("pear", "yellow");
		map.put("eggplant", "purple");

		if (map.get("apple").equals("red") && map.get("eggplant").equals("purple") && map.size() == 3) {
			System.out.println("Yay1");
		}

		map.put("apple", "green");
		if (map.get("apple").equals("green") && map.size() == 3 && map.delete("pear").equals("yellow")
				&& map.size() == 2) {
			System.out.println("Yay2");
		}

		if (map.get("banana") == null && map.delete("pear") == null) {
			System.out.println("Yay3");
		}

		if (map.toString().equals("{[],[],[],[],[],[],[],[],[<eggplant,purple>],[],[<apple,green>]}")) {
			System.out.println("Yay4");
		}
	}

}