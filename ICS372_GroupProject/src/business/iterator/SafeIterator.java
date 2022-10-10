package business.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

import Buissness.Entities.Member;
import Buissness.Entities.Orders;
import Buissness.Entities.Product;
import business.facade.Result;
import business.iterator.SafeIterator.Type.SafeMember;
import business.iterator.SafeIterator.Type.SafeOrder;
import business.iterator.SafeIterator.Type.SafeProduct;

public class SafeIterator<T> implements Iterator<Result> {
	private Iterator<T> iterator;
	private Type type;
	private Result result = new Result();
	public static final Type MEMBER = new SafeMember();
	public static final Type PRODUCT = new SafeProduct();
	public static final Type ORDER = new SafeOrder();

	public abstract static class Type {
		public abstract void copy(Result result, Object object);

		public static class SafeProduct extends Type {
			@Override
			public void copy(Result result, Object object) {
				Product product = (Product) object;
				result.setProductFields(product);
			}
		}

		public static class SafeMember extends Type {
			@Override
			public void copy(Result result, Object object) {
				Member member = (Member) object;
				result.setMemberFields(member);
				result.setDate(member.getDate1());
			}
		}

		public static class SafeOrder extends Type {

			@Override
			public void copy(Result result, Object object) {
				Orders order = (Orders) object;
				result.setOrderFields(order);
				result.setDate(order.getDate1());
			}
		}
	}

	public SafeIterator(Iterator<T> iterator, Type type) {
		this.iterator = iterator;
		this.type = type;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Result next() {
		if (iterator.hasNext()) {
			type.copy(result, iterator.next());
		} else {
			throw new NoSuchElementException("No such element");
		}
		return result;
	}
}
