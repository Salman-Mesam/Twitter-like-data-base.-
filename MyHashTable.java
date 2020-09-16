package FinalProject_Template;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class MyHashTable<K, V> implements Iterable<HashPair<K, V>> {
	// num of entries to the table
	private int numEntries;
	// num of buckets
	private int numBuckets;
	// load factor needed to check for rehashing
	private static final double MAX_LOAD_FACTOR = 0.75;
	// ArrayList of buckets. Each bucket is a LinkedList of HashPair
	private ArrayList<LinkedList<HashPair<K, V>>> buckets;

	// constructor
	public MyHashTable(int initialCapacity) {
		numEntries = 0;
		this.buckets = new ArrayList<LinkedList<HashPair<K, V>>>(initialCapacity);
		for (int i = 0; i < initialCapacity; i++) {
			LinkedList<HashPair<K, V>> linkedList = new LinkedList<HashPair<K, V>>();
			buckets.add(i, linkedList);
		}

		
		numBuckets = initialCapacity;
		if(this.numBuckets==0) {
			this.buckets.add(new LinkedList<>());
			numBuckets=1;
		}

	}

	public int size() {
		return this.numEntries;
	}

	public boolean isEmpty() {
		return this.numEntries == 0;
	}

	public int numBuckets() {
		return this.numBuckets;
	}

	/**
	 * Returns the buckets variable. Useful for testing purposes.
	 */
	public ArrayList<LinkedList<HashPair<K, V>>> getBuckets() {
		return this.buckets;
	}

	/**
	 * Given a key, return the bucket position for the key.
	 */
	public int hashFunction(K key) {
		int hashValue = Math.abs(key.hashCode()) % this.numBuckets;
		return hashValue;
	}

	/**
	 * Takes a key and a value as input and adds the corresponding HashPair to this
	 * HashTable. Expected average run time O(1)
	 */
	public V put(K key, V value) {
		V guy = null;
		LinkedList<HashPair<K, V>> weKnow = buckets.get(hashFunction(key));

		for (HashPair<K, V> pair : weKnow) {
			if (key.equals(pair.getKey())) {
				guy = pair.getValue();
				pair.setValue(value);
				return guy;

			}

		}
		HashPair<K, V> element = new HashPair<K, V>(key, value);
		weKnow.add(element);
		numEntries = numEntries + 1;
		if (((double) numEntries / (double) numBuckets) > MAX_LOAD_FACTOR) {
			rehash();

		}

		return null;
	}

	// ADD YOUR CODE ABOVE HERE

	/**
	 * Get the value corresponding to key. Expected average runtime O(1)
	 */

	public V get(K key) {
		V value = null;
		LinkedList<HashPair<K, V>> weKnow = buckets.get(hashFunction(key));

		for (HashPair<K, V> pair : weKnow) { // I am iterating through the linked list of hash map lookinf
			if (key.equals(pair.getKey())) {
				value = pair.getValue();
				return value;

			}
		}
		return null;
	}

	/**
	 * Remove the HashPair corresponding to key . Expected average runtime O(1)
	 */
	public V remove(K key) {
		V valueOfRemovedKey = null;
		LinkedList<HashPair<K, V>> weKnow = buckets.get(hashFunction(key));

		for (HashPair<K, V> pair : weKnow) { // I am iterating through the linked list of hash map lookinf
			if (key.equals(pair.getKey())) {
				valueOfRemovedKey = pair.getValue();
				weKnow.remove(pair);
				numEntries = numEntries - 1;
				return valueOfRemovedKey;

			}
		}

		return null;

	}

	/**
	 * Method to double the size of the hashtable if load factor increases beyond
	 * MAX_LOAD_FACTOR. Made public for ease of testing. Expected average runtime is
	 * O(m), where m is the number of buckets
	 */
	public void rehash() {
		int newInitialCapacity = 2 * numBuckets;

		ArrayList<LinkedList<HashPair<K, V>>> newBuckets = new ArrayList<LinkedList<HashPair<K, V>>>(
				newInitialCapacity);
		for (int i = 0; i < newInitialCapacity; i++) {
			LinkedList<HashPair<K, V>> meriLinkedList = new LinkedList<HashPair<K, V>>();
			newBuckets.add(i, meriLinkedList);
		}

		numBuckets *= 2;// scale to match indices

		for (LinkedList<HashPair<K, V>> temp : buckets) {
			for (HashPair<K, V> temp1 : temp) {
				K key = temp1.getKey();
				newBuckets.get(hashFunction(key)).add(temp1);
			}

		}

		for (int i = 0; i < newBuckets.size(); ++i) {
			LinkedList<HashPair<K, V>> nLL = newBuckets.get(i);
			if (i < buckets.size()) {
				buckets.set(i, nLL);

			} else {
				buckets.add(nLL);
			}
		}

	}

	/**
	 * Return a list of all the keys present in this hashtable. Expected average
	 * runtime is O(m), where m is the number of buckets
	 */

	public ArrayList<K> keys() {
		ArrayList<K> str = new ArrayList<K>();
		for (LinkedList<HashPair<K, V>> variable : buckets) {
			for (HashPair<K, V> temp1 : variable) {
				str.add(temp1.getKey());
			}
		}
		return str;

	}

	/**
	 * Returns an ArrayList of unique values present in this hashtable. Expected
	 * average runtime is O(m) where m is the number of buckets
	 */
	public ArrayList<V> values() {
		/*
		 * ArrayList<V> str2 = new ArrayList<V>(); for (LinkedList<HashPair<K, V>>
		 * variable : buckets) { for (HashPair<K, V> temp1 : variable) {
		 * str2.add(temp1.getValue()); } }
		 */
		MyHashTable<V, Integer> map = new MyHashTable<V, Integer>(buckets.size() * 2);
		for (LinkedList<HashPair<K, V>> variable : buckets) {
			for (HashPair<K, V> temp1 : variable) {
				map.put(temp1.getValue(), 1);
			}

		}

		return map.keys();

	}

	/**
	 * This method takes as input an object of type MyHashTable with values that are
	 * Comparable. It returns an ArrayList containing all the keys from the map,
	 * ordered in descending order based on the values they mapped to.
	 * 
	 * The time complexity for this method is O(n^2), where n is the number of pairs
	 * in the map.
	 */
	public static <K, V extends Comparable<V>> ArrayList<K> slowSort(MyHashTable<K, V> results) {
		ArrayList<K> sortedResults = new ArrayList<>();
		for (HashPair<K, V> entry : results) {
			V element = entry.getValue();
			K toAdd = entry.getKey();
			int i = sortedResults.size() - 1;
			V toCompare = null;
			while (i >= 0) {
				toCompare = results.get(sortedResults.get(i));
				if (element.compareTo(toCompare) <= 0)
					break;
				i--;
			}
			sortedResults.add(i + 1, toAdd);
		}
		return sortedResults;
	}

	/**
	 * This method takes as input an object of type MyHashTable with values that are
	 * Comparable. It returns an ArrayList containing all the keys from the map,
	 * ordered in descending order based on the values they mapped to.
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number of
	 * pairs in the map.
	 */

	public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {

		LinkedList<HashPair<K, V>> linkedListHashPairs = new LinkedList<HashPair<K, V>>();
		for (HashPair<K, V> entry : results) {
			linkedListHashPairs.add(entry);
		}

		LinkedList<HashPair<K, V>> sortedHashPair = mergeSort(linkedListHashPairs);
		ArrayList<K> sortedKeys = new ArrayList<>(sortedHashPair.size());

		for (HashPair<K, V> pair : sortedHashPair) {
			sortedKeys.add(pair.getKey());

		}

		return sortedKeys;

	}

	private static <K, V extends Comparable<V>> LinkedList<HashPair<K, V>> mergeSort(
			LinkedList<HashPair<K, V>> linkedList) {
		if (linkedList.size() == 1 || linkedList.size()==0) {
			return linkedList;
		}

		else {
			int middle = ((linkedList.size()) - 1) / 2;
			LinkedList<HashPair<K, V>> leftList = new LinkedList<HashPair<K, V>>();
			LinkedList<HashPair<K, V>> rightList = new LinkedList<HashPair<K, V>>();
			Iterator<HashPair<K, V>> iterator = linkedList.iterator();
			for (int i = 0; i <= middle; i++) {
				leftList.add(iterator.next());

			}
			for (int i = middle + 1; i <= linkedList.size() - 1; i++) {
				rightList.add(iterator.next());

			}

			leftList = mergeSort(leftList);
			rightList = mergeSort(rightList);

			LinkedList<HashPair<K, V>> hero = merge(leftList, rightList);

			return hero;
		}
	}

	private static <K, V extends Comparable<V>> LinkedList<HashPair<K, V>> merge(LinkedList<HashPair<K, V>> leftList,
			LinkedList<HashPair<K, V>> rightList) {
		LinkedList<HashPair<K, V>> hero = new LinkedList<HashPair<K, V>>();
		while (!leftList.isEmpty() && !rightList.isEmpty()) {
			V leftValue = leftList.getFirst().getValue();
			V rightValue = rightList.getFirst().getValue();
			if (leftValue.compareTo(rightValue) > 0) { // this line decides the ordering
				hero.addLast(leftList.removeFirst());
			} else {
				hero.addLast(rightList.removeFirst());
			}

		}
		while (!leftList.isEmpty()) {
			hero.addLast(leftList.removeFirst());
		}
		while (!rightList.isEmpty()) {
			hero.addLast(rightList.removeFirst());
		}

		return hero;

	}

	@Override
	public MyHashIterator iterator() {
		return new MyHashIterator();
	}

	public class MyHashIterator implements Iterator<HashPair<K, V>> {
		private int index;
		ArrayList<HashPair<K, V>> data;

		/**
		 * Expected average runtime is O(m) where m is the number of buckets
		 */
		private MyHashIterator() {
			data = new ArrayList<HashPair<K, V>>();
			index = 0;
			ArrayList<LinkedList<HashPair<K, V>>> b = buckets;

			for (LinkedList<HashPair<K, V>> l : b) {
				for (HashPair<K, V> tempo : l) {
					data.add(tempo);

				}
			}

		}

		@Override
		/**
		 * Expected average runtime is O(1)
		 */
		public boolean hasNext() {
			if (index < data.size()) {
				return true;
			}

			return false;
		}

		// ADD YOUR CODE ABOVE HERE

		@Override
		/**
		 * Expected average runtime is O(1)
		 */
		public HashPair<K, V> next() {
			if (index < data.size()) {
				HashPair<K, V> element = data.get(index);
				index++;
				return element;
			} else {
				throw new NoSuchElementException("does not exist");
			}

		}

	}
}
