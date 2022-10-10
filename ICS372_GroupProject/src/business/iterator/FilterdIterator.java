package business.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import Buissness.Entities.Transactions;

public class FilterdIterator implements Iterator<Transactions> {

	private Transactions item;
	private Predicate<Transactions> predicate;
	private Iterator<Transactions> iterator;

	/**
	 * Sets the iterator and predicate fields and positions to the first item that
	 * satisfies the predicate.
	 * 
	 * @param iterator  the iterator to the list
	 * @param predicate specifies the test
	 */
	public FilterdIterator(Iterator<Transactions> iterator, Predicate<Transactions> predicate) {
		this.predicate = predicate;
		this.iterator = iterator;
		getNextItem();
	}

	@Override
	public boolean hasNext() {
		return item != null;
	}

	@Override
	public Transactions next() {
		if (!hasNext()) {
			throw new NoSuchElementException("No such element");
		}
		Transactions returnValue = item;
		getNextItem();
		return returnValue;
	}

	/*
	 * This method searches for the next item that satisfies the predicate. If none
	 * is found, the item field is set to null.
	 */
	private void getNextItem() {
		while (iterator.hasNext()) {
			item = iterator.next();
			if (predicate.test(item)) {
				return;
			}
		}
		item = null;
	}
}
